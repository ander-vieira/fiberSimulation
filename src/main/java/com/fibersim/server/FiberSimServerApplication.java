package com.fibersim.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.fibersim")
public class FiberSimServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(FiberSimServerApplication.class, args);
    }
}