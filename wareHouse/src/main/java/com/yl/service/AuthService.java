package com.yl.service;

import com.yl.dto.AssignAuthDto;
import com.yl.entity.Auth;

import java.util.List;

public interface AuthService {

    //根据用户id查询用户权限(菜单)树的业务方法
    public List<Auth> findAuthTree(int userId);

    //查询整个权限(菜单)树的业务方法
    public List<Auth> allAuthTree();

    void assignAuth(AssignAuthDto assignAuthDto);
}
