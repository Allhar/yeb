package com.allhar.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HelloController
 *
 * @author allhar
 * @since 1.0.0
 **/
@RestController
public class HelloController {

    @GetMapping("hello")
    public String hello(){return "hello";}

    @GetMapping("/employee/basic/hello")
    public String hello2(){
        return "/emp/basic/hello";
    }
    @GetMapping("/employee/advanced/hello")
    public String hello3(){
        return "/emp/adv/hello";
    }
}
