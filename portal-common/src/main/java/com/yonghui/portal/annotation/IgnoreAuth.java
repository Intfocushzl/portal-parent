package com.yonghui.portal.annotation;

import java.lang.annotation.*;

/**
 * 忽略Token验证
 * 如果有@IgnoreAuth注解，则不验证token
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreAuth {

}
