package com.calendar.service;

import com.calendar.domain.Calendar;
import com.calendar.domain.User;
import com.calendar.enums.StatusCodeEnum;
import com.calendar.exception.AuthException;
import com.calendar.repository.CalendarRepository;
import com.calendar.repository.UserRepository;
import com.calendar.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService extends BaseService{

    @Autowired
    private UserRepository userRepository;

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CalendarRepository calendarRepository;

    public User getMyUserInfo() {
        Integer userId = this.getUserInfo().getId();
        return userRepository.findById(userId).orElse(null);
    }

    public List<User> getUserByOpenId(String openId) {
        return userRepository.findByOpenId(openId);
    }

    public Map<String, String> LoginOrRegister(User user) {

        List<User> issetUser = this.getUserByOpenId(user.getOpenId());
        User userInfo=null;
        if(issetUser != null && !issetUser.isEmpty()){
            userInfo = issetUser.get(0);
        }else{
            // TODO: add user to db
            userInfo = userRepository.save(user);
        }

        String token = jwtUtils.generateToken(userInfo);
        String refreshToken = jwtUtils.refreshToken(userInfo);
        Map<String,String> generateToken = new HashMap<>();
        generateToken.put("token",token);
        generateToken.put("refreshToken",refreshToken);

        return generateToken;
    }

    public Map<String,String> refreshToken(String token,String refreshToken){
        try {
            // 验证Token，解密获得subject，即用户ID
            Claims tokenClaims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            //说明token还没有过期，直接返回
            Map<String,String> result = new HashMap<>();
            result.put("token",token);
            result.put("refreshToken",refreshToken);
            return result;
        } catch (ExpiredJwtException e){
            try {
                String tokenOpenId = e.getClaims().get("open_id", String.class);
                Claims refreshTokenClaims = Jwts.parser().setSigningKey(secret).parseClaimsJws(refreshToken).getBody();
                String refreshTokenOpenId = refreshTokenClaims.get("open_id", String.class);
                System.out.println("tokenOpenId:"+tokenOpenId+"refreshTokenOpenId:"+refreshTokenOpenId);
                if (tokenOpenId.equals(refreshTokenOpenId)){
                    //重新生成token
                    User userInfo = this.getUserByOpenId(tokenOpenId).get(0);
                    Map<String,String> generateToken = new HashMap<>();
                    generateToken.put("token",jwtUtils.generateToken(userInfo));
                    generateToken.put("refreshToken",refreshToken);
                    return generateToken;
                }else{
                    throw new AuthException(StatusCodeEnum.SC401.getCode(), "非法token");
                }
            }catch (ExpiredJwtException re){
                //如果refreshToken也过期了，则直接返回401
                throw new AuthException(StatusCodeEnum.SC401.getCode(), e.getMessage());
            }
        } catch (Exception e){
            throw new AuthException(StatusCodeEnum.SC401.getCode(), e.getMessage());
        }
    }
}

