package com.yl.service.impl;


import com.yl.dto.AssignRoleDto;
import com.yl.entity.Result;
import com.yl.entity.Role;
import com.yl.mapper.AuthMapper;
import com.yl.mapper.RoleMapper;
import com.yl.page.Page;
import com.yl.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private AuthMapper authMapper;
    @Autowired
    private RoleMapper roleMapper;

    //查询所有角色方法
    @Override
    public List<Role> findAllRole() {
        return roleMapper.findAllRole();
    }


    @Override
    public List<Role> queryRolesByUserId(Integer userId) {
        return roleMapper.findRolesByUserId(userId);
    }


    @Transactional
    @Override
    public void assignRole(AssignRoleDto assignRoleDto) {

        //拿到用户id
        Integer userId = assignRoleDto.getUserId();
        //拿到给用户分配的所有角色名
        List<String> roleNameList = assignRoleDto.getRoleCheckList();

        //根据用户id删除给用户已分配的所有角色
        roleMapper.delRoleByUserId(userId);
        //循环添加用户角色关系
        int roleId = 0;
        for (String roleName : roleNameList) {
            roleId = roleMapper.getRoleIdByName(roleName);
        }
        //添加用户角色关系
        roleMapper.insertUserRole(userId, roleId);
    }

    @Override
    public Page queryRolePage(Page page, Role role) {

        //查询角色总行数
        int roleCount = roleMapper.selectRoleCount(role);

        //分页查询角色
        List<Role> roleList = roleMapper.selectRolePage(page, role);

        //将查询到的总行数和当前页数据组装到Page对象
        page.setPageCount(roleCount);
        page.setResultList(roleList);

        return page;
    }

    @Override
    public Result saveRole(Role role) {
        Role oldRole = roleMapper.findRoleByNameOrCode(role.getRoleName(), role.getRoleCode());
        if (oldRole != null) {
            return Result.err(Result.CODE_ERR_BUSINESS, "该角色已存在！");
        }
        //角色不存在,添加角色
        roleMapper.insertRole(role);
        return Result.ok("添加角色成功！");
    }

    @Override
    public Result updateRoleState(Role role) {
        int i = roleMapper.updateRoleState(role);
        if (i > 0) {
            return Result.ok("修改成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "修改失败！");
    }

    @Override
    public List<Integer> queryAuthIds(Integer roleId) {
        //根据角色id查询角色已分配的所有权限(菜单)的id
        return roleMapper.findAuthById(roleId);
    }

    @Override
    public void deleteRole(Integer roleId) {
        int i = roleMapper.deleteRoleById(roleId);
        if (i > 0) {
            authMapper.delAuthByRoleId(roleId);
        }
    }

    @Override
    public Result updateRoleDesc(Role role) {

        int i = roleMapper.updateDescById(role);
        if (i>0){
            return Result.ok("角色修改成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "角色修改失败！");
    }
}
