package com.echo.controller;

import com.echo.pojo.Car;
import com.echo.pojo.CommonMessage;
import com.echo.pojo.EncryptMessage;
import com.echo.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/car")
public class CarController {
    @Autowired
    private CarService carService;

    @RequestMapping(value = "/handshake/first",method = RequestMethod.POST)
    public CommonMessage<Car> firstHandShake(@RequestParam("data")String data) {
        return carService.firstHandShake(data);
    }

    @RequestMapping(value = "/handshake/second",method = RequestMethod.POST)
    public CommonMessage<Car> secondHandShake(@RequestParam("data")String data) {
        //System.out.println("经过了consumer中的feign");
        return carService.secondHandShake(data);
    }

    @RequestMapping(value = "/message",method = RequestMethod.POST)
    public CommonMessage<EncryptMessage> sendMessage(@RequestParam("data")String data){
        return carService.sendMessage(data);
    }

}
