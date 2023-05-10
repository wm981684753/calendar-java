package com.calendar.service;

import com.calendar.domain.User;
import com.calendar.utils.JwtUtils;
import com.calendar.utils.ThreadLocalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseService {
    @Autowired
    private JwtUtils jwtUtils;

    protected User getUserInfo() {
        return (User) ThreadLocalUtils.getThreadCache("userInfo");
    }
}
