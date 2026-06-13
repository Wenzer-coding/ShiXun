package com.eutmp.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.eutmp.app.mapper")
public class EutmpApplication {

    public static void main(String[] args) {
        SpringApplication.run(EutmpApplication.class, args);
    }
}
