package com.yonghui.portal.service.test;

/**
 * 数据源切换测试
 * <p>
 * Created by 张海 on 2017/04/28.
 */
public interface TestDataSourceService {

    // 不指定，则默认使用数据源1
    String dataA();

    // 覆盖类上指定的，使用数据源2
    String dataB();

    // 覆盖类上指定的，使用数据源3
    String dataC();

    // 覆盖类上指定的，使用数据源4
    String dataD();

    // 覆盖类上指定的，使用数据源5
    String dataE();

    // 覆盖类上指定的，使用数据源6
    String dataF();

    // app 85库
    public String dataApp();
}
