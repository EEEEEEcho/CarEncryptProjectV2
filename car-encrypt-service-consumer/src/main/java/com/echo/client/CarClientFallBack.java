package com.echo.client;

import com.echo.pojo.Car;
import com.echo.pojo.CommonMessage;
import com.echo.pojo.EncryptMessage;
import com.echo.pojo.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class CarClientFallBack implements CarClient{
    @Override
    public CommonMessage<Car> firstHandShake(String data) {
        System.out.println("FirstHandShake FallBack");
        return new CommonMessage<Car>(HttpStatus.NOTFOUND.code, HttpStatus.NOTFOUND.tip,null);
    }

    @Override
    public CommonMessage<Car> secondHandShake(String data) {
        System.out.println("SecondHandShake FallBack");
        return new CommonMessage<Car>(HttpStatus.NOTFOUND.code, HttpStatus.NOTFOUND.tip,null);
    }

    @Override
    public CommonMessage<EncryptMessage> sendMessage(String data) {
        System.out.println("SendMessage FallBack");
        return new CommonMessage<>(HttpStatus.NOTFOUND.code, HttpStatus.NOTFOUND.tip, null);
    }

}
