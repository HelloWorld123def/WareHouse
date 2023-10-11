package com.yl.service;

import com.yl.entity.Result;
import com.yl.entity.User;
import com.yl.page.Page;

import java.util.List;

public interface UserService {

    User findUserByCode (String userCode);

    //分页查询用户的业务方法
     Page queryUserPage(Page page, User user);

    //添加用户的业务方法
     Result saveUser(User user);

    Result updateUserState(User user);

    Result deleteUserByName(Integer userId);

    //修改用户昵称的业务方法
    public Result updateUserName(User user);

    Result resetPwd(Integer userId);
}
