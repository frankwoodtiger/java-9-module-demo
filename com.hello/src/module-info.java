import com.hello.HelloService;
import com.hello.HelloServiceImpl;

module com.hello {
    exports com.hello;
    provides HelloService with HelloServiceImpl;
}