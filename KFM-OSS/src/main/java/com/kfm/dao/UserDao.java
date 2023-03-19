package com.kfm.dao;

import com.kfm.model.QueryInfo;
import com.kfm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDao extends JpaRepository<User,Integer> {
//    @Query(nativeQuery = true,value = "SELECT mob FROM USER\n" +
//            "  WHERE (NAME=?1 OR mail=?1 OR mob=?1)")
    @Query(value = "SELECT mob FROM User " +
            "  WHERE (name=?1 OR mail=?1 OR mob=?1)")
    public List<String> getMobsByLogin(String login);
    @Query("FROM User " +
            "  WHERE (name=:login OR mail=:login OR mob=:login) AND password=:password")
    User login(String login,String password);

    @Query(nativeQuery = true,value = "SELECT DISTINCT " +
            "  A.id," +
            "  A.mob," +
            "  A.name," +
            "  A.mail," +
            "  A.password" +
            " FROM" +
            "  User A" +
            "  LEFT JOIN user_roles AR ON A.id = AR.user_id" +
            "  LEFT JOIN ROLE R ON AR.roles_id =R.id" +
            "  WHERE A.mail=:#{#queryInfo.query} OR  A.mob=:#{#queryInfo.query} OR A.name LIKE CONCAT('%',:#{#queryInfo.query},'%') " +
            " OR R.name LIKE CONCAT('%',:#{#queryInfo.query},'%') ")
    List<User> findUsersByQuery(@Param("queryInfo")QueryInfo queryInfo);
}
