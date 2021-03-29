package com.echo.encrypt;

import com.echo.encrypt.gm.sm4.SM4;
import com.echo.encrypt.gm.sm9.*;
import com.echo.service.KGCMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class KGCMaster {
    private SM9Curve sm9Curve;
    private SM9 sm9;
    private KGC kgc;
    private MasterKeyPair encryptMasterKeyPair;
    private MasterKeyPair signMasterKeyPair;
    @Autowired
    private KGCMasterService kgcMasterService;


    public void init() {
        sm9Curve = new SM9Curve();
        sm9 = new SM9(sm9Curve);
        kgc = new KGC(sm9Curve);
        //尝试一下还原MasterKeyPair 是否能解决负载均衡问题
        List<Map<String, String>> keyPair = kgcMasterService.getKeyPair();
        System.out.println(keyPair);
        String id = "0E7231DC797C486290E8713CA3C6ECCC";
        if(keyPair.get(0) == null){
            encryptMasterKeyPair = kgc.genEncryptMasterKeyPair();
            signMasterKeyPair = kgc.genSignMasterKeyPair();
            Map<String,Object> map = new HashMap<>();
            map.put("id", id);
            map.put("encMasterKeyPair",Base64.getUrlEncoder().encode(encryptMasterKeyPair.toByteArray()));
            map.put("signMasterKeyPair",Base64.getUrlEncoder().encode(signMasterKeyPair.toByteArray()));
            kgcMasterService.updateKeyPair(map);
        }
        else{
            byte[] encMasterKeyPairByte = Base64.getUrlDecoder().decode(keyPair.get(0).get("enc_master_key_pair"));
            byte[] signMasterKeyPairByte = Base64.getUrlDecoder().decode(keyPair.get(0).get("sign_master_key_pair"));
            encryptMasterKeyPair = MasterKeyPair.fromByteArray(sm9Curve,encMasterKeyPairByte);
            signMasterKeyPair = MasterKeyPair.fromByteArray(sm9Curve,signMasterKeyPairByte);
        }
        //经过实验：这两个MasterKeyPair,只要椭圆曲线参数没有变。这两个MasterKeyPair就不会变！，所以上述代码可以删除。
        //经过实验，在缓存中需要存储的其实就只是serverTmpKey即可，其余代码可以不变！
        Map<String, Object> map = new HashMap<>();
        map.put("publicEncMasterKey", Base64.getUrlEncoder().encode(encryptMasterKeyPair.getPublicKey().toByteArray()));
        map.put("privateEncMasterKey", Base64.getUrlEncoder().encode(encryptMasterKeyPair.getPrivateKey().toByteArray()));
        map.put("publicSignMasterKey", Base64.getUrlEncoder().encode(signMasterKeyPair.getPublicKey().toByteArray()));
        map.put("privateSignMasterKey", Base64.getUrlEncoder().encode(signMasterKeyPair.getPrivateKey().toByteArray()));
        map.put("id", id);
        kgcMasterService.updateKgc(map);
    }

    /**
     * use vin code to generate encrypt private key
     *
     * @param id vin code
     * @return encrypt private key
     */
    public PrivateKey genEncryptPrivateKey(String id) {

        MasterPrivateKey privateKey = encryptMasterKeyPair.getPrivateKey();
        try {
            return kgc.genPrivateKey(privateKey, id, PrivateKeyType.KEY_ENCRYPT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * use vin code to generate sign private key
     *
     * @param id vin code
     * @return sign private key
     */
    public PrivateKey genSignPrivateKey(String id) {
        MasterPrivateKey privateKey = signMasterKeyPair.getPrivateKey();
        try {
            return kgc.genPrivateKey(privateKey, id, PrivateKeyType.KEY_SIGN);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * use vin code to generate exchange private key
     *
     * @param id vin code
     * @return exchange
     */
    public PrivateKey genExchangePrivateKey(String id) {
        MasterPrivateKey privateKey = encryptMasterKeyPair.getPrivateKey();
        try {
            return kgc.genPrivateKey(privateKey, id, PrivateKeyType.KEY_KEY_EXCHANGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public byte[] encryptMessageToByte(byte[] bytes, byte[] key) {
        try {
            return SM4.ecbCrypt(true, key, bytes, 0, bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] encryptMessageToByte(String str, byte[] key) {
        try {
            byte[] bytes = Base64.getUrlDecoder().decode(str);
            return SM4.ecbCrypt(true, key, bytes, 0, bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] decryptMessageToByte(byte[] bytes, byte[] key) {
        try {
            return SM4.ecbCrypt(false, key, bytes, 0, bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] decryptMessageToByte(String str, byte[] key) {
        try {
            byte[] bytes = Base64.getUrlDecoder().decode(str);
            return SM4.ecbCrypt(false, key, bytes, 0, bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String encryptMessageToString(String str, byte[] key) {
        try {
            byte[] bytes = Base64.getUrlDecoder().decode(str);
            byte[] ecbCrypt = SM4.ecbCrypt(true, key, bytes, 0, bytes.length);
            return Base64.getUrlEncoder().encodeToString(ecbCrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String encryptMessageToString(byte[] bytes, byte[] key) {
        try {
            byte[] ecbCrypt = SM4.ecbCrypt(true, key, bytes, 0, bytes.length);
            return Base64.getUrlEncoder().encodeToString(ecbCrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String decryptMessageToString(byte[] bytes, byte[] key) {
        try {
            byte[] ecbCrypt = SM4.ecbCrypt(false, key, bytes, 0, bytes.length);
            return Base64.getUrlEncoder().encodeToString(ecbCrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String decryptMessageToString(String str, byte[] key) {
        try {
            byte[] bytes = Base64.getUrlDecoder().decode(str);
            byte[] ecbCrypt = SM4.ecbCrypt(false, key, bytes, 0, bytes.length);
            return Base64.getUrlEncoder().encodeToString(ecbCrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String decryptMessageToNoneUrlString(String str, byte[] key) {
        try {
            byte[] bytes = Base64.getDecoder().decode(str);
            byte[] ecbCrypt = SM4.ecbCrypt(false, key, bytes, 0, bytes.length);
            return Base64.getEncoder().encodeToString(ecbCrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String encryptMessageToNoneUrlString(String str, byte[] key) {
        try {
            byte[] bytes = Base64.getDecoder().decode(str);
            byte[] ecbCrypt = SM4.ecbCrypt(true, key, bytes, 0, bytes.length);
            return Base64.getEncoder().encodeToString(ecbCrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public SM9Curve getSm9Curve() {
        return sm9Curve;
    }

    public void setSm9Curve(SM9Curve sm9Curve) {
        this.sm9Curve = sm9Curve;
    }

    public SM9 getSm9() {
        return sm9;
    }

    public void setSm9(SM9 sm9) {
        this.sm9 = sm9;
    }

    public KGC getKgc() {
        return kgc;
    }

    public void setKgc(KGC kgc) {
        this.kgc = kgc;
    }

    public MasterKeyPair getEncryptMasterKeyPair() {
        return encryptMasterKeyPair;
    }

    public void setEncryptMasterKeyPair(MasterKeyPair encryptMasterKeyPair) {
        this.encryptMasterKeyPair = encryptMasterKeyPair;
    }

    public MasterKeyPair getSignMasterKeyPair() {
        return signMasterKeyPair;
    }

    public void setSignMasterKeyPair(MasterKeyPair signMasterKeyPair) {
        this.signMasterKeyPair = signMasterKeyPair;
    }
}
