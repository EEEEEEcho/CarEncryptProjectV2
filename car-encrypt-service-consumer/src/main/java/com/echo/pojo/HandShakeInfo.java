package com.echo.pojo;

public class HandShakeInfo {
    private String encryptMasterPublicKey;
    private String serverTempKey;
    private String clientTempKey;
    private String SA;
    private String SB;
    private String serverVin;

    public String getEncryptMasterPublicKey() {
        return encryptMasterPublicKey;
    }

    public void setEncryptMasterPublicKey(String encryptMasterPublicKey) {
        this.encryptMasterPublicKey = encryptMasterPublicKey;
    }

    public String getServerTempKey() {
        return serverTempKey;
    }

    public void setServerTempKey(String serverTempKey) {
        this.serverTempKey = serverTempKey;
    }

    public String getClientTempKey() {
        return clientTempKey;
    }

    public void setClientTempKey(String clientTempKey) {
        this.clientTempKey = clientTempKey;
    }

    public String getSA() {
        return SA;
    }

    public void setSA(String SA) {
        this.SA = SA;
    }

    public String getSB() {
        return SB;
    }

    public void setSB(String SB) {
        this.SB = SB;
    }

    public String getServerVin() {
        return serverVin;
    }

    public void setServerVin(String serverVin) {
        this.serverVin = serverVin;
    }
}
