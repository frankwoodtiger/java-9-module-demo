package com.greeting;

import com.hello.HelloService;

import java.util.ServiceLoader;

public class GreetingServiceImpl implements GreetingService {
    private HelloService helloService;

    public GreetingServiceImpl() {
        ServiceLoader<HelloService> serviceLoader = ServiceLoader.load(HelloService.class);
        this.helloService = serviceLoader.iterator().next();
    }

    @Override
    public void greet(String name) {
        System.out.println(helloService.sayHello() + " " + name);
    }
}
