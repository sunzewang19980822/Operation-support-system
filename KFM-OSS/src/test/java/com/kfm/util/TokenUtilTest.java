package com.kfm.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.NONE)
class TokenUtilTest {
    @Autowired
    private TokenUtil tokenUtil;
    @Test
    void getToken() {
        System.out.println(tokenUtil.getToken("101", "john"));
    }

    @Test
    void parseToken() {
        String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0aW1lU3RhbXAiOjE2NzAyODgzMzI5MjMsInVzZXJSb2xlIjoiam9obiIsImV4cCI6MTY3MDMzMTUzMiwidXNlcklkIjoiMTAxIn0.ffbwLbJVpUkPGT5uGUtxwV6AIN3BKh991laQLa2ar84";
        Map<String, String> map = tokenUtil.parseToken(token);
        System.out.println(map);

    }
}