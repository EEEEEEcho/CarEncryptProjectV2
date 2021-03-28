package com.echo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy    //启用zuul网关组件
@EnableEurekaClient    //启用Eureka客户端
public class CarEncryptZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarEncryptZuulApplication.class, args);
    }

}
