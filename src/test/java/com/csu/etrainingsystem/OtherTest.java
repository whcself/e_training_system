package com.csu.etrainingsystem;


import com.csu.etrainingsystem.component.SystemProperties;
import com.csu.etrainingsystem.config.MainConfig;
import javafx.application.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLOutput;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes={SystemProperties.class})
public class OtherTest {
    @Autowired
    private SystemProperties system;

    @Test
    public void test01(){
        AnnotationConfigApplicationContext context= new AnnotationConfigApplicationContext(MainConfig.class);
        String names[]=context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
        System.out.println(system.getName());
    }
}
