package com.kfm.controller;

import com.kfm.model.LoginForm;
import com.kfm.model.RegGroup;
import com.kfm.model.ResultEntity;
import com.kfm.model.User;
import com.kfm.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/login")
@Api(tags = "登录操作")
public class LoginController {
    @Resource
    private UserService userService;
    @PostMapping
    @ApiOperation(value = "登录")
    public ResultEntity<User> add(@RequestBody LoginForm loginForm){
        User user = userService.login(loginForm.getLoginName(), loginForm.getPassword());
        if(user!=null){
            return new ResultEntity<>(200,"登录成功",user);
        }else{
            return new ResultEntity<>(500,"登录失败",null);
        }
    }
    @GetMapping
    @ApiOperation(value = "退出登录")
    public ResultEntity<User> add(){
            return new ResultEntity<>(200,"退出登录成功",null);

    }



}
