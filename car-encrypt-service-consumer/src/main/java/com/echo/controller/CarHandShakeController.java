package com.echo.controller;

import com.echo.pojo.Car;
import com.echo.service.CarHandShakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/car/handshake")
public class CarHandShakeController {
    @Autowired
    private CarHandShakeService carHandShakeService;

    @RequestMapping("/start/{carVin}")
    public Car startHandShake(@PathVariable("carVin") String carVin) {
        return carHandShakeService.startHandShake(carVin);
    }

    @RequestMapping("/finish/{carVin}/{clientTempKeyStr}/{sb1Str}")
    public Car finishHandShake(@PathVariable("carVin") String carVin,
                               @PathVariable("clientTempKeyStr") String clientTempKeyStr,
                               @PathVariable("sb1Str") String sb1Str) {
        System.out.println("经过了consumer中的feign");
        return carHandShakeService.finishHandShake(carVin, clientTempKeyStr, sb1Str);
    }

}
