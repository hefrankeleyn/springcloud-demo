package io.github.hefrankeleyn.sc.provider.aop;

import com.alibaba.nacos.shaded.com.google.common.base.Strings;
import jakarta.annotation.Resource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.rmi.registry.Registry;

/**
 * @Date 2024/8/25
 * @Author lifei
 */
@Aspect
@Component
public class ServiceRegisterAspect {
    // 机房
    private static final String DC = "dc";
    // 地区
    private static final String ZONE = "zone";
    // 环境
    private static final String ENV = "env";
    // group/namespce
    private static final String GROUP = "group";

    @Resource
    private Environment environment;

    @Before("execution(* org.springframework.cloud.client.serviceregistry.ServiceRegistry.register(..))")
    public void beforeRegister(JoinPoint joinPoint) {
        String dc = environment.getProperty(DC, "BJ");
        String zone = environment.getProperty(ZONE, "B001");
        String env = environment.getProperty(ENV, "DEV");
        String group = environment.getProperty(GROUP, "DEV");
        System.out.println(Strings.lenientFormat("ServiceRegisterAspect.beforeRegister for dc/zone/env/group: %s/%s/%s/%s", dc, zone, env, group));
        Object[] args = joinPoint.getArgs();
        for (Object o : args) {
            if (o instanceof Registration registration) {
                registration.getMetadata().put(DC, dc);
                registration.getMetadata().put(ZONE, zone);
                registration.getMetadata().put(ENV, env);
                registration.getMetadata().put(GROUP, group);
            }
        }
    }
}
