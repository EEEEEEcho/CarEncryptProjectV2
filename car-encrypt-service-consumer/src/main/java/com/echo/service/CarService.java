package com.echo.service;

import com.echo.pojo.Car;
import com.echo.pojo.CommonMessage;
import com.echo.pojo.EncryptMessage;
import org.springframework.web.bind.annotation.RequestParam;

public interface CarService {
    public CommonMessage<Car> firstHandShake(String data);

    public CommonMessage<Car> secondHandShake(String data);

    public CommonMessage<EncryptMessage> sendMessage(String data);
}
