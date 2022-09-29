package com.zhen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhen.domain.ResponseResult;
import com.zhen.domain.entity.Menu;
import com.zhen.domain.vo.ConditionVo;
import com.zhen.domain.vo.MenuVo;
import com.zhen.domain.vo.RouterVo;
import com.zhen.mapper.MenuMapper;
import com.zhen.service.MenuService;
import com.zhen.utils.BeanCopyUtils;
import com.zhen.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2022-09-27 10:29:04
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public ResponseResult getLoginUserMenus(Long userId) {
        List<Menu> menus = null;
        //角色判断 管理员/其他角色
        if (SecurityUtils.isAdmin()){
            menus = getBaseMapper().selectAllRouterMenu();
        }else {
            menus = getBaseMapper().getLoginUserMenus(userId);
        }
        //构建菜单树状结构
        List<Menu> menuTree = builderMenuTree(menus,0L);
        return ResponseResult.okResult(new RouterVo(menuTree));
    }

    @Override
    public ResponseResult getMenuList(ConditionVo conditionVo) {
        //有搜索关键字时返回 menus列表
        List<Menu> menus = menuMapper.selectList(new LambdaQueryWrapper<Menu>()
                .like(StringUtils.isNotBlank(conditionVo.getKeywords()),Menu::getMenuName,conditionVo.getKeywords()));

        //获取所有menu
        List<Menu> menuList = list();

        //构建列表树状结构
        List<Menu> menuTree = builderMenuTree(menuList, 1L);
        if (menus != null){
            List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(menus, MenuVo.class);
            return ResponseResult.okResult(menuVos);
        }else{
            //无查询条件
            return null;
        }


    }

    /**
     *  目录
     * @param menus
     * @param parentId
     * @return
     */
    private List<Menu> builderMenuTree(List<Menu> menus, Long parentId) {
        List<Menu> menuTree = menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
        return menuTree;
    }

    /**
     * 获取存入参数的 子Menu集合
     * @param menu
     * @param menus
     * @return
     */
    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        List<Menu> childrenList = menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m->m.setChildren(getChildren(m,menus)))
                .collect(Collectors.toList());
        return childrenList;
    }

}
