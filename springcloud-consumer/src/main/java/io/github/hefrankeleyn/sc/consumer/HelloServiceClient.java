package io.github.hefrankeleyn.sc.consumer;

import org.springframework.cloud.openfeign.FeignClient;


/**
 * @Date 2024/8/24
 * @Author lifei
 */
@FeignClient(value = "springcloud-provider", contextId = "providerClient")
public interface HelloServiceClient extends io.github.hefrankeleyn.sc.api.HelloServiceClient {

}
