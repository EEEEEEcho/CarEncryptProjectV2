package com.echo.encrypt;

import com.echo.encrypt.gm.sm9.G1KeyPair;
import com.echo.encrypt.gm.sm9.MasterKeyPair;
import com.echo.encrypt.gm.sm9.PrivateKey;
import com.echo.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class ServerMaster {
    private String serverVin;
    private PrivateKey encryptPrivateKey;
    private PrivateKey signPrivateKey;
    private PrivateKey exchangePrivateKey;
    private G1KeyPair serverTempKey;
    @Autowired
    private KGCMaster kgcMaster;
    @Autowired
    private ServerService serverService;

    public void init() {
        serverVin = "ADERXWRY78VC78W";
        encryptPrivateKey = kgcMaster.genEncryptPrivateKey(serverVin);
        signPrivateKey = kgcMaster.genSignPrivateKey(serverVin);
        exchangePrivateKey = kgcMaster.genExchangePrivateKey(serverVin);
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("serverVin", serverVin);
        hashMap.put("privateEncKey", Base64.getUrlEncoder().encode(encryptPrivateKey.toByteArray()));
        hashMap.put("privateSignKey", Base64.getUrlEncoder().encode(signPrivateKey.toByteArray()));
        hashMap.put("privateExecKey", Base64.getUrlEncoder().encode(exchangePrivateKey.toByteArray()));
        serverService.updateServer(hashMap);
    }

    public String getServerVin() {
        return serverVin;
    }

    public void setServerVin(String serverVin) {
        this.serverVin = serverVin;
    }

    public PrivateKey getEncryptPrivateKey() {
        return encryptPrivateKey;
    }

    public void setEncryptPrivateKey(PrivateKey encryptPrivateKey) {
        this.encryptPrivateKey = encryptPrivateKey;
    }

    public PrivateKey getSignPrivateKey() {
        return signPrivateKey;
    }

    public void setSignPrivateKey(PrivateKey signPrivateKey) {
        this.signPrivateKey = signPrivateKey;
    }

    public PrivateKey getExchangePrivateKey() {
        return exchangePrivateKey;
    }

    public void setExchangePrivateKey(PrivateKey exchangePrivateKey) {
        this.exchangePrivateKey = exchangePrivateKey;
    }

    public G1KeyPair getServerTempKey() {
        return serverTempKey;
    }

    public void setServerTempKey(G1KeyPair serverTempKey) {
        this.serverTempKey = serverTempKey;
    }
}
