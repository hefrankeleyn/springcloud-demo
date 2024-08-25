# 简单演示springcloud的使用

## 一、目的

类似下面的下问题，非常多，它并不是bug，它就是这么设计的，多探讨一些类似的东西。
探讨的越多，再去追源码，就会对springcloud体系有更深的理解。


## 二、启动步骤


1. 下载nacos：https://github.com/alibaba/nacos/releases/tag/2.4.1
2. 将jdk切换为1.8，启动nacos；

```shell
    sh startup.sh -m standalone
```

3. 启动之后，jdk再切回到 17
4. 启动 springcloud-provider 项目；
5. 在nacos中查看“服务管理”->“服务列表”：http://localhost:8848/nacos；

## 三、内容

演示 springcloud openfegin 的使用：

1. fegin 不支持多接口继承,因为它要根据接口生成对应的bean；
2. spring cloud的服务是以单个微服务应用作为它所有管理的纬度；
   这样就导致， 在springcloud-consumer中写两个类，定义如下，会导致项目启动失败：

```java
@FeignClient(value = "springcloud-provider")
public interface HelloServiceClient extends io.github.hefrankeleyn.sc.api.service.HelloApiService {

}

@FeignClient(value = "springcloud-provider")
public interface UserServiceClient extends io.github.hefrankeleyn.sc.api.service.UserApiService {

}
```
报错信息为：
> The bean 'springcloud-provider.FeignClientSpecification' could not be registered. A bean with that name has already been defined and overriding is disabled.

一个比较好的解决方案是：添加 contextId

```java
@FeignClient(value = "springcloud-provider", contextId = "providerClient")
public interface HelloServiceClient extends io.github.hefrankeleyn.sc.api.service.HelloApiService {

}

@FeignClient(value = "springcloud-provider", contextId = "userClient")
public interface UserServiceClient extends io.github.hefrankeleyn.sc.api.service.UserApiService {

}
```

## 四、针对上面问题，更完整的回答

这个问题的原因是，在 Spring Cloud 中使用 OpenFeign 时，由于两个不同的接口 `HelloServiceClient` 和 `UserServiceClient` 都使用了相同的 `value` 属性（即 `"springcloud-provider"`），它们在 Spring 容器中会被识别为相同的 bean 名称，即 `springcloud-provider.FeignClientSpecification`。由于 Spring 默认不允许覆盖已有的 bean，这就导致了 bean 重复定义的错误。

要解决这个问题，可以考虑以下几种方法：

### 方法一：为每个 FeignClient 指定不同的 `contextId`
可以通过为每个 `@FeignClient` 注解指定不同的 `contextId` 来解决这个问题。`contextId` 是 Feign 客户端的唯一标识符。

```java
@FeignClient(value = "springcloud-provider", contextId = "helloServiceClient")
public interface HelloServiceClient extends io.github.hefrankeleyn.sc.api.service.HelloApiService {

}

@FeignClient(value = "springcloud-provider", contextId = "userServiceClient")
public interface UserServiceClient extends io.github.hefrankeleyn.sc.api.service.UserApiService {

}
```

### 方法二：为每个 FeignClient 指定不同的 `name`
另一种方法是为每个 `@FeignClient` 注解指定不同的 `name`。`name` 属性与 `value` 属性的作用类似，也可以用来区分不同的 Feign 客户端。

```java
@FeignClient(name = "helloServiceClient", value = "springcloud-provider")
public interface HelloServiceClient extends io.github.hefrankeleyn.sc.api.service.HelloApiService {

}

@FeignClient(name = "userServiceClient", value = "springcloud-provider")
public interface UserServiceClient extends io.github.hefrankeleyn.sc.api.service.UserApiService {

}
```

### 方法三：启用 Bean 覆盖（不推荐）
可以通过启用 Spring 的 bean 覆盖功能来解决这个问题，不过这通常不是推荐的做法，因为它可能导致其他问题。你可以在 Spring 的配置文件中启用 bean 覆盖功能：

```yaml
spring:
  main:
    allow-bean-definition-overriding: true
```

或者在代码中通过 `@SpringBootApplication` 注解的属性来启用：

```java
@SpringBootApplication
public class MyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
```

在大多数情况下，方法一或方法二是解决这个问题的最佳方式。


## 四、最佳实践

### （1）使用接口契约

最佳实践：使用接口契约，来约束springcloud-provider  和 springcloud-consumer

### （2）对请求报文、响应报文进行约束

好处：
1. 假如返回出错，可以用封装的通用报文来描述这个错误；
2. 便于在开发阶段，进行Mock报文；
3. 有利于做服务编排；
4. 如果业务规模比较大，有几千个这样的服务，所有服务基础代码都是一样的，可以有一个base服务去描述它们的共享；

报文放在什么地方？定义出一个新的服务：springcloud-common。


### （3） 定义服务元信息，有利于实现负载策略
如何在注册中心中，标识这个服务的元信息（哪个机房）：
1. 标识 服务（provider和consumer） 的元信息；
2. 让大家相互之间能感知到；
做法：
1. 在启动时候，添加环境变量，补充这些信息；
2. 拦截注册中心的注册方法，把这些信息写到注册信息的节点数据上去；
   传统的做法是在配置文件中配置： `pring.cloud.nacos.discovery.metadata.dc = B001`
   但我们要让其跟启动节点相关，跟通用信息不相关，所以只需要在每个机房部署这些信息的时候，把这些脚本变量改成不一样的就行，这样就不用在配置文件中配置了。
3. 这样，在客户端，就能获取到这些信息；

负载均衡策略：
1. 方案一：可以使用spring默认的策略：
   https://docs.spring.io/spring-cloud-commons/docs/current/reference/html/#spring-cloud-loadbalancer
2. 方案二：使用自定义的策略，只需要在spring容器中装配一个： ReactorServiceInstanceLoadBalancer 的bean；
   这样SpringCloud就会优先使用我们定义的这个；
3. 在springcloud-openFeign中指定使用哪个策略：@FeginClient中有一个 configuration参数；

### （4）springcloud 里面其它内容

1. spring.cloud.loadbalancer.retry;
2. eager-load 用来做服务预热；

## 五、技术组件

技术组件：
技术专项：通用报文的设计。


## 六、探索的过程远比探索的结果重要

1. 约定大于配置；
2. 方向：大规模、大并发下的最佳实践；
3. 探讨目标：这块有多少路、这路条如何设计和落地，这个探讨的过程更重要，比最后的结果还重要；
   掌握探索过程的经验和方法。
