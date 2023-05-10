package com.calendar.interceptor;

import com.calendar.domain.User;
import com.calendar.enums.StatusCodeEnum;
import com.calendar.exception.AuthException;
import com.calendar.utils.ThreadLocalUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.header}")
    private String header;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        // 如果方法上有注解，检查用户是否已经登录
        if (method.isAnnotationPresent(LoginRequired.class)) {

            // 从请求头中获取Token
            String token = request.getHeader(header);
            // 检查Token是否为空
            if (token == null || token.isEmpty()) {
                throw new AuthException(StatusCodeEnum.SC401.getCode(),"token为空");
            } else {
                try {
                    // 验证Token，解密获得subject，即用户ID
                    Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
                    request.setAttribute("token", token);
                    //存用户信息
                    User userInfo = new User(
                            claims.get("userId", Integer.class),
                            claims.get("open_id", String.class)
                    );
                    ThreadLocalUtils.setThreadCache("userInfo",userInfo);
                    return true;
                } catch (Exception e) {
                    // Token解析失败，抛出异常
                    throw new AuthException(StatusCodeEnum.SC401.getCode(),"token解析失败");
                }
            }
        } else {
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
