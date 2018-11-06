package com.csu.etrainingsystem;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication // exclude: because it does not config the dataSource yet.
@EnableSwagger2
public class ETrainingSystemApplication {
    public static void main(String[] args) {
        BasicConfigurator.configure();
        SpringApplication.run(ETrainingSystemApplication.class, args);
    }

}