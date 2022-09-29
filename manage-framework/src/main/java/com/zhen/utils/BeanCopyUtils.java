package com.zhen.utils;


import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 甄子函
 * @date: 2022/8/24__21:05
 */
public class BeanCopyUtils {

    private BeanCopyUtils(){
    }

    /**
     * 拷贝对象到对应VO
     *
     * @param source 源
     * @param clazz 目标
     * @param <V> VO
     * @return {@link V}
     */
    public static <V>V copyBean(Object source ,Class<V> clazz) {
        //创建目标对象
        V result = null;
        try {
            //通过反射创建对象
            result = clazz.newInstance();
            //实现属性拷贝
            BeanUtils.copyProperties(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回结果
        return result;
    }

    public static <O,V> List<V> copyBeanList(List<O> list , Class<V> clazz){
        return list.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
    }



}
