package com.zhen.controller;

import com.zhen.annotation.InterfaceLog;
import com.zhen.domain.ResponseResult;
import com.zhen.domain.entity.User;
import com.zhen.enums.AppHttpCodeEnum;

import com.zhen.exception.SystemException;
import com.zhen.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 甄子函
 * @date: 2022/9/22__20:23
 */
@RestController
@RequestMapping("/system")
public class UserController {

    @Autowired
    private LoginService loginService;




    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            //提示必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }

    @PostMapping("/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }


}
