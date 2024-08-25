package io.github.hefrankeleyn.sc.contract;

import io.github.hefrankeleyn.sc.api.service.UserApiService;
import org.springframework.cloud.openfeign.FeignClient;


/**
 * @Date 2024/8/24
 * @Author lifei
 */
@FeignClient(value = "springcloud-provider", contextId = "userClient")
public interface UserApiServiceClient extends UserApiService {

}
