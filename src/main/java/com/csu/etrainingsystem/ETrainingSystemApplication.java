package com.csu.etrainingsystem;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

//(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication
@EnableScheduling
@EnableCaching
@EnableAsync
@EnableSwagger2
public class ETrainingSystemApplication {
    public static void main(String[] args) {
      //  BasicConfigurator.configure(); //自动快速地使用缺省Log4j环境。
        SpringApplication.run(ETrainingSystemApplication.class, args);
        //add oneline code
    }

//    @PostConstruct
//    void setDefaultTimezone() {
//        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
//    }


}