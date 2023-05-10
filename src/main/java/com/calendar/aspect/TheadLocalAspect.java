package com.calendar.aspect;

import com.calendar.utils.ThreadLocalUtils;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TheadLocalAspect {

    @After(
            "@annotation(org.springframework.web.bind.annotation.RequestMapping) || " +
                    "@annotation(org.springframework.web.bind.annotation.GetMapping) || " +
                    "@annotation(org.springframework.web.bind.annotation.PostMapping)"
    )
    public void after(){
        ThreadLocalUtils.removeThreadCache();
    }
}

