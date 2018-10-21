package com.csu.etrainingsystem.mylearn.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public static String index(){
        return "Hello world2";
    }
    @GetMapping("/hello2")
    public static String index2() throws Exception{
        throw new Exception("error");
    }
    @GetMapping("/hello3")
    public static String index3(){
        return "hello22a";
    }
    @RequestMapping("/json")
    public String json() throws Exception {
        throw new Exception("发生错误2");
    }

}
