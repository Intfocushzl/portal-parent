package com.yonghui.portal.annotation;

import java.lang.annotation.*;

/**
 * 初始化继承BaseService的service
 *
 * Created by 张海 on 2017/04/28.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BaseService {

}
