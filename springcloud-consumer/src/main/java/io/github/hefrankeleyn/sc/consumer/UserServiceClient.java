package io.github.hefrankeleyn.sc.consumer;

import org.springframework.cloud.openfeign.FeignClient;


/**
 * @Date 2024/8/24
 * @Author lifei
 */
@FeignClient(value = "springcloud-provider", contextId = "userClient")
public interface UserServiceClient extends io.github.hefrankeleyn.sc.api.UserServiceClient {

}
