package com.echo.pojo;

public class EncryptMessage {
    private String timeStamp;
    private String message;
    private String carVin;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCarVin() {
        return carVin;
    }

    public void setCarVin(String carVin) {
        this.carVin = carVin;
    }

    @Override
    public String toString() {
        return "EncryptMessage{" +
                "timeStamp='" + timeStamp + '\'' +
                ", message='" + message + '\'' +
                ", carVin='" + carVin + '\'' +
                '}';
    }
}
