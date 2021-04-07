package com.echo.pojo;

public class Car {
    private String carVin;
    private String birthKey;
    private String privateEncKey;
    private String privateSignKey;
    private String privateExecKey;
    private String sessionKey;
    private String message;
    private HandShakeInfo handShakeInfo;
    private IdentityAuthInfo identityAuthInfo;

    public String getCarVin() {
        return carVin;
    }

    public void setCarVin(String carVin) {
        this.carVin = carVin;
    }

    public String getBirthKey() {
        return birthKey;
    }

    public void setBirthKey(String birthKey) {
        this.birthKey = birthKey;
    }

    public String getPrivateEncKey() {
        return privateEncKey;
    }

    public void setPrivateEncKey(String privateEncKey) {
        this.privateEncKey = privateEncKey;
    }

    public String getPrivateSignKey() {
        return privateSignKey;
    }

    public void setPrivateSignKey(String privateSignKey) {
        this.privateSignKey = privateSignKey;
    }

    public String getPrivateExecKey() {
        return privateExecKey;
    }

    public void setPrivateExecKey(String privateExecKey) {
        this.privateExecKey = privateExecKey;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HandShakeInfo getHandShakeInfo() {
        return handShakeInfo;
    }

    public void setHandShakeInfo(HandShakeInfo handShakeInfo) {
        this.handShakeInfo = handShakeInfo;
    }

    public IdentityAuthInfo getIdentityAuthInfo() {
        return identityAuthInfo;
    }

    public void setIdentityAuthInfo(IdentityAuthInfo identityAuthInfo) {
        this.identityAuthInfo = identityAuthInfo;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carVin='" + carVin + '\'' +
                ", birthKey='" + birthKey + '\'' +
                ", privateEncKey='" + privateEncKey + '\'' +
                ", privateSignKey='" + privateSignKey + '\'' +
                ", privateExecKey='" + privateExecKey + '\'' +
                ", sessionKey='" + sessionKey + '\'' +
                ", message='" + message + '\'' +
                ", handShakeInfo=" + handShakeInfo +
                ", identityAuthInfo=" + identityAuthInfo +
                '}';
    }
}
