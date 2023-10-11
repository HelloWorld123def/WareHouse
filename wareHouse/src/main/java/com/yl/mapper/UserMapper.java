package com.yl.mapper;


import com.yl.entity.User;
import com.yl.page.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    User findUserByCode(String userCode);


    //查询用户总行数的方法
    public int selectUserCount(User user);

    //分页查询用户的方法
    public List<User> selectUserPage(@Param("page") Page page, @Param("user")User user);


    //添加用户的方法
    public int insertUser(User user);


    //根据用户id修改用户状态的方法
    int updateUserState(User user);

    //根据用户id删除用户
    int deleteUserByName(Integer userId);

    //根据用户id修改用户昵称的方法
    public int updateNameById(User user);

    //根据用户id修改密码的方法
    int resetPwd(Integer userId);
}
