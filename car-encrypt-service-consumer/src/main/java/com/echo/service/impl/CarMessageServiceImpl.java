package com.echo.service.impl;

import com.echo.client.CarClient;
import com.echo.pojo.EncryptMessage;
import com.echo.service.CarMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CarMessageServiceImpl implements CarMessageService {
    @Qualifier("com.echo.client.CarClient")
    @Autowired
    private CarClient carClient;

    @Override
    public EncryptMessage sayHello(String carVin, String message) {
        return carClient.sayHello(carVin,message);
    }
}
