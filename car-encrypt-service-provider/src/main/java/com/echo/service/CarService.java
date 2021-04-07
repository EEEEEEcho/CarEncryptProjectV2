package com.echo.service;

import com.echo.pojo.Car;
import com.echo.pojo.CommonMessage;
import com.echo.pojo.EncryptMessage;
import org.springframework.web.bind.annotation.PathVariable;

public interface CarService {
    public CommonMessage<Car> startHandShake(String carVin,Integer randomA);

    public CommonMessage<Car> finishHandShake(String carVin, String clientTempKeyStr, String sb1Str,String randomA,String serverVin,String resultSign);

    public EncryptMessage sayHello(String carVin, String message);
}
