package com.hello;

import com.hello.model.HelloMessageModel;

public interface HelloService {
    String sayHello();
    String sayHello(HelloMessageModel messageModel);
}
