package com.csu.etrainingsystem;


import com.csu.etrainingsystem.mylearn.controller.HelloController;
import com.csu.etrainingsystem.overwork.entity.Overwork;
import com.csu.etrainingsystem.overwork.service.OverworkService;
import com.csu.etrainingsystem.util.ExcelPort;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)// use the junit4
@SpringBootTest(classes = MockServletContext.class)
@WebAppConfiguration
//a class-level annotation that is used to declare that the ApplicationContext loaded for an integration test should be a WebApplicationContext.
public class ApplicationTest1 {

    @Mock
    private OverworkService overworkService;

    public ApplicationTest1(){}


    @Test
    public void testOverworkService(){
        List<Overwork> overworks=overworkService.getOverworkByTimeOrProName("2018-10-10","2018-12-12",null);
        System.out.println(overworks.size());
        for(Overwork overwork:overworks){
            System.out.println(overwork.getOverwork_time()+" "+overwork.getOverwork_time_end());
        }
    }
}
