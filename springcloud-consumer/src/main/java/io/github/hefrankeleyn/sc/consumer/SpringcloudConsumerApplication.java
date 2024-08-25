package io.github.hefrankeleyn.sc.consumer;

import com.alibaba.nacos.shaded.com.google.common.base.Strings;
import io.github.hefrankeleyn.sc.api.service.UserApiService;
import io.github.hefrankeleyn.sc.contract.HelloApiServiceClient;
import io.github.hefrankeleyn.sc.contract.UserApiServiceClient;
import jakarta.annotation.Resource;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"io.github.hefrankeleyn.sc.contract"})
public class SpringcloudConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudConsumerApplication.class, args);
	}

	@Resource
	private HelloApiServiceClient helloServiceClient;

	@Resource
	private UserApiServiceClient userApiServiceClient;

	@Bean
	public ApplicationRunner runner() {
		return args -> {
			System.out.println("===============> helloServiceClient begin.....");
			String hello = helloServiceClient.hello();
			System.out.println(Strings.lenientFormat("===> openFegin hello: %s", hello));
			String tw = helloServiceClient.a("tw");
			System.out.println(Strings.lenientFormat("===> openFegin a: %s", tw));
			String lf = helloServiceClient.b("lf");
			System.out.println(Strings.lenientFormat("===> openFegin b: %s", lf));
			System.out.println("===============> helloServiceClient end.....");

			System.out.println("================> userServiceClient begin....");
			String userClient = userApiServiceClient.list("user client");
			System.out.println("===> openFegin user.list: " + userClient);
			String world = userApiServiceClient.find("world");
			System.out.println(Strings.lenientFormat("====> openFegin user.find: %s", world));
			System.out.println("================> userServiceClient end....");
		};
	}
}
