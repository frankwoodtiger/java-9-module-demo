import com.hello.HelloService;
import com.hello.HelloServiceImpl;

module com.hello {
    exports com.hello;

    // although com.hello.model is nested package of com.hello, we still need to
    // expose nested package explicitly if we want that to be available outside world
    exports com.hello.model;

    provides HelloService with HelloServiceImpl;
}