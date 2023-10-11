package com.yl.service;

import com.yl.dto.AssignRoleDto;
import com.yl.entity.Result;
import com.yl.entity.Role;
import com.yl.page.Page;

import java.util.List;

public interface RoleService {
    //查询所有角色方法
    List<Role> findAllRole();


    //查询用户已分配的角色的业务方法
    public List<Role> queryRolesByUserId(Integer userId);


    //给用户分配角色的业务方法
    public void assignRole(AssignRoleDto assignRoleDto);


    //分页查询角色的业务方法
    Page queryRolePage(Page page, Role role);

    //添加角色的业务方法
    Result saveRole(Role role);

    //修改角色状态的业务方法
    Result updateRoleState(Role role);

    //查询角色已分配的权限(菜单)的业务方法
    public List<Integer> queryAuthIds(Integer roleId);

    //删除角色的业务方法
    void deleteRole(Integer roleId);

    //修改角色描述的业务方法
    Result updateRoleDesc(Role role);
}
