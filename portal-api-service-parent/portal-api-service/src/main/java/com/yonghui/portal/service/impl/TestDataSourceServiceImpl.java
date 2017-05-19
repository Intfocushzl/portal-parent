package com.yonghui.portal.service.impl;

import com.yonghui.portal.annotation.DataSource;
import com.yonghui.portal.db.DataSourceConstants;
import com.yonghui.portal.db.DataSourceContextHolder;
import com.yonghui.portal.service.TestDataSourceService;
import org.apache.log4j.Logger;

/**
 * 动态数据源测试
 * <p>
 * 注解@DataSource既可以加在方法上，也可以加在接口或者接口的实现类上，优先级别：方法>实现类>接口。
 * 如果接口、接口实现类以及方法上分别加了@DataSource注解来指定数据源，则优先以方法上指定的为准。
 * <p>
 * Created by 张海 on 2017/04/27.
 *
 * @Service是dubbo本身的注解，跟配置文件中先声明一个bean然后再声明接口暴露的意思一样
 */
//@Service
// 默认DataSourceServiceImpl下的所有方法均访问数据源1
@DataSource(DataSourceConstants.MYSQL_PORTAL_SLAVE)
public class TestDataSourceServiceImpl implements TestDataSourceService {


    private Logger log = Logger.getLogger(this.getClass());

    @Override
    public String dataA() {
        return "使用默认数据源";
    }

    @Override
    @DataSource(DataSourceConstants.MYSQL_PORTAL_MASTER)
    public String dataB() {
        return "覆盖类上指定的，使用数据源 MYSQL_PORTAL_MASTER";
    }

    @Override
    @DataSource(DataSourceConstants.ORACLE_PORTAL_MASTER)
    public String dataC() {
        return "覆盖类上指定的，使用数据源 ORACLE_PORTAL_MASTER";
    }

    @Override
    @DataSource(DataSourceConstants.ORACLE_PORTAL_SLAVE)
    public String dataD() {
        return "覆盖类上指定的，使用数据源 ORACLE_PORTAL_SLAVE";
    }

    @Override
    public String dataE() {
        DataSourceContextHolder.set(DataSourceConstants.MYSQL_PORTAL_MASTER);
        return "指定切换到数据源 MYSQL_PORTAL_MASTER";
    }

    @Override
    public String dataF() {
        DataSourceContextHolder.set(DataSourceConstants.ORACLE_PORTAL_MASTER);
        return "指定切换到数据源 ORACLE_PORTAL_MASTER";
    }
}
