package com.kfm.interceptor;

import com.kfm.exception.TokenAuthExpiredException;
import com.kfm.service.UserService;
import com.kfm.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@Component
public class AuthHandlerInterceptor implements HandlerInterceptor {
    @Autowired
    private TokenUtil tokenUtil;
    @Value("${token.privateKey}")
    private String privateKey;
    @Value("${token.youngToken}")
    private Long youngToken;
    @Value("${token.oldToken}")
    private Long oldToken;
    @Autowired
    private UserService userService;

    /**
     * 权限认证的拦截操作.
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        // 如果不是映射到方法直接通过,可以访问资源.
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        //为空就返回错误
        String token = httpServletRequest.getHeader("authorization");
        if (null == token || "".equals(token.trim())) {
            throw new TokenAuthExpiredException();
        }
        log.debug("==============token:" + token);
        Map<String, String> map = tokenUtil.parseToken(token);
        String userId = map.get("userId");
        String userRole = map.get("userRole");
        long timeOfUse = System.currentTimeMillis() - Long.parseLong(map.get("timeStamp"));
        //1.判断 token 是否过期
        //年轻 token
        if (timeOfUse < youngToken  * 3600000) {
            log.debug("年轻 token");
        } else if (timeOfUse >= youngToken * 3600000 && timeOfUse < oldToken  * 3600000) {//老年 token 就刷新 token
            httpServletResponse.setHeader("token", tokenUtil.getToken(userId, userRole));//续签了Token
        }
        //过期 token 就返回 token 无效.
        else {
            throw new TokenAuthExpiredException();
        }

        //2.角色匹配.
//        if ("user".equals(userRole)) {
//            log.debug("========user账户============");
//            return true;
//        }
//        if ("admin".equals(userRole)) {
//            log.debug("========admin账户============");
//            return true;
//        }
//        return false;
         if(StringUtils.isEmpty(userRole)){
            return false;
        }
        return true;
    }
}
