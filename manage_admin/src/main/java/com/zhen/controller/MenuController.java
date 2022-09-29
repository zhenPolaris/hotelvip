package com.zhen.controller;

import com.zhen.annotation.InterfaceLog;
import com.zhen.domain.ResponseResult;
import com.zhen.domain.vo.ConditionVo;
import com.zhen.service.MenuService;
import com.zhen.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 甄子函
 * @date: 2022/9/27__10:32
 */
@RestController
@RequestMapping("/system")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/menus")
    public ResponseResult getMenuList(ConditionVo conditionVo){

        return menuService.getMenuList(conditionVo);
    }


    @GetMapping("/user/menus")
    @InterfaceLog(InterfaceType = "登录用户菜单列表")
    public ResponseResult getLoginUserMenus(){

      return  menuService.getLoginUserMenus(SecurityUtils.getUserId());

    }
}
