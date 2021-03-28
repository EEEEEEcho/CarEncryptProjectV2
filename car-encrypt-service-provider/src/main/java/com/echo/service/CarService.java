package com.echo.service;

import com.echo.pojo.Car;
import com.echo.pojo.EncryptMessage;
import org.springframework.web.bind.annotation.PathVariable;

public interface CarService {
    public Car startHandShake(String carVin);

    public Car finishHandShake(String carVin, String clientTempKeyStr, String sb1Str);

    public EncryptMessage sayHello(String carVin, String message);
}
