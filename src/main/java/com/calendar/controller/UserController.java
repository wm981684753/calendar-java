package com.calendar.controller;

import com.calendar.domain.Calendar;
import com.calendar.domain.User;
import com.calendar.interceptor.LoginRequired;
import com.calendar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    @LoginRequired
    public User getUserInfo() {
        return userService.getMyUserInfo();
    }

    @PostMapping("/loginOrRegister")
    public Map<String, String> register(User user) {
        return userService.LoginOrRegister(user);
    }

    @PostMapping("/refreshToken")
    public Map<String, String> refreshToken(@RequestParam(value = "token",required = true) String token,
                                            @RequestParam(value = "refreshToken",required = true) String refreshToken) {
        return userService.refreshToken(token,refreshToken);
    }
}
