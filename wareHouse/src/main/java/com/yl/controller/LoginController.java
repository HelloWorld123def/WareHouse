package com.yl.controller;


import com.google.code.kaptcha.Producer;
import com.yl.entity.*;
import com.yl.service.UserService;
import com.yl.utils.DigestUtil;
import com.yl.utils.TokenUtils;
import com.yl.utils.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
public class LoginController {

    @Autowired
    private Producer producer;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private TokenUtils tokenUtils;


    @Autowired
    private UserService userService;

    @RequestMapping("captcha/captchaImage")
    public void captchaImage(HttpServletResponse response){

        ServletOutputStream outputStream = null;
        try {
        //生成验证码图片的文件
        String text = producer.createText();
        //使用验证码文本生成验证码图片 BufferedImage对象就代表生成的验证码图片，在内存中
        BufferedImage image = producer.createImage(text);
        //保存到redis 设置过期时间

        redisTemplate.opsForValue().set(text,"",60*30, TimeUnit.SECONDS);
        /*
         * 将验证码响应给前端
         * 设置响应正文image/jpeg
         * 将验证码响应给前端
         */

        response.setContentType("image/jpeg");
        outputStream = response.getOutputStream();
        ImageIO.write(image,"jpg",outputStream);
        outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if (outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    @PostMapping("login")
    public Result login(@RequestBody LoginUser loginUser) {
		/*
		  校验验证码：
		 */
        if(!redisTemplate.hasKey(loginUser.getVerificationCode())){
            return Result.err(Result.CODE_ERR_BUSINESS, "验证码不正确！");
        }

		/*
		  校验用户名密码:
		 */
        //根据用户名查询用户
        User user = userService.findUserByCode(loginUser.getUserCode());
        if (user!=null) {//查到了用户
            if (user.getUserState().equals(WarehouseConstants.USER_STATE_PASS)) {//查到的用户状态是已审核
                //将用户录入的密码进行加密
                String password = DigestUtil.hmacSign(loginUser.getUserPwd());
                if (password.equals(user.getUserPwd())) {//查到的用户的密码和用户录入的密码相同
                    //生成token并响应给前端
                    CurrentUser currentUser = new CurrentUser(user.getUserId(), user.getUserCode(), user.getUserName());
                    String token = tokenUtils.loginSign(currentUser, user.getUserPwd());
                    return Result.ok("登录成功！", token);
                } else {//查到的用户的密码和用户录入的密码不同
                    return Result.err(Result.CODE_ERR_BUSINESS, "密码不正确！");
                }
            } else {//查到的用户状态是未审核
                return Result.err(Result.CODE_ERR_BUSINESS, "用户未审核！");
            }
        }else{//没有查到用户
            return Result.err(Result.CODE_ERR_BUSINESS, "该用户不存在！");
        }
    }


    @RequestMapping("curr-user")
    public Result currentUser(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        return Result.ok(currentUser);
    }


    @RequestMapping("logout")
    public Result logout(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){
        redisTemplate.delete(token);

        return Result.ok("退出系统");
    }
    //加载权限用户接口
   /* @RequestMapping("user/auth-list")
    public Result loadAuthTree(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int userId = currentUser.getUserId();
        List<Auth> authTreeList = authService.authTreeByUId(userId);
        return Result.ok(authTreeList);
    }*/
}
