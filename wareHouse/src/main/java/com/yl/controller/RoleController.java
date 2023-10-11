package com.yl.controller;

import com.yl.dto.AssignAuthDto;
import com.yl.entity.CurrentUser;
import com.yl.entity.Result;
import com.yl.entity.Role;
import com.yl.page.Page;
import com.yl.service.AuthService;
import com.yl.service.RoleService;
import com.yl.utils.TokenUtils;
import com.yl.utils.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthService authService;

    /**
     * 查询所有角色的url接口role/role-list
     */

    @RequestMapping("/role-list")
    public Result roleList(){
        List<Role> allRole = roleService.findAllRole();
        return Result.ok("success",allRole);
    }

    /**
     * 分页查询角色的url接口/role/role-page-list
     *
     * 参数Page对象用于接收请求参数页码pageNum、每页行数pageSize;
     * 参数Role对象用于接收请求参数角色名roleName、角色代码roleCode、角色状态roleState;
     *
     * 返回值Result对象向客户端响应组装了所有分页信息的Page对象;
     */
    @RequestMapping("/role-page-list")
    public Result roleListPage(Page page, Role role){

        //执行业务
        page = roleService.queryRolePage(page, role);

        //响应
        return Result.ok(page);
    }

    /**
     * 添加角色的url接口/role/role-add
     *
     * @RequestBody Role role将添加的角色信息的json串数据封装到参数Role对象;
     * @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token
     * 将请求头Token的值即客户端归还的token赋值给参数变量token;
     */
    @RequestMapping("role-add")
    public Result addRole(@RequestBody Role role,
                          @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME)String token){
        //获取当前登录的用户
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        //获取当前登录的用户id,即创建新角色的用户id
        int userId = currentUser.getUserId();
        role.setCreateBy(userId);
        return roleService.saveRole(role);
    }

    /**
     * 修改角色状态的url接口/role/role-state-update
     *
     * @RequestBody Role role将客户端传递的json数据封装到参数Role对象中;
     * @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token
     * 将请求头Token的值即客户端归还的token赋值给参数变量token;
     */

    @RequestMapping("/role-state-update")
    public Result updateRoleState(@RequestBody Role role,
                                  @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){

        //获取当前登录的用户
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        //获取当前登录的用户id,即修改角色的用户id
        int userId = currentUser.getUserId();

        role.setUpdateBy(userId);
        role.setUpdateTime(new Date());
        return roleService.updateRoleState(role);
    }


    /**
     * 查询角色已分配的权限(菜单)的url接口/role/role-auth
     *
     * Integer roleId将请求参数roleId赋值给请求处理方法参数roleId;
     *
     * 返回值Result对象向客户端响应组装了给角色分配的所有权限(菜单)id的List<Integer>;
     */
    @RequestMapping("/role-auth")
    public Result queryRoleAuth(Integer roleId){
        return Result.ok(roleService.queryAuthIds(roleId));
    }



    /**
     * 给角色分配权限(菜单)的url接口/role/auth-grant
     *
     * @RequestBody AssignAuthDto assignAuthDto将请求传递的json数据
     * 封装到参数AssignAuthDto对象中;
     */
    @RequestMapping("/auth-grant")
    public Result assignAuth(@RequestBody AssignAuthDto assignAuthDto){
        //执行业务
        authService.assignAuth(assignAuthDto);
        //响应
        return Result.ok("分配权限成功！");
    }


    /**
     * 删除角色的url接口/role/role-delete/{roleId}
     */
    @RequestMapping("/role-delete/{roleId}")
    public Result deleteRole(@PathVariable Integer roleId){
        roleService.deleteRole(roleId);
        return Result.ok("角色删除成功！");
    }

    /**
     * 修改角色的url接口/role/role-update
     *
     * @RequestBody Role roler将请求传递的json数据封装到参数Role对象;
     * @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token
     * 将请求头Token的值即客户端归还的token赋值给参数变量token;
     */

    @RequestMapping("/role-update")
    public Result updateRole(@RequestBody Role role,
                             @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){

        //获取当前登录的用户
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        //获取当前登录的用户id -- 修改角色的用户id
        int userId = currentUser.getUserId();

        role.setUpdateBy(userId);
        //执行业务
        //响应
        return Result.ok(roleService.updateRoleDesc(role));
    }

}
