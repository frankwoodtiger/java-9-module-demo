package com.greeting;

import com.hello.model.HelloMessageModel;

public class Main {
    public static void main(String[] args) {
        GreetingService greetingService = new GreetingServiceImpl();
        greetingService.greet("Chi");
        greetingService.greet(new HelloMessageModel("Greeting!"));
    }
}