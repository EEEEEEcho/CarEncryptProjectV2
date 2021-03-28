package com.echo.service;

import com.echo.pojo.Car;

public interface CarHandShakeService {
    public Car startHandShake(String carVin);

    public Car finishHandShake(String carVin, String clientTempKeyStr, String sb1Str);
}
