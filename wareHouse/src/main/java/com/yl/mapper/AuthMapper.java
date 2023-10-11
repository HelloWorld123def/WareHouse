package com.yl.mapper;


import com.yl.entity.Auth;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthMapper {

    //根据用户id查询用户所有权限(菜单)的方法
    public List<Auth> findAllAuth(int userId);

    //查询所有状态正常的权限(菜单)的方法
    public List<Auth> getAllAuth();

    //根据角色id删除给角色已分配的所有权限(菜单)
    public int delAuthByRoleId(Integer roleId);

    //添加角色权限(菜单)关系的方法
    void insertRoleAuth(Integer roleId, Integer authId);
}
