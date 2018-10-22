package com.csu.etrainingsystem;

import com.csu.etrainingsystem.component.SystemProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ETrainingSystemApplicationTests {

    @Autowired
    private SystemProperties systemProperties;

    @Test
    public void contextLoads() {
    }

    @Test
    public void properties(){
        System.out.println(systemProperties.getName());
    }

}


