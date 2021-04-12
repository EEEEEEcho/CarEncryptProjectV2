package com.echo.pojo;

public class HandShakeInfo {
    private String encryptMasterPublicKey;
    private String serverTempKey;
    private String clientTempKey;
    private String sa;
    private String sb;
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

    public String getSa() {
        return sa;
    }

    public void setSa(String sa) {
        this.sa = sa;
    }

    public String getSb() {
        return sb;
    }

    public void setSb(String sb) {
        this.sb = sb;
    }

    public String getServerVin() {
        return serverVin;
    }

    public void setServerVin(String serverVin) {
        this.serverVin = serverVin;
    }

    @Override
    public String toString() {
        return "HandShakeInfo{" +
                "encryptMasterPublicKey='" + encryptMasterPublicKey + '\'' +
                ", serverTempKey='" + serverTempKey + '\'' +
                ", clientTempKey='" + clientTempKey + '\'' +
                ", SA='" + sa + '\'' +
                ", SB='" + sb + '\'' +
                ", serverVin='" + serverVin + '\'' +
                '}';
    }
}
