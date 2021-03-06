package com.echo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.echo.pojo.Car;
import com.echo.pojo.CommonMessage;
import com.echo.pojo.EncryptMessage;
import com.echo.pojo.HttpStatus;
import com.echo.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarService carService;

    @RequestMapping(value = "/handshake/first",method = RequestMethod.POST)
    public CommonMessage<Car> firstHandShake(@RequestBody Car car){
//        if (data.length() == 0){
//            return new CommonMessage<>(HttpStatus.NOTFOUND.code, HttpStatus.NOTFOUND.tip, null);
//        }
        if (car == null){
            return new CommonMessage<>(HttpStatus.NOTFOUND.code, HttpStatus.NOTFOUND.tip, null);
        }
        else{
            //Car car;
            try {
                //car = JSON.parseObject(data, Car.class);
                car = carService.firstHandShake(car);
                if(car == null){
                    return new CommonMessage<>(HttpStatus.NOTFOUND.code, HttpStatus.NOTFOUND.tip, null);
                }
            }
            catch (JSONException jsonException){
                jsonException.printStackTrace();
                return new CommonMessage<>(HttpStatus.NOTFOUND.code, HttpStatus.NOTFOUND.tip, null);
            }
            return new CommonMessage<>(HttpStatus.SUCCESS.code, HttpStatus.SUCCESS.tip, car);
        }
    }

    @RequestMapping(value = "/handshake/second",method = RequestMethod.POST)
    public CommonMessage<Car> secondHandShake(@RequestBody Car car){
        if (car == null){
            return new CommonMessage<>(HttpStatus.NOTFOUND.code, HttpStatus.NOTFOUND.tip, null);
        }
        else{
            try {
                car = carService.secondHandShake(car);
                if(car == null){
                    return new CommonMessage<>(HttpStatus.NOTFOUND.code, HttpStatus.NOTFOUND.tip, null);
                }
            }
            catch (JSONException jsonException){
                jsonException.printStackTrace();
                return new CommonMessage<>(HttpStatus.NOTFOUND.code, HttpStatus.NOTFOUND.tip, null);
            }
            return new CommonMessage<>(HttpStatus.SUCCESS.code, HttpStatus.SUCCESS.tip, car);
        }
    }

    //?????????
//    @RequestMapping(value = "/handshake/second",method = RequestMethod.POST)
//    public CommonMessage<Car> secondHandShake(@RequestParam("data")String data){
//        if (data.length() == 0){
//            return new CommonMessage<>(HttpStatus.NOTFOUND.code, HttpStatus.NOTFOUND.tip, null);
//        }
//        else{
//            Car car;
//            try {
//                car = JSON.parseObject(data, Car.class);
//                car = carService.secondHandShake(car);
//                if(car == null){
//                    return new CommonMessage<>(HttpStatus.NOTFOUND.code, HttpStatus.NOTFOUND.tip, null);
//                }
//            }
//            catch (JSONException jsonException){
//                jsonException.printStackTrace();
//                return new CommonMessage<>(HttpStatus.NOTFOUND.code, HttpStatus.NOTFOUND.tip, null);
//            }
//            return new CommonMessage<>(HttpStatus.SUCCESS.code, HttpStatus.SUCCESS.tip, car);
//        }
//    }

    @RequestMapping(value = "/message",method = RequestMethod.POST)
    public CommonMessage<EncryptMessage> sendMessage(@RequestBody EncryptMessage encryptMessage){
        if (encryptMessage == null){
            return new CommonMessage<>(HttpStatus.NOTFOUND.code, HttpStatus.NOTFOUND.tip, null);
        }
        else{
            try {
                encryptMessage = carService.sendMessage(encryptMessage);
                if(encryptMessage == null){
                    return new CommonMessage<>(HttpStatus.NOTFOUND.code, HttpStatus.NOTFOUND.tip, null);
                }
            }
            catch (JSONException jsonException){
                jsonException.printStackTrace();
                return new CommonMessage<>(HttpStatus.NOTFOUND.code, HttpStatus.NOTFOUND.tip, null);
            }
            return new CommonMessage<>(HttpStatus.SUCCESS.code, HttpStatus.SUCCESS.tip, encryptMessage);
        }
    }
}
