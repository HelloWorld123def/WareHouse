package com.yl.mapper;


import com.yl.entity.Role;
import com.yl.page.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper {

    //查询所有角色方法
    List<Role> findAllRole();

    //根据用户id查询用户已分配的角色
    List<Role> findRolesByUserId(Integer userId);

    //根据角色名查询角色id
    Integer findRoleIdByRoleName(String roleName);


    //根据用户id删除给用户已分配的所有角色
    public int delRoleByUserId(Integer userId);

    //根据角色名称查询角色id
    public int getRoleIdByName(String roleName);

    //添加用户角色关系的方法
    public void insertUserRole(Integer userId, Integer roleId);

    //查询角色总行数的方法
    public int selectRoleCount(Role role);


    //分页查询角色的方法
    public List<Role> selectRolePage(@Param("page") Page page, @Param("role") Role role);

    //根据角色名称或者角色代码查询角色的方法
    Role findRoleByNameOrCode(String roleName, String roleCode);

    //添加角色的方法
    int insertRole(Role role);

    //根据角色id修改角色状态的方法
    int updateRoleState(Role role);

    //根据角色id查询角色已分配的所有权限(菜单)的id
    List<Integer> findAuthById(Integer roleId);


    //根据角色id删除角色的方法
    int deleteRoleById(Integer roleId);

    //根据角色id修改角色描述的方法
    int updateDescById(Role role);
}
