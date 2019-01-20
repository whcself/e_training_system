package com.csu.etrainingsystem;


import com.csu.etrainingsystem.mylearn.controller.HelloController;
import com.csu.etrainingsystem.overwork.entity.Overwork;
import com.csu.etrainingsystem.overwork.service.OverworkService;
import com.csu.etrainingsystem.util.CookieUtils;
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

    @Test
    public void testSystem(){
        System.out.println(System.currentTimeMillis());
    }
    @Test
    public void testurl(){
        String domainName = null;
        // 获取完整的请求URL地址。
        String serverName ="http://gxxt.runtofuture.cn/";
        if (serverName == null || serverName.equals("")) {
            domainName = "";
        } else {
            serverName = serverName.toLowerCase();
            if (serverName.startsWith("http://")){
                serverName = serverName.substring(7);
            } else if (serverName.startsWith("https://")){
                serverName = serverName.substring(8);
            }
            final int end = serverName.indexOf("/");
            // .test.com  www.test.com.cn/sso.test.com.cn/.test.com.cn  spring.io/xxxx/xxx
            serverName = serverName.substring(0, end);
            final String[] domains = serverName.split("\\.");
            int len = domains.length;
            if (len > 3) {
                domainName = "." + domains[len - 3] + "." + domains[len - 2] + "." + domains[len - 1];
            } else if (len <= 3 && len > 1) {
                domainName = "." + domains[len - 2] + "." + domains[len - 1];
            } else {
                domainName = serverName;
            }
        }

        if (domainName != null && domainName.indexOf(":") > 0) {
            String[] ary = domainName.split("\\:");
            domainName = ary[0];
        }
        System.out.println (domainName);
    }
}
