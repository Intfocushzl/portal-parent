package com.yonghui.portal.annotation;

import java.lang.annotation.*;

/**
 * 忽略Token验证  校验sign
 * 如果有@OpenAuth注解，则不验证token,而是校验sigin
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OpenAuth {

}
