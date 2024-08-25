package io.github.hefrankeleyn.sc.common;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @Date 2024/8/25
 * @Author lifei
 */
public class BaseRequest<T> {

    // 还可以考虑，把这里面一些字段放到基础类里：BaseEntity
    // 从哪个应用发起
    private Integer appId;
    // 要请求哪个系统
    private Integer targetAppId;
    // 微服务ID
    private Integer serviceId;
    // 核心客户号
    private Long customId;

    // 甚至可以加一些通用域没有的
    private String traceId;
    private String spanId;

    // 扩展域
    private Map<String, Object> headers = Maps.newLinkedHashMap();
    private Map<String, Object> properties = Maps.newLinkedHashMap();

    // 私有域
    private T data;

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public Integer getTargetAppId() {
        return targetAppId;
    }

    public void setTargetAppId(Integer targetAppId) {
        this.targetAppId = targetAppId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Long getCustomId() {
        return customId;
    }

    public void setCustomId(Long customId) {
        this.customId = customId;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getSpanId() {
        return spanId;
    }

    public void setSpanId(String spanId) {
        this.spanId = spanId;
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, Object> headers) {
        this.headers = headers;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(BaseRequest.class)
                .add("appId", appId)
                .add("targetAppId", targetAppId)
                .add("serviceId", serviceId)
                .add("customId", customId)
                .add("traceId", traceId)
                .add("spanId", spanId)
                .add("headers", headers)
                .add("properties", properties)
                .add("data", data)
                .toString();
    }
}
