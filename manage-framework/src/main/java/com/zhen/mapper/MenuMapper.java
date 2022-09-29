package com.zhen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhen.domain.entity.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2022-09-27 10:29:03
 */

public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> getLoginUserMenus(Long userId);

    List<Menu> selectAllRouterMenu();
}
