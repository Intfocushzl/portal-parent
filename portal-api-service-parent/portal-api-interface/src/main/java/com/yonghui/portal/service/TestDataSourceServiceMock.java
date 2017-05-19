package com.yonghui.portal.service;


import org.apache.log4j.Logger;

/**
 * 降级实现ApiService接口
 * 1.将接口进行归类，查询类和变更操作类：对于查询的分为一个接口类，变更的归类为其他的接口类，
 * 这样对于查询的可以使用mock="return null"进行降级操作；对于变更类的，可以仍旧使用try……catch进行异常捕获处理；
 * 2.配置mock="true"，同时mock实现接口，接口名要注意命名规范：接口名+Mock后缀。
 * 此时如果调用失败会调用Mock实现。mock实现需要保证有无参的构造方法。
 * <p>
 * Created by 张海 on 2017/04/27.
 */
public class TestDataSourceServiceMock implements TestDataSourceService {

    private Logger log = Logger.getLogger(this.getClass());

    public String dataA() {
        log.info("DataSourceServiceMock => dataA");
        throw new RuntimeException("DataSourceService fail!");
    }

    public String dataB() {
        log.info("DataSourceServiceMock => dataA");
        return null;
    }

    public String dataC() {
        log.info("DataSourceServiceMock => dataA");
        return null;
    }

    public String dataD() {
        log.info("DataSourceServiceMock => dataA");
        return null;
    }

    public String dataE() {
        log.info("DataSourceServiceMock => dataA");
        return null;
    }

    public String dataF() {
        log.info("DataSourceServiceMock => dataA");
        return null;
    }
}
