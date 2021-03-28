package com.echo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer //启用eureka服务端
public class CarEncryptEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarEncryptEurekaApplication.class, args);
    }

}
