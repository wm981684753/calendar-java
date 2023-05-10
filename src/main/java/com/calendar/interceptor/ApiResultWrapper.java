package com.calendar.interceptor;

import com.alibaba.fastjson.JSON;
import com.calendar.utils.ApiResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class ApiResultWrapper implements ResponseBodyAdvice<Object> {

    /**
     * 是否支持advice功能
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    /**
     * 对返回的数据进行处理
     */
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (o instanceof String) {
            return JSON.toJSONString(ApiResult.success(o));
        }
        // 这个判断的作用：防止全局异常处理后返回的结果（类型为ApiResult）再次被包装
        if (o instanceof ApiResult) {
            return o;
        }
        return ApiResult.success(o);
    }

}

