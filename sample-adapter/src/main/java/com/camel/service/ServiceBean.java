package com.camel.service;
import com.camel.dto.User;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.springframework.stereotype.Component;

@Component
public class ServiceBean {

//    @Handler
    public User getUser(User user) {
        user.name = user.name.toUpperCase();
        return user;
    }

    public String toUpper(User user) {
        return user.name.toUpperCase();
    }

    public void doSomething(Exchange exchange) {
        exchange.getIn().setBody("Bye World");
    }

    public void beanBinding(String payload, boolean highPriority) {
//        .bean(OrderService.class, "doSomething(*, true)")
//        .bean(OrderService.class, "doSomething(${body}, true)")
//        .to("bean:orderService?method=doSomething(null, true)")
//        .bean(MyBean.class, "hello(String)") //overload method
//        .bean(OrderService.class, "doSomething(com.foo.MyOrder)")
//        .bean(OrderService.class, "doSomething(${body.asXml}, ${header.high})")
    }

//    public User response(User user) {
////        user.name = user.name.toUpperCase();
//        return user;
//    }
}