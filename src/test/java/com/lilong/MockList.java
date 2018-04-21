package com.lilong;


import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

/**
 * 类名称：MyAnnotion<br>
 * 类描述：调用类必须为集合 List<br>
 * 创建时间：2018年04月18日<br>
 *
 * @author lichao
 * @version 1.0.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
//最高优先级
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface MockList {
    /**
     * json 文件路径
     *
     * @author lichao  2018/4/18
     * @param
     * @return java.lang.String
     */
    String value() default "";

    /**
     * json 表达式
     *
     * @author lichao  2018/4/18
     * @param
     * @return java.lang.String
     */
    String expression() default "";
}
