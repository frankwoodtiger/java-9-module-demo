package com.greeting;

import com.hello.HelloService;
import com.hello.model.HelloMessageModel;

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

    @Override
    public void greet(HelloMessageModel messageModel) {
        System.out.println(helloService.sayHello(messageModel));
    }
}
