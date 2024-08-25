package io.github.hefrankeleyn.sc.provider.controller;

import io.github.hefrankeleyn.sc.api.UserServiceClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Date 2024/8/24
 * @Author lifei
 */
@RestController
@RequestMapping(value = "/userController")
public class UserController implements UserServiceClient {

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(@RequestParam("name") String name) {
        return "user list: " + name;
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public String find(@RequestParam("name") String name) {
        return "User is : " + name;
    }



}
