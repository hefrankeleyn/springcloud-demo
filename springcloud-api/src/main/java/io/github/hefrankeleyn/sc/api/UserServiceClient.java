package io.github.hefrankeleyn.sc.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Date 2024/8/24
 * @Author lifei
 */
public interface UserServiceClient {

    @RequestMapping(value = "/userController/list", method = RequestMethod.GET)
    String list(@RequestParam("name") String name);

    @RequestMapping(value = "/userController/find", method = RequestMethod.GET)
    String find(@RequestParam("name") String name);
}
