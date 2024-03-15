package com.adaptiveapp.hestia;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(scanBasePackages = {"com.adaptiveapp.hestia"})
@MapperScan("com.adaptiveapp.hestia.dal")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class HestiaApplication {

    public static void main(String[] args) {
        SpringApplication.run(HestiaApplication.class, args);
    }

}
