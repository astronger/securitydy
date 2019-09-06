package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2019/9/5.
 */
@RestController
public class HelloController {

    @GetMapping("/hello") //测试可直接进入
    public String hello(){
        return "hello";
    }
    @GetMapping("/db/hello")
    public String db(){
        return "hello db";
    }
    @GetMapping("/admin/hello")
    public String admin(){
        return "hello admin";
    }
    @GetMapping("/user/hello")
    public String user(){
        return "hello user";
    }
}
