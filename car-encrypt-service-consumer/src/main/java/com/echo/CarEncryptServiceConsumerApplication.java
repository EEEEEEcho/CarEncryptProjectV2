package com.echo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CarEncryptServiceConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarEncryptServiceConsumerApplication.class, args);
    }

}
