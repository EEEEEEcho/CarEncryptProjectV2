package com.echo.service.impl;

import com.echo.client.CarClient;
import com.echo.pojo.Car;
import com.echo.pojo.CommonMessage;
import com.echo.pojo.EncryptMessage;
import com.echo.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl implements CarService {
    @Qualifier("com.echo.client.CarClient")
    @Autowired
    private CarClient carClient;

    @Override
    public CommonMessage<Car> firstHandShake(String data) {
        return carClient.firstHandShake(data);
    }

    @Override
    public CommonMessage<Car> secondHandShake(String data) {
        return carClient.secondHandShake(data);
    }

    @Override
    public CommonMessage<EncryptMessage> sendMessage(String data) {
        return carClient.sendMessage(data);
    }
}
