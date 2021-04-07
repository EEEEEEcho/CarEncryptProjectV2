package com.echo.service.impl;

import com.echo.encrypt.KGCMaster;
import com.echo.encrypt.ServerMaster;
import com.echo.encrypt.gm.sm9.*;
import com.echo.mapper.CarMapper;
import com.echo.pojo.Car;
import com.echo.pojo.EncryptMessage;
import com.echo.pojo.HandShakeInfo;
import com.echo.pojo.IdentityAuthInfo;
import com.echo.service.CarService;
import com.echo.util.CodeUtil;
import com.echo.util.EncryptUtil;
import com.echo.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Random;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private CarMapper carMapper;
    @Autowired
    private KGCMaster kgcMaster;
    @Autowired
    private ServerMaster serverMaster;
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public Car firstHandShake(Car car) {
        //1.查询有无这辆车
        String carVin = car.getCarVin();
        Car existCar = carMapper.findCarByCarVin(carVin);
        if(existCar == null){
            return null;
        }
        //2.有这辆车，生成相关信息
        byte[] carPrivateEncryptKeyByte = kgcMaster.genEncryptPrivateKey(carVin).toByteArray();
        byte[] carPrivateSignKeyByte = kgcMaster.genSignPrivateKey(carVin).toByteArray();
        byte[] carPrivateExecKeyByte = kgcMaster.genExchangePrivateKey(carVin).toByteArray();
        //3.将相关信息进行存储
        car.setPrivateEncKey(CodeUtil.encodeToString(carPrivateEncryptKeyByte));
        car.setPrivateSignKey(CodeUtil.encodeToString(carPrivateSignKeyByte));
        car.setPrivateExecKey(CodeUtil.encodeToString(carPrivateExecKeyByte));
        carMapper.updateCarKeys(car);
        //3.将需要加密的信息进行加密
        byte[] carBirthKey = CodeUtil.decodeStringToByte(existCar.getBirthKey());
        car.setPrivateEncKey(EncryptUtil.encryptMessageToString(carPrivateEncryptKeyByte, carBirthKey));
        car.setPrivateSignKey(EncryptUtil.encryptMessageToString(carPrivateSignKeyByte, carBirthKey));
        car.setPrivateExecKey(EncryptUtil.encryptMessageToString(carPrivateExecKeyByte, carBirthKey));
        //4.将密钥交换所需信息进行封装，需加密
        HandShakeInfo handShakeInfo = genHandShakeInfo(carVin,carBirthKey);
        car.setHandShakeInfo(handShakeInfo);
        //5.将身份证所需信息进行封装，需加密
        IdentityAuthInfo identityAuthInfo = genIdentityInfo(carVin, car.getIdentityAuthInfo().getRandomB(), carBirthKey);
        car.setIdentityAuthInfo(identityAuthInfo);
        System.out.println("第一次握手完成");
        return car;
    }

    /**
     * 单出抽一个方法做密钥交换信息的生成
     * @param carVin
     * @param carBirthKey
     * @return
     */
    private HandShakeInfo genHandShakeInfo(String carVin, byte[] carBirthKey){
        G1KeyPair serverTempKey = kgcMaster.getSm9().keyExchangeInit(kgcMaster.getEncryptMasterKeyPair().getPublicKey(), carVin);
        String insertServerTmpKey = Base64.getUrlEncoder().encodeToString(serverTempKey.toByteArray());
        redisUtil.hset(carVin,"serverTempKey",insertServerTmpKey);
        //第二次握手还要用，先存一下
        serverMaster.setServerTempKey(serverTempKey);
        //用一个容器存握手信息
        HandShakeInfo handShakeInfo = new HandShakeInfo();
        handShakeInfo.setServerTempKey(EncryptUtil.encryptMessageToString(serverTempKey.toByteArray(), carBirthKey));
        handShakeInfo.setEncryptMasterPublicKey(EncryptUtil.encryptMessageToString(kgcMaster.getEncryptMasterKeyPair().getPublicKey().toByteArray(), carBirthKey));
        handShakeInfo.setServerVin(serverMaster.getServerVin());
        return handShakeInfo;
    }

    /**
     * 单处抽出一个方法做身份认证信息
     * @param carVin
     * @param randomB
     * @param birthKey
     * @return
     */
    private IdentityAuthInfo genIdentityInfo(String carVin, Integer randomB, byte[] birthKey){
        MasterPublicKey signMasterPublicKey = kgcMaster.getSignMasterKeyPair().getPublicKey();
        int randomA = new Random(47).nextInt();
        redisUtil.hset(carVin,"randomA",randomA + "");
        serverMaster.setRandom(randomA);
        String msg = carVin + serverMaster.getRandom() + randomB;
        ResultSignature resultSignature = kgcMaster.getSm9().sign(signMasterPublicKey,serverMaster.getSignPrivateKey(),msg.getBytes(StandardCharsets.UTF_8));
        String resultSignatureStr = CodeUtil.encodeToString(resultSignature.toByteArray());
        IdentityAuthInfo identityAuthInfo = new IdentityAuthInfo();
        identityAuthInfo.setSignMasterPublicKey(CodeUtil.encodeToString(signMasterPublicKey.toByteArray()));
        identityAuthInfo.setServerVinCode(serverMaster.getServerVin());
        identityAuthInfo.setRandomA(serverMaster.getRandom());
        identityAuthInfo.setRandomB(randomB);
        identityAuthInfo.setSignResult(resultSignatureStr);
        return identityAuthInfo;
    }

    @Override
    public Car secondHandShake(Car car) {
        IdentityAuthInfo identityAuthInfo = car.getIdentityAuthInfo();
        boolean authSuccess = finishIdentityShakeInfo(identityAuthInfo,car.getCarVin());
        HandShakeInfo handShakeInfo = car.getHandShakeInfo();
        HandShakeInfo newHandShakeInfo = finishHandShakeInfo(handShakeInfo,car.getCarVin());
        if (authSuccess && newHandShakeInfo != null){
            car.setHandShakeInfo(newHandShakeInfo);
            car.setIdentityAuthInfo(new IdentityAuthInfo());
            System.out.println("第二次握手完成");
            return car;
        }
        return null;
    }

    private HandShakeInfo finishHandShakeInfo(HandShakeInfo handShakeInfo,String carVin){
        Car car = carMapper.findCarByCarVin(carVin);
        if (car == null) {
            return null;
        }
        byte[] birthKey = CodeUtil.decodeStringToByte(car.getBirthKey());
        //2.解密clientTempKeyStr和sb1Str
        byte[] clientTempKeyBytes = EncryptUtil.decryptMessageToByte(handShakeInfo.getClientTempKey(), birthKey);
        byte[] sb1Bytes = EncryptUtil.decryptMessageToByte(handShakeInfo.getSB(), birthKey);
        //3.还原clientTempKey
        G1KeyPair clientTempKey = G1KeyPair.fromByteArray(kgcMaster.getSm9Curve(), clientTempKeyBytes);
        //4.生成serverAgreementKey
        ResultKeyExchange serverAgreementKey = null;
        //还原serverTempKey
        byte[] serverTempKeyByte = CodeUtil.decodeStringToByte(redisUtil.hget(carVin,"serverTempKey"));
        G1KeyPair serverTempKey = G1KeyPair.fromByteArray(kgcMaster.getSm9Curve(), serverTempKeyByte);
        try {
            serverAgreementKey = kgcMaster.getSm9().keyExchange(
                    kgcMaster.getEncryptMasterKeyPair().getPublicKey(),
                    true,
                    serverMaster.getServerVin(),
                    carVin,
                    serverMaster.getExchangePrivateKey(),
                    serverTempKey,
                    clientTempKey.getPublicKey(),
                    16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        if (serverAgreementKey != null && SM9Utils.byteEqual(sb1Bytes, serverAgreementKey.getSB1())) {
            HandShakeInfo newHandShakeInfo = new HandShakeInfo();
            newHandShakeInfo.setSA(EncryptUtil.encryptMessageToString(serverAgreementKey.getSA2(), birthKey));
            redisUtil.hset(carVin,"sessionKey",CodeUtil.encodeToString(serverAgreementKey.getSK()));
            //删掉这个serverTempkey,其实也可以不用删除的
            redisUtil.hdel(carVin,"serverTempKey");
            return newHandShakeInfo;
        }
        else{
            return null;
        }
    }

    private boolean finishIdentityShakeInfo(IdentityAuthInfo identityAuthInfo,String carVin){
        int flg = 0;
        int randomA = Integer.parseInt(redisUtil.hget(carVin,"randomA"));
        serverMaster.setRandom(randomA);
        if(identityAuthInfo.getRandomA().equals(serverMaster.getRandom())){
            System.out.println("RandomCheck");
            flg ++;
        }
        if(identityAuthInfo.getServerVinCode().equals(serverMaster.getServerVin())){
            System.out.println("serverVin check");
            flg ++;
        }
        ResultSignature resultSignature = ResultSignature.fromByteArray(kgcMaster.getSm9Curve(),
                CodeUtil.decodeStringToByte(identityAuthInfo.getSignResult()));
        String msg = serverMaster.getServerVin() + serverMaster.getRandom();
        if(kgcMaster.getSm9().verify(kgcMaster.getSignMasterKeyPair().getPublicKey(),
                carVin,
                msg.getBytes(StandardCharsets.UTF_8),
                resultSignature)){
            flg ++;
        }
        if (flg == 3){
            System.out.println("Check Out!");
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public EncryptMessage sendMessage(EncryptMessage encryptMessage) {
        String carVin = encryptMessage.getCarVin();
        String sessionKey = redisUtil.hget(carVin, "sessionKey");
        if(StringUtils.isEmpty(sessionKey)){
            return null;
        }
        String data = encryptMessage.getMessage();
        System.out.println("The encrypt message is :" + data);
        byte[] decryptByte = EncryptUtil.decryptMessageToByte(data,CodeUtil.decodeStringToByte(sessionKey));
        if(decryptByte != null){
            String ping = new String(decryptByte);
            System.out.println("The client send is :" + ping);
        }
        else{
            System.out.println("Decrypt Error");
        }

        String pong = "To be or not to be,that is a question";
        EncryptMessage result = new EncryptMessage();
        result.setCarVin(carVin);
        result.setTimeStamp(System.currentTimeMillis() + "");
        pong = EncryptUtil.encryptMessageToString(pong.getBytes(StandardCharsets.UTF_8),
                CodeUtil.decodeStringToByte(sessionKey));
        result.setMessage(pong);
        return result;
    }
}
