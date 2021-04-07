package com.echo.client;

import com.echo.pojo.Car;
import com.echo.pojo.CommonMessage;
import com.echo.pojo.EncryptMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service-provider",fallback = CarClientFallBack.class)
public interface CarClient {
    @RequestMapping(value = "/car/handshake/first",method = RequestMethod.POST)
    public CommonMessage<Car> firstHandShake(@RequestParam("data")String data);

    @RequestMapping(value = "/car//handshake/second",method = RequestMethod.POST)
    public CommonMessage<Car> secondHandShake(@RequestParam("data")String data);

    @RequestMapping(value = "/car/message",method = RequestMethod.POST)
    public CommonMessage<EncryptMessage> sendMessage(@RequestParam("data")String data);
}
