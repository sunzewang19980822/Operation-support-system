package com.kfm.dao;

import com.kfm.model.Role;
import com.kfm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role,Integer> {
}
