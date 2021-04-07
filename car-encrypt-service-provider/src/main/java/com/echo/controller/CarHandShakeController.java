package com.echo.controller;

import com.echo.pojo.Car;
import com.echo.pojo.CommonMessage;
import com.echo.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/car/handshake")
public class CarHandShakeController {
    @Autowired
    CarService carService;

    @RequestMapping("/start/{carVin}/{randomA}")
    public CommonMessage<Car> startHandShake(@PathVariable("carVin") String carVin,
                                             @PathVariable("randomA")Integer randomA) {
        return carService.startHandShake(carVin,randomA);
    }

    @RequestMapping("/finish/{carVin}/{clientTempKeyStr}/{sb1Str}/{randomA}/{serverVin}/{resultSign}")
    public CommonMessage<Car> finishHandShake(@PathVariable("carVin") String carVin,
                           @PathVariable("clientTempKeyStr") String clientTempKeyStr,
                           @PathVariable("sb1Str") String sb1Str,
                               @PathVariable("randomA") String randomA,
                               @PathVariable("serverVin") String serverVin,
                               @PathVariable("resultSign") String resultSign) {
        return carService.finishHandShake(carVin, clientTempKeyStr, sb1Str,randomA,serverVin,resultSign);
    }
}
