package com.nahush.springboot.firstrestapi.helloworld;

import jakarta.websocket.server.PathParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @RequestMapping("hello-world")
    public String helloWorld(){
        return "Hello World";
    }

    @RequestMapping("hello-world-bean")
    public HelloWorldBean helloWorldBean(){
        return new HelloWorldBean("Hello world bean!");
    }

    @RequestMapping("hello-world-path-param/{name}")
    public HelloWorldBean helloWorldPathParam(@PathVariable String name){
        return new HelloWorldBean("Hello world " + name + "!");
    }

    @RequestMapping("hello-world-path-param/{name}/{msg}")
    public HelloWorldBean helloWorldPathParamMultiple(@PathVariable String name, @PathVariable String msg){
        return new HelloWorldBean("Hello world " + name + "!" + msg);
    }
}

