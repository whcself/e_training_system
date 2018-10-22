package com.csu.etrainingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class ETrainingSystemApplication {

    public static void main(String[] args) {


        SpringApplication.run(ETrainingSystemApplication.class, args);
    }
}
