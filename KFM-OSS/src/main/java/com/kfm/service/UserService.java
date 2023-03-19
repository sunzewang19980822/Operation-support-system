package com.kfm.service;

import com.kfm.model.Promiss;
import com.kfm.model.QueryInfo;
import com.kfm.model.Role;
import com.kfm.model.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    public boolean addUser(User user);
    public boolean deleteUser(Integer id);
    public boolean updateUser(User user);
    public User getUserById(Integer id);
    public List<User> getUsers();
    public User login(String login,String password);
    public boolean setRole(User user);

    public boolean addRole(Role role);
    public boolean deleteRole(Integer id);
    public boolean updateRole(Role role);
    public Role setPromisses(Role role);
    public Role getRoleById(Integer id);
    public List<Role> getRoles();

    public boolean addPromiss(Promiss promiss);
    public boolean deletePromiss(Integer id);
    public boolean updatePromiss(Promiss promiss);
    public Promiss getPromissById(Integer id);
    public List<Promiss> getPromisss();

    public Page<User> getUsers(QueryInfo params);
}
