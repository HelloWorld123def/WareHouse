package com.yl.controller;

import com.yl.entity.Auth;
import com.yl.entity.Result;
import com.yl.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuthController {

    //注入AuthService
    @Autowired
    private AuthService authService;


    /**
     * 查询整个权限(菜单)树的url接口/auth/auth-tree
     */
    @RequestMapping("auth/auth-tree")
    public Result allAuthTree1(){
        //执行业务
        List<Auth> allAuthTree = authService.allAuthTree();
        //响应
        return Result.ok(allAuthTree);
    }

}
