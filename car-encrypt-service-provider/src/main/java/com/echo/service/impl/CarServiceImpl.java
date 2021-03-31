package com.echo.service.impl;

import com.echo.encrypt.KGCMaster;
import com.echo.encrypt.ServerMaster;
import com.echo.encrypt.gm.sm9.G1KeyPair;
import com.echo.encrypt.gm.sm9.PrivateKey;
import com.echo.encrypt.gm.sm9.ResultKeyExchange;
import com.echo.encrypt.gm.sm9.SM9Utils;
import com.echo.mapper.CarMapper;
import com.echo.mapper.TmpExchangeInfoMapper;
import com.echo.pojo.Car;
import com.echo.pojo.EncryptMessage;
import com.echo.pojo.HandShakeInfo;
import com.echo.service.CarService;
import com.echo.util.RedisUtil;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class CarServiceImpl implements CarService {
    @Autowired
    private CarMapper carMapper;
    @Autowired
    private KGCMaster kgcMaster;
    @Autowired
    private ServerMaster serverMaster;
    @Autowired
    TmpExchangeInfoMapper tmpMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Car startHandShake(String carVin) {
        //1.查询有无这辆车
        Car isExist = carMapper.findCarByCarVin(carVin);
        if (isExist == null) {
            return null;
        }
        //2.有这辆车，生成相关信息
        Car car = new Car();    //这里要重新生成一个对象，因为如果有原来的相关信息，相关信息会传递过去
        car.setBirthKey(isExist.getBirthKey());
        car.setCarVin(isExist.getCarVin());
        byte[] carPrivateEncryptKeyByte = kgcMaster.genEncryptPrivateKey(carVin).toByteArray();
        byte[] carPrivateSignKeyByte = kgcMaster.genSignPrivateKey(carVin).toByteArray();
        byte[] carPrivateExecKeyByte = kgcMaster.genExchangePrivateKey(carVin).toByteArray();
        //3.将相关信息进行存储
        car.setPrivateEncKey(encode(carPrivateEncryptKeyByte));
        car.setPrivateSignKey(encode(carPrivateSignKeyByte));
        car.setPrivateExecKey(encode(carPrivateExecKeyByte));
        carMapper.updateCarKeys(car);
        //3.将需要加密的信息进行加密
        byte[] carBirthKey = decode(car.getBirthKey());
        //重新设定车辆对象，用来进行密文返回
        car.setPrivateEncKey(kgcMaster.encryptMessageToString(carPrivateEncryptKeyByte, carBirthKey));
        car.setPrivateSignKey(kgcMaster.encryptMessageToString(carPrivateSignKeyByte, carBirthKey));
        car.setPrivateExecKey(kgcMaster.encryptMessageToString(carPrivateExecKeyByte, carBirthKey));
        //4.将密钥交换所需信息进行封装，需加密
        //todo 这里逻辑有大问题
//        String serverTmpKeyStr = tmpMapper.findServerTmpKey(carVin);
//        Map<Object,Object> carCache = redisUtil.hgetall(carVin);
//        String serverTmpKeyStr = (String)carCache.get("serverTempKey");
//        G1KeyPair serverTempKey = null;
//        if(serverTmpKeyStr == null){
//            serverTempKey = kgcMaster.getSm9().keyExchangeInit(kgcMaster.getEncryptMasterKeyPair().getPublicKey(), carVin);
//            String insertServerTmpKey = Base64.getUrlEncoder().encodeToString(serverTempKey.toByteArray());
//            //tmpMapper.insertServerTmpKey(carVin,insertServerTmpKey);
//            redisUtil.hset(carVin,"serverTempKey",insertServerTmpKey);
//        }
//        else{
//            byte[] serverTmpKeyByte = Base64.getUrlDecoder().decode(serverTmpKeyStr);
//            serverTempKey = G1KeyPair.fromByteArray(kgcMaster.getSm9Curve(),serverTmpKeyByte);
//        }
        G1KeyPair serverTempKey = kgcMaster.getSm9().keyExchangeInit(kgcMaster.getEncryptMasterKeyPair().getPublicKey(), carVin);
        String insertServerTmpKey = Base64.getUrlEncoder().encodeToString(serverTempKey.toByteArray());
        redisUtil.hset(carVin,"serverTempKey",insertServerTmpKey);

        //第二次握手还要用，先存一下
        serverMaster.setServerTempKey(serverTempKey);
        //用一个容器存握手信息
        HandShakeInfo handShakeInfo = new HandShakeInfo();
        handShakeInfo.setServerTempKey(kgcMaster.encryptMessageToString(serverTempKey.toByteArray(), carBirthKey));
        handShakeInfo.setEncryptMasterPublicKey(kgcMaster.encryptMessageToString(kgcMaster.getEncryptMasterKeyPair().getPublicKey().toByteArray(), carBirthKey));
        handShakeInfo.setServerVin(serverMaster.getServerVin());
        car.setHandShakeInfo(handShakeInfo);
        //清空birtkKey
        car.setBirthKey(null);
        System.out.println("第一次握手完成");
        return car;
    }

    @Override
    public Car finishHandShake(String carVin, String clientTempKeyStr, String sb1Str) {
        //1.获得车辆的birthKey用来解密clientTempKeyStr和sb1Str
        Car car = carMapper.findCarByCarVin(carVin);
        if (car == null) {
            return null;
        }
        byte[] birthKey = decode(car.getBirthKey());
        //2.解密clientTempKeyStr和sb1Str
        byte[] clientTempKeyBytes = kgcMaster.decryptMessageToByte(clientTempKeyStr, birthKey);
        byte[] sb1Bytes = kgcMaster.decryptMessageToByte(sb1Str, birthKey);
        //3.还原clientTempKey
        G1KeyPair clientTempKey = G1KeyPair.fromByteArray(kgcMaster.getSm9Curve(), clientTempKeyBytes);
        //4.生成serverAgreementKey
        ResultKeyExchange serverAgreementKey = null;
        //还原serverTempKey
        byte[] serverTempKeyByte = decode(redisUtil.hget(carVin,"serverTempKey"));
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
        }
        Car result = new Car();
        if (serverAgreementKey != null && SM9Utils.byteEqual(sb1Bytes, serverAgreementKey.getSB1())) {
            //System.out.println("进来了");
            HandShakeInfo handShakeInfo = new HandShakeInfo();
            handShakeInfo.setSA(kgcMaster.encryptMessageToString(serverAgreementKey.getSA2(), birthKey));
            //todo 使用redis进行session key的缓存，目前仍使用的mysql进行存储
//            Map<String, Object> map = new HashMap<>();
//            map.put("sessionKey", encode(serverAgreementKey.getSK()));
//            map.put("carVin", carVin);
//            carMapper.updateTheSessionKeyOfCar(map);
            redisUtil.hset(carVin,"sessionKey",encode(serverAgreementKey.getSK()));
            result.setHandShakeInfo(handShakeInfo);
        }
        System.out.println("第二次握手完成");
        //删掉这个serverTempkey,其实也可以不用删除的
        redisUtil.hdel(carVin,"serverTempKey");
        return result;
    }

    @Override
    public EncryptMessage sayHello(String carVin, String message) {
//        Car car = carMapper.findCarByCarVin(carVin);
        EncryptMessage encryptMessage = new EncryptMessage();
        String sessionKeyStr = redisUtil.hget(carVin,"sessionKey");
        if (sessionKeyStr == null) {
            return encryptMessage;
        }
        byte[] sessionKey = decode(sessionKeyStr);
        byte[] decryptByte  = kgcMaster.decryptMessageToByte(message,sessionKey);
        String s = new String(decryptByte);
        System.out.println("The client send:" + s);
        String result = "World Is Beautiful";
        byte[] resultByte = result.getBytes(StandardCharsets.UTF_8);
        encryptMessage.setTimeStamp(String.valueOf(System.currentTimeMillis()));
        encryptMessage.setMessage(kgcMaster.encryptMessageToString(resultByte, sessionKey));
        return encryptMessage;
    }

    public String encode(byte[] bytes) {
        return Base64.getUrlEncoder().encodeToString(bytes);
    }

    public byte[] decode(String str) {
        return Base64.getUrlDecoder().decode(str);
    }
}
