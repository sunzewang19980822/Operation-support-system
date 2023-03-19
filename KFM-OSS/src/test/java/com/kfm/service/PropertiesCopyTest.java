package com.kfm.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.kfm.model.User;

/**
 * 测试  BeanUtil.copyProperties的使用步骤
 */
public class PropertiesCopyTest {
    public static void main(String[] args) {
        CopyOptions copyOption = CopyOptions.create(null, true);
        User old=new User("name","pws","mob","email");
        old.setId(12);
        User newUser =new User();
        newUser.setName("name1");
        BeanUtil.copyProperties(newUser,old,copyOption);
        System.out.println(old); //这就是想要的效果
        System.out.println(newUser);

        BeanUtil.copyProperties(old,newUser,copyOption);
        System.out.println(old);
        System.out.println(newUser);
    }
}
