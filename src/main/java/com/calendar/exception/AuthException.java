package com.calendar.exception;

import org.springframework.stereotype.Component;

public class AuthException extends RuntimeException{
    // 返回的状态码
    private static Integer code;

    public AuthException(Integer code,String msg){
        super(msg);
        AuthException.code = code;
    }

    public static Integer getCode() {
        return code;
    }
}
