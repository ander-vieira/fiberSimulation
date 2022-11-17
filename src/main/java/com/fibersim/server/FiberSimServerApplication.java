package com.fibersim.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = "com.fibersim")
@EnableAsync
public class FiberSimServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(FiberSimServerApplication.class, args);
    }
}