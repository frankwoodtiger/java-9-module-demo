package com.greeting;

public class Main {
    public static void main(String[] args) {
        GreetingService greetingService = new GreetingServiceImpl();
        greetingService.greet("Chi");
    }
}