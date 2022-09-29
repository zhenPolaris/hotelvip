package com.zhen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhen.domain.ResponseResult;
import com.zhen.domain.entity.Menu;
import com.zhen.domain.vo.ConditionVo;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2022-09-27 10:29:04
 */
public interface MenuService extends IService<Menu> {

    ResponseResult getLoginUserMenus(Long userId);

    ResponseResult getMenuList(ConditionVo conditionVO);

}
