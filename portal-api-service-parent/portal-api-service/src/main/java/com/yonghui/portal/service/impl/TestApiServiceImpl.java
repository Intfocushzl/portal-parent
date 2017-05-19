package com.yonghui.portal.service.impl;

import com.yonghui.portal.service.TestApiService;
import org.apache.log4j.Logger;

/**
 * 实现ApiService接口
 * Created by 张海 on 2017/04/27.
 *
 * @Service是dubbo本身的注解，跟配置文件中先声明一个bean然后再声明接口暴露的意思一样
 */
//@Service
public class TestApiServiceImpl implements TestApiService {

    private Logger log = Logger.getLogger(this.getClass());

    @Override
    public String hello(String name) {
        return "hello," + name + "!";
    }

}
