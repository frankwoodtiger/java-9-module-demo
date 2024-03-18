package com.hello;

public class Main {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        System.out.println(helloService.sayHello());
    }
}
