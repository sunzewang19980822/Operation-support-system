package com.kfm.service;

import com.kfm.model.Promiss;
import com.kfm.model.Role;
import com.kfm.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.SecondaryTable;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Test
    void initData() {
        addUser();
        addRole();
        addPromiss();
        setRole();
        setPromisses();
        setPromisses2();
    }
    @Test
    void addUser() {
//        User user=new User("杜甫","123456","13100010002","acbc@kfm.com");
//        User user=new User("杜甫1","123456","13100010003","acbd@kfm.com");
//        User user=new User("杜甫2","123456","13100010004","acbe@kfm.com");
//        User user=new User("13100010004","123456","13100010005","acbf@kfm.com");
//        assertTrue(userService.addUser(user));
//        assertTrue(user.getId()>0);
//        System.out.println(user);
        User admin=new User("李白","123456","13100010001","a001@kfm.com");
        userService.addUser(admin);
        User teacher=new User("杜甫","123456","13100010002","a002@kfm.com");
        userService.addUser(teacher);

    }

    @Test
    void login() {
        User login = userService.login("13100010004", "123456");
        System.out.println(login);
    }
    @Test
    void deleteUser() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void setRole() {
        User user=new User();
        user.setId(1);
        Set<Role> roles=new HashSet<>();
        roles.add(new Role(1));
        roles.add(new Role(2));
        user.setRoles(roles);
        userService.setRole(user);

    }

    @Test
    void getUserById() {
        User user=userService.getUserById(1);
        System.out.println(user);
    }

    @Test
    void getUsers() {
    }

    @Test
    void addRole() {
        Role role=new Role("管理员");
        userService.addRole(role);
        Role teacher=new Role("任课老师");
        userService.addRole(teacher);

    }

    @Test
    void deleteRole() {
    }

    @Test
    void updateRole() {
    }

    @Test
    void setPromisses() {
        Role role=new Role();
        role.setId(1);
        Set<Promiss> promisses=new HashSet<>();
        promisses.add(new Promiss(1));
        promisses.add(new Promiss(3));
        promisses.add(new Promiss(5));
        role.setPromisses(promisses);
        userService.setPromisses(role);
    }
    @Test
    void setPromisses2() {
        Role role=new Role();
        role.setId(2);
        Set<Promiss> promisses=new HashSet<>();
        promisses.add(new Promiss(1));
        role.setPromisses(promisses);
        userService.setPromisses(role);
    }

    @Test
    void getRoleById() {
    }

    @Test
    void getRoles() {
    }

    @Test
    void addPromiss() {
        Promiss user = new Promiss("用户管理", "Avatar","", null);
        userService.addPromiss(user);
        Promiss user1 = new Promiss("用户信息", "Document","main/user", user);
        userService.addPromiss(user1);

        Promiss role = new Promiss("角色管理", "Avatar","", null);
        userService.addPromiss(role);
        Promiss role1 = new Promiss("角色信息", "Document","main/role", role);
        userService.addPromiss(role1);

        Promiss promiss = new Promiss("权限管理", "Avatar","", null);
        userService.addPromiss(promiss);
        Promiss promiss1 = new Promiss("权限信息", "Document","main/promisss", promiss);
        userService.addPromiss(promiss1);
    }


    @Test
    void deletePromiss() {
    }

    @Test
    void updatePromiss() {
    }

    @Test
    void getPromissById() {
    }

    @Test
    void getPromisss() {
    }
}