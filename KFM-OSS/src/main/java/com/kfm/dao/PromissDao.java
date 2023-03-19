package com.kfm.dao;

import com.kfm.model.Promiss;
import com.kfm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromissDao extends JpaRepository<Promiss,Integer> {
}
