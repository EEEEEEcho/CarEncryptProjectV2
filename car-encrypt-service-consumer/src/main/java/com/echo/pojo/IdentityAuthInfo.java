package com.echo.pojo;

public class IdentityAuthInfo {
    private String serverVinCode;
    private Integer randomA;
    private Integer randomB;
    private String signMasterPublicKey;
    private String signResult;

    public String getServerVinCode() {
        return serverVinCode;
    }

    public void setServerVinCode(String serverVinCode) {
        this.serverVinCode = serverVinCode;
    }

    public Integer getRandomA() {
        return randomA;
    }

    public void setRandomA(Integer randomA) {
        this.randomA = randomA;
    }

    public Integer getRandomB() {
        return randomB;
    }

    public void setRandomB(Integer randomB) {
        this.randomB = randomB;
    }

    public String getSignMasterPublicKey() {
        return signMasterPublicKey;
    }

    public void setSignMasterPublicKey(String signMasterPublicKey) {
        this.signMasterPublicKey = signMasterPublicKey;
    }

    public String getSignResult() {
        return signResult;
    }

    public void setSignResult(String signResult) {
        this.signResult = signResult;
    }
}
