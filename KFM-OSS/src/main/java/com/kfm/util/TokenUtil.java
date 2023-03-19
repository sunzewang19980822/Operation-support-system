package com.kfm.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kfm.exception.TokenAuthExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class TokenUtil {
    @Value("${token.privateKey}")//JWT的私钥
    private  volatile String privateKey;
    @Value("${token.expires}")//超期时间(小时单位)
    private  volatile int expires;

    /**
     * 加密token.
     */
    public  String getToken(String userId, String userRole) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, expires);//
        //这个是放到负载payLoad 里面,魔法值可以使用常量类进行封装.
        String token = JWT
                .create()
                .withClaim("userId" ,userId)//自定义在和
                .withClaim("userRole", userRole)
                .withClaim("timeStamp", System.currentTimeMillis())
                .withExpiresAt(calendar.getTime())  // 过期时间
                .sign(Algorithm.HMAC256(privateKey));
        return token;
    }

    /**
     * 解析token.
     * (优化可以用常量固定魔法值+使用DTO在 mvc 之前传输数据，而不是 map)
     * {
     * "userId": "3412435312",
     * "userRole": "ROLE_USER",
     * "timeStamp": "134143214"
     * }
     */
    public Map<String, String> parseToken(String token)  {
        log.debug(token);
        if(token==null){
            throw new TokenAuthExpiredException();
        }
        if(token.startsWith("Bearer ")){
            token=token.substring(7);
        }
        log.debug(token);
        HashMap<String, String> map = new HashMap<>();
        try {
            DecodedJWT decodedjwt = JWT.require(Algorithm.HMAC256(privateKey))
                    .build().verify(token);
            Claim userId = decodedjwt.getClaim("userId");
            Claim userRole = decodedjwt.getClaim("userRole");
            Claim timeStamp = decodedjwt.getClaim("timeStamp");
            map.put("userId", userId.asString());
            map.put("userRole", userRole.asString());
            map.put("timeStamp", timeStamp.asLong().toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new TokenAuthExpiredException();
        }
        return map;
    }
}
