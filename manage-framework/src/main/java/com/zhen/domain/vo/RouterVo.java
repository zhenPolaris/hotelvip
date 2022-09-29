package com.zhen.domain.vo;

import com.zhen.domain.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 甄子函
 * @date: 2022/9/27__11:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouterVo {

    private List<Menu> menus;
}
