package com.echo.controller;

import com.echo.pojo.EncryptMessage;
import com.echo.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CarMessageController {
    @Autowired
    private CarService carService;

    @RequestMapping(value = "/message",method = RequestMethod.POST)
    public EncryptMessage sayHello(@RequestParam("carVin")String carVin,@RequestParam("data")String data) {
        //return new EncryptMessage();
        System.out.println(carVin + ":" + data);
        return carService.sayHello(carVin, data);
    }
    @RequestMapping(value = "/hello",method = RequestMethod.POST)
    public String helloWorld(@RequestParam("name")String name){
        return "Hello world";
    }
//    @RequestMapping(name = "/message",method = RequestMethod.POST)
//    public String sayHello(@RequestParam("carVin")String carVin,@RequestParam("data")String data) {
//        //return new EncryptMessage();
//        System.out.println(carVin + ":" + data);
//        EncryptMessage encryptMessage = new EncryptMessage();
//        encryptMessage.setMessage("sssssss");
//    }
}
