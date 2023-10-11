package com.yl.service.impl;

import com.yl.entity.Result;
import com.yl.entity.User;
import com.yl.mapper.UserMapper;
import com.yl.page.Page;
import com.yl.service.UserService;
import com.yl.utils.DigestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;
    @Override
    public User findUserByCode(String userCode) {
        return userMapper.findUserByCode(userCode);
    }


    @Override
    public Page queryUserPage(Page page, User user) {
        //查询用户总行数
        int userCount = userMapper.selectUserCount(user);
        //分页查询用户
        List<User> userList = userMapper.selectUserPage(page, user);
        //将查询到的总行数和当前页数据组装到Page对象
        page.setTotalNum(userCount);
        page.setResultList(userList);

        return page;
    }

    @Override
    public Result saveUser(User user) {
        User oldUser = userMapper.findUserByCode(user.getUserCode());
        if (oldUser!=null){
            return Result.err(Result.CODE_ERR_BUSINESS, "该用户已存在！");
        }
        //用户不存在,对密码加密,添加用户
        String userPwd = DigestUtil.hmacSign(user.getUserPwd());
        user.setUserPwd(userPwd);
        userMapper.insertUser(user);
        return Result.ok("添加用户成功！");
    }

    @Override
    public Result updateUserState(User user) {
        //根据用户id修改用户状态
        int i = userMapper.updateUserState(user);
        if(i>0){
            return Result.ok("修改成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "修改失败！");
    }


    @Override
    public Result deleteUserByName(Integer userId) {
        int i = userMapper.deleteUserByName(userId);
        if (i>0){
            return Result.ok("用户删除成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "用户删除失败！");
    }

    @Override
    public Result updateUserName(User user) {
        int i = userMapper.updateNameById(user);
        if (i>0){
            return Result.ok("用户修改成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "用户修改失败！");
    }

    @Override
    public Result resetPwd(Integer userId) {
        return null;
    }
}
