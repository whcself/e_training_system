package com.csu.etrainingsystem.redis;

import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.redis.dao.impl.JedisDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisTestController {
    @Autowired
    private JedisDaoImpl jedisDao;
    @PostMapping(value = "/test")
    public CommonResponseForm test(){
        return CommonResponseForm.of200 ("查询结果:",jedisDao.get ("whc"));
    }

}
