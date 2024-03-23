package com.hello;

import com.hello.model.HelloMessageModel;

public class Main {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        System.out.println(helloService.sayHello());
        System.out.println(helloService.sayHello(new HelloMessageModel("Greeting!")));
    }
}
