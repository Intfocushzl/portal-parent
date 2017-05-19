package com.yonghui.portal.annotation;


import java.lang.annotation.*;

/**
 * 直接通过注解的方式指定需要访问的数据源，
 * 比如在dao层使用@DataSource("xxx")就指定访问数据源xxx
 *
 * Created by 张海 on 2017/04/28.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface DataSource {
	String value() default "";
}
