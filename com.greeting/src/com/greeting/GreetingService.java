package com.greeting;

import com.hello.model.HelloMessageModel;

public interface GreetingService {
    void greet(String name);
    void greet(HelloMessageModel messageModel);
}
