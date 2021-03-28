package com.echo.service;

import com.echo.pojo.EncryptMessage;

public interface CarMessageService {
    public EncryptMessage sayHello(String carVin, String message);
}
