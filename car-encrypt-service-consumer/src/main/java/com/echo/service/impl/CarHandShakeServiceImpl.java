package com.echo.service.impl;

import com.echo.client.CarClient;
import com.echo.pojo.Car;
import com.echo.service.CarHandShakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CarHandShakeServiceImpl implements CarHandShakeService {
    @Qualifier("com.echo.client.CarClient")
    @Autowired
    private CarClient carClient;

    @Override
    public Car startHandShake(String carVin) {
        return carClient.startHandShake(carVin);
    }

    @Override
    public Car finishHandShake(String carVin, String clientTempKeyStr, String sb1Str) {
        System.out.println("经过了consumer中的service");
        return carClient.finishHandShake(carVin,clientTempKeyStr,sb1Str);
    }
}
