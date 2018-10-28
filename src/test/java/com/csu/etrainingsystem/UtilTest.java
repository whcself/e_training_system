package com.csu.etrainingsystem;

import com.csu.etrainingsystem.util.ExcelPort;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)// use the junit4
@SpringBootTest(classes = MockServletContext.class)
@WebAppConfiguration
public class UtilTest {
}

