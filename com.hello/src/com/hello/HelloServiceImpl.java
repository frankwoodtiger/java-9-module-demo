package com.hello;

import com.hello.model.HelloMessageModel;

public class HelloServiceImpl implements  HelloService {
    @Override
    public String sayHello() {
        return "Hello!";
    }

    @Override
    public String sayHello(HelloMessageModel messageModel) {
        return messageModel.getMessage() + " on " + messageModel.getDate();
    }
}
