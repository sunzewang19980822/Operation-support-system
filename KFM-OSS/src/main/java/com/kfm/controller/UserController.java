package com.kfm.controller;

import com.kfm.model.QueryInfo;
import com.kfm.model.RegGroup;
import com.kfm.model.ResultEntity;
import com.kfm.model.User;
import com.kfm.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
@Api(tags = "用户管理")
public class UserController {
    @Resource
    private UserService userService;
    @PostMapping
//    @PostMapping("/")
    @ApiOperation(value = "新增数据")
    public ResultEntity<User> add(@RequestBody  User user){
        if(userService.addUser(user)){
            return new ResultEntity<>(200,"保存成功",user);
        }else{
            return new ResultEntity<>(500,"保存失败",null);
        }
    }
    @DeleteMapping("/{id}")
    @ApiOperation("删除数据")
    public ResultEntity delete(@PathVariable Integer id){
        if(userService.deleteUser(id)){
            return new ResultEntity<>(200,"删除成功",null);
        }else{
            return new ResultEntity<>(500,"删除失败",null);
        }
    }
    @PatchMapping
    @ApiOperation("修改数据")
    public ResultEntity<User> update(@RequestBody @Validated User user){
        if(userService.updateUser(user)){
            return new ResultEntity<>(200,"保存成功",user);
        }else{
            return new ResultEntity<>(500,"保存失败",null);
        }
    }
    @PatchMapping("role")
    @ApiOperation("修改数据")
    public ResultEntity<User> setRole(@RequestBody User user){
        if(userService.setRole(user)){
            return new ResultEntity<>(200,"保存成功",user);
        }else{
            return new ResultEntity<>(500,"保存失败",null);
        }
    }
    @GetMapping("/{id}")
    @ApiOperation("通过ID查询单条数据")
    @ApiImplicitParam(name = "id",value="需要查询的编号")
    public ResultEntity get(@PathVariable Integer id){
        return new ResultEntity<>(200,"获取成功",userService.getUserById(id));
    }
//    @GetMapping
//    @ApiOperation("查询所有数据")
//    public ResultEntity getAll(){
//        return new ResultEntity<>(200,"获取成功",userService.getUsers());
//    }
    @GetMapping
    public ResultEntity get(QueryInfo params){
        return new ResultEntity(200,"success",userService.getUsers(params));
    }

}
