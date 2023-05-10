package com.calendar.exception;

import com.calendar.enums.StatusCodeEnum;
import com.calendar.utils.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 捕获其他异常
     */
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(AuthException.class)
    public ApiResult<String> handle(AuthException e) {
        log.error("全局异常信息：{}", e.getMessage());
        return ApiResult.fail(AuthException.getCode(), StatusCodeEnum.SC500.getMsg() + "：" + e.getMessage());
    }
}

