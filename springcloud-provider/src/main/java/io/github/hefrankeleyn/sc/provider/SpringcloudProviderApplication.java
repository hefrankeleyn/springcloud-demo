package io.github.hefrankeleyn.sc.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

// 新版本不用加
//@EnableDiscoveryClient
@SpringBootApplication
public class SpringcloudProviderApplication {

	/*
	1. 下载nacos：https://github.com/alibaba/nacos/releases/tag/2.4.1
	2. 将jdk切换为1.8，启动nacos；
	    sh startup.sh -m standalone
	3.   启动之后，jdk再切回来.
	4. 启动项目，访问
	     http://localhost:8848/nacos
	 */

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudProviderApplication.class, args);
	}

}
