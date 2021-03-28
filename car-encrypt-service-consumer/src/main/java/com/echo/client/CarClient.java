package com.echo.client;

import com.echo.pojo.Car;
import com.echo.pojo.EncryptMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service-provider",fallback = CarClientFallBack.class)
public interface CarClient {
    @RequestMapping("/car/handshake/start/{carVin}")
    public Car startHandShake(@PathVariable("carVin") String carVin);

    @RequestMapping("/car/handshake/finish/{carVin}/{clientTempKeyStr}/{sb1Str}")
    public Car finishHandShake(@PathVariable("carVin") String carVin,
                               @PathVariable("clientTempKeyStr") String clientTempKeyStr,
                               @PathVariable("sb1Str") String sb1Str);

    @RequestMapping(value = "/message",method = RequestMethod.POST)
    public EncryptMessage sayHello(@RequestParam("carVin")String carVin, @RequestParam("data")String data);
}
