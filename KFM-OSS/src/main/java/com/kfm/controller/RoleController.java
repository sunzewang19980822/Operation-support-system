package com.kfm.controller;

import com.kfm.model.ResultEntity;
import com.kfm.model.Role;
import com.kfm.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/role")
@Api(tags = "角色管理")
public class RoleController {
    @Resource
    private UserService userService;
    @PostMapping
//    @PostMapping("/")
    @ApiOperation(value = "新增数据")
    public ResultEntity<Role> add(@RequestBody  Role role){
        if(userService.addRole(role)){
            return new ResultEntity<>(200,"保存成功",role);
        }else{
            return new ResultEntity<>(500,"保存失败",null);
        }
    }
    @DeleteMapping("/{id}")
    @ApiOperation("删除数据")
    public ResultEntity delete(@PathVariable Integer id){
        if(userService.deleteRole(id)){
            return new ResultEntity<>(200,"删除成功",null);
        }else{
            return new ResultEntity<>(500,"删除失败",null);
        }
    }
    @PatchMapping
    @ApiOperation("修改数据")
    public ResultEntity<Role> update(@RequestBody @Validated Role role){
        if(userService.updateRole(role)){
            return new ResultEntity<>(200,"保存成功",role);
        }else{
            return new ResultEntity<>(500,"保存失败",null);
        }
    }
    @GetMapping("/{id}")
    @ApiOperation("通过ID查询单条数据")
    @ApiImplicitParam(name = "id",value="需要查询的编号")
    public ResultEntity get(@PathVariable Integer id){
        return new ResultEntity<>(200,"获取成功", userService.getRoleById(id));
    }
    @GetMapping
    @ApiOperation("查询所有数据")
    public ResultEntity getAll(){
        return new ResultEntity<>(200,"获取成功", userService.getRoles());
    }
}
