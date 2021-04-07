package com.echo.service;

import com.echo.pojo.Car;
import com.echo.pojo.CommonMessage;
import com.echo.pojo.EncryptMessage;

public interface CarService {
    public Car firstHandShake(Car car);

    public Car secondHandShake(Car car);

    public EncryptMessage sendMessage(EncryptMessage encryptMessage);
}
