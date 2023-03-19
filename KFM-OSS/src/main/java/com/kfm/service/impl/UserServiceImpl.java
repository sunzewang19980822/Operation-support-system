package com.kfm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.druid.util.StringUtils;
import com.kfm.dao.PromissDao;
import com.kfm.dao.RoleDao;
import com.kfm.dao.UserDao;
import com.kfm.model.Promiss;
import com.kfm.model.QueryInfo;
import com.kfm.model.Role;
import com.kfm.model.User;
import com.kfm.service.UserService;
import com.kfm.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.SecondaryTable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//@Service("userService")
@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {
//    private LoggerFactory log=
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PromissDao promissDao;
    @Autowired
    private TokenUtil tokenUtil;

    @Override
    public boolean addUser(User user) {
        try {
//            给明文+salt（盐值） 明文相同，密文不同
            String password = SecureUtil.md5(user.getPassword()+user.getMob());
//            System.out.println(password);
            user.setPassword(password);
            userDao.save(user);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
//            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteUser(Integer id) {
        try {
            userDao.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        User old = userDao.findById(user.getId()).get();
        CopyOptions copyOption = CopyOptions.create(null, true);
//        把新的非null数据合并
        BeanUtil.copyProperties(user,old,copyOption);
        try {
            userDao.save(old);
            BeanUtil.copyProperties(old,user,copyOption);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

    @Override
    public User getUserById(Integer id) {
        return userDao.findById(id).get();
    }

    @Override
    public List<User> getUsers() {
        return userDao.findAll();
    }
    //    @Override
    public Page<User> getUsers(QueryInfo params) {
        Pageable pageable = PageRequest.of(params.getPagenum()-1,params.getPagesize());
        if(StringUtils.isEmpty(params.getQuery())){
            return userDao.findAll(pageable);
        }
        List<User> adminsByQuery = userDao.findUsersByQuery(params);
        List<User> results;
        if(adminsByQuery.size()<params.getPagesize()){
            results=adminsByQuery;
        }else{
            results=adminsByQuery.subList((params.getPagenum()-1)*params.getPagesize(),
                    params.getPagenum()*params.getPagesize());
        }
        PageImpl<User> admins = new PageImpl<>(results,pageable,adminsByQuery.size());
        return admins;
    }

    @Transactional(readOnly = true)
    @Override
    public User login(String login, String password) {
//        1.查出匹配手机号的集合
        List<String> mobs = userDao.getMobsByLogin(login);
        System.out.println(mobs);
//        2.遍历集合，匹配第一个登录操作，遇到第一个登录成功退出
        for (String mob : mobs) {
            User user = userDao.login(login, SecureUtil.md5(password + mob));
            if(user!=null){//登录成功
                String token= tokenUtil.getToken(String.valueOf(user.getId()),user.getRoles().toString());
                user.setToken(token);
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean setRole(User user) {
        Integer[] roleIds = user.getRoleIds();
        if(roleIds==null || roleIds.length==0) return false;
        Set<Role> roles=new HashSet<>();
        for (Integer roleId : roleIds) {
            roles.add(new Role(roleId));
        }
        user.setRoles(roles);
        return updateUser(user);
    }

    @Override
    public boolean addRole(Role role) {
        try {
            roleDao.save(role);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteRole(Integer id) {
        try {
            roleDao.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateRole(Role role) {
        Role old = roleDao.findById(role.getId()).get();
        CopyOptions copyOption = CopyOptions.create(null, true);
        BeanUtil.copyProperties(role,old,copyOption);
        try {
            roleDao.save(old);
            BeanUtil.copyProperties(old,role,copyOption);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

    @Override
    public Role setPromisses(Role role) {
        if(updateRole(role)){
            return role;
        }
        return null;
    }

    @Override
    public Role getRoleById(Integer id) {
        return roleDao.findById(id).get();
    }

    @Override
    public List getRoles() {
        return roleDao.findAll();
    }

    @Override
    public boolean addPromiss(Promiss promiss) {
        try {
            promissDao.save(promiss);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deletePromiss(Integer id) {
        try {
            promissDao.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updatePromiss(Promiss promiss) {
        Promiss old = promissDao.findById(promiss.getId()).get();
        CopyOptions copyOption = CopyOptions.create(null, true);
        BeanUtil.copyProperties(promiss,old,copyOption);
        try {
            promissDao.save(old);
            BeanUtil.copyProperties(old,promiss,copyOption);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

    @Override
    public Promiss getPromissById(Integer id) {
        return promissDao.findById(id).get();
    }

    @Override
    public List getPromisss() {
        return promissDao.findAll();
    }
}
