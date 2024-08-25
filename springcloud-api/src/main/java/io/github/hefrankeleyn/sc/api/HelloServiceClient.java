package io.github.hefrankeleyn.sc.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Date 2024/8/24
 * @Author lifei
 */
public interface HelloServiceClient {

    @RequestMapping(value = "/helloController/hello", method = RequestMethod.GET)
    String hello();

    @RequestMapping(value = "/helloController/a", method = RequestMethod.GET)
    String a(@RequestParam("name") String name);

    @RequestMapping(value = "/helloController/b", method = RequestMethod.GET)
    String b(@RequestParam("name") String name);
}
