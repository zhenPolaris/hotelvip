package com.zhen.service.impl;


import com.zhen.domain.ResponseResult;
import com.zhen.domain.entity.LoginUser;
import com.zhen.domain.entity.User;
import com.zhen.domain.vo.LoginVo;
import com.zhen.domain.vo.UserInfoVo;
import com.zhen.service.LoginService;
import com.zhen.utils.BeanCopyUtils;
import com.zhen.utils.JwtUtil;
import com.zhen.utils.RedisCache;
import com.zhen.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author 甄子函
 * @date: 2022/8/29__20:53
 */
@Service
public class AdminLoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        //判断是否认证通过
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //获取userid生成token
        LoginUser loginUser =(LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getUserId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //把用户信息存入redis
        redisCache.setCacheObject("login:"+userId, loginUser);

        //把token封装 返回
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        LoginVo vo = new LoginVo(jwt,userInfoVo);

        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult logout() {
        //获取token解析userId
        Long userId = SecurityUtils.getUserId();
        //从redis中删除用户信息
        redisCache.deleteObject("login:"+userId);
        return ResponseResult.okResult();
    }
}
