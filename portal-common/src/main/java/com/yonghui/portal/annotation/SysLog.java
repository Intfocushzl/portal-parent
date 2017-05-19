package com.yonghui.portal.annotation;

import java.lang.annotation.*;

/**
 * 系统日志注解
 * <p>
 * Created by 张海 on 2017/04/29.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    String value() default "";
}
