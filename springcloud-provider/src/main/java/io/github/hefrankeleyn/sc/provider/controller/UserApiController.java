package io.github.hefrankeleyn.sc.provider.controller;

import com.alibaba.nacos.shaded.com.google.common.base.Strings;
import io.github.hefrankeleyn.sc.api.service.UserApiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Date 2024/8/24
 * @Author lifei
 */
@RestController
public class UserApiController implements UserApiService {

    @Value("${server.port}")
    private Integer port;

    @Override
    public String list(String name) {
        return Strings.lenientFormat("user list: %s, port: %s", name, port);
    }

    @Override
    public String find(String name) {
        return Strings.lenientFormat("User is: %s, port: %s", name, port);
    }



}
