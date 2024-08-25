package io.github.hefrankeleyn.sc.provider.controller;

import io.github.hefrankeleyn.sc.api.HelloServiceClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Date 2024/8/24
 * @Author lifei
 */
@RestController
@RequestMapping(value = "/helloController")
public class HelloController implements HelloServiceClient {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "hello springcloud";
    }

    @RequestMapping(value = "/a", method = RequestMethod.GET)
    public String a(@RequestParam("name") String name) {
        return "hello a: " + name;
    }

    @RequestMapping(value = "/b", method = RequestMethod.GET)
    public String b(@RequestParam("name") String name) {
        return "hello b: " + name;
    }


}
