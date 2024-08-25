# 简单演示springcloud的使用

## 一、目的

类似下面的下问题，非常多，它并不是bug，就是它就是这么设计的，多谈论一些类似的东西。
谈论的越多，再去追源码，就会对springcloud体系有更深的理解。

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
public interface HelloServiceClient extends io.github.hefrankeleyn.sc.api.HelloServiceClient {

}

@FeignClient(value = "springcloud-provider")
public interface UserServiceClient extends io.github.hefrankeleyn.sc.api.UserServiceClient {

}
```
报错信息为：
> The bean 'springcloud-provider.FeignClientSpecification' could not be registered. A bean with that name has already been defined and overriding is disabled.

一个比较好的解决方案是：添加 contextId

```java
@FeignClient(value = "springcloud-provider", contextId = "providerClient")
public interface HelloServiceClient extends io.github.hefrankeleyn.sc.api.HelloServiceClient {

}

@FeignClient(value = "springcloud-provider", contextId = "userClient")
public interface UserServiceClient extends io.github.hefrankeleyn.sc.api.UserServiceClient {

}
```

## 四、针对上面问题，更完整的回答

这个问题的原因是，在 Spring Cloud 中使用 OpenFeign 时，由于两个不同的接口 `HelloServiceClient` 和 `UserServiceClient` 都使用了相同的 `value` 属性（即 `"springcloud-provider"`），它们在 Spring 容器中会被识别为相同的 bean 名称，即 `springcloud-provider.FeignClientSpecification`。由于 Spring 默认不允许覆盖已有的 bean，这就导致了 bean 重复定义的错误。

要解决这个问题，可以考虑以下几种方法：

### 方法一：为每个 FeignClient 指定不同的 `contextId`
可以通过为每个 `@FeignClient` 注解指定不同的 `contextId` 来解决这个问题。`contextId` 是 Feign 客户端的唯一标识符。

```java
@FeignClient(value = "springcloud-provider", contextId = "helloServiceClient")
public interface HelloServiceClient extends io.github.hefrankeleyn.sc.api.HelloServiceClient {

}

@FeignClient(value = "springcloud-provider", contextId = "userServiceClient")
public interface UserServiceClient extends io.github.hefrankeleyn.sc.api.UserServiceClient {

}
```

### 方法二：为每个 FeignClient 指定不同的 `name`
另一种方法是为每个 `@FeignClient` 注解指定不同的 `name`。`name` 属性与 `value` 属性的作用类似，也可以用来区分不同的 Feign 客户端。

```java
@FeignClient(name = "helloServiceClient", value = "springcloud-provider")
public interface HelloServiceClient extends io.github.hefrankeleyn.sc.api.HelloServiceClient {

}

@FeignClient(name = "userServiceClient", value = "springcloud-provider")
public interface UserServiceClient extends io.github.hefrankeleyn.sc.api.UserServiceClient {

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