package com.zhen.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 查询条件
 *
 * @author 甄子函
 * @date: 2022/9/29__9:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConditionVo {
    /**
     * 页码
     */
    @ApiModelProperty(name = "pageNum", value = "页码", dataType = "Long")
    private Long pageNum;

    /**
     * 条数
     */
    @ApiModelProperty(name = "pageSize", value = "条数", dataType = "Long")
    private Long pageSize;

    /**
     * 搜索内容
     */
    @ApiModelProperty(name = "keywords", value = "搜索内容", dataType = "String")
    private String keywords;
}
