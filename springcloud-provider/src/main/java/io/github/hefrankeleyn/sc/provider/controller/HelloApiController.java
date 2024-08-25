package io.github.hefrankeleyn.sc.provider.controller;

import com.alibaba.nacos.shaded.com.google.common.base.Strings;
import io.github.hefrankeleyn.sc.api.service.HelloApiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Date 2024/8/24
 * @Author lifei
 */
@RestController
public class HelloApiController implements HelloApiService {

    @Value("${server.port}")
    private Integer port;

    @Override
    public String hello() {
        return Strings.lenientFormat("hello springcloud, port:  %s",  port);
    }

    @Override
    public String a(String name) {
        return Strings.lenientFormat("hello a: %s, port: %s", name, port);
    }

    @Override
    public String b(String name) {
        return Strings.lenientFormat("hello b: %s, port: %s", name, port);
    }


}
