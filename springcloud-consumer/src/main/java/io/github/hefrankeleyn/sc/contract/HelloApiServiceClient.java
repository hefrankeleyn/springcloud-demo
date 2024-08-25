package io.github.hefrankeleyn.sc.contract;

import io.github.hefrankeleyn.sc.api.service.HelloApiService;
import org.springframework.cloud.openfeign.FeignClient;


/**
 * @Date 2024/8/24
 * @Author lifei
 */
@FeignClient(value = "springcloud-provider", contextId = "providerClient")
public interface HelloApiServiceClient extends HelloApiService {

}
