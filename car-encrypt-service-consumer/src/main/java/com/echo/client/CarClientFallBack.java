package com.echo.client;

import com.echo.pojo.Car;
import com.echo.pojo.EncryptMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class CarClientFallBack implements CarClient{
    @Override
    public Car startHandShake(String carVin) {
        System.out.println("StartHandShake FallBack");
        return null;
    }

    @Override
    public Car finishHandShake(String carVin, String clientTempKeyStr, String sb1Str) {
        System.out.println("FinishHandShake FallBack");
        return null;
    }

    @Override
    public EncryptMessage sayHello(String carVin, String data) {
        System.out.println("SayHello FallBack");
        return null;
    }
}
