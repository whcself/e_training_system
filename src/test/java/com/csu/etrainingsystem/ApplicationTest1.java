package com.csu.etrainingsystem;


import com.csu.etrainingsystem.mylearn.controller.HelloController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
    private MockMvc mvc;

    @Before//Methods annotated with the @Before annotation are executed before each test.
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new HelloController()).build();
    }

    @Test
    public void getHello() throws Exception {
        ResultActions hello_world = mvc.perform(MockMvcRequestBuilders.get("/hello").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Hello world"))); //throw Exception if not equal
        System.out.println("ss");

    }

    @Test
    public void whenCallingSayHello_thenReturnHello() {
        assertEquals("Hello world", HelloController.index());
    }
}
