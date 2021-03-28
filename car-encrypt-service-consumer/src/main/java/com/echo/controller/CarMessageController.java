package com.echo.controller;

import com.echo.pojo.EncryptMessage;
import com.echo.service.CarMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CarMessageController {
    @Autowired
    private CarMessageService carMessageService;

    @RequestMapping(value = "/message",method = RequestMethod.POST)
    public EncryptMessage sayHello(@RequestParam("carVin")String carVin, @RequestParam("data")String data) {
        return carMessageService.sayHello(carVin, data);
    }
}
