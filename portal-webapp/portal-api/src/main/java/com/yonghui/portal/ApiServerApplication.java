package com.yonghui.portal;

import com.alibaba.dubbo.container.Main;
import com.yonghui.portal.service.TestApiService;
import com.yonghui.portal.service.TestDataSourceService;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 调用服务
 * Created by 张海 on 2017/04/27.
 */
public class ApiServerApplication {

    private Logger log = Logger.getLogger(this.getClass());

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:springMVC-servlet.xml");
        // 接口测试
        TestApiService testApiService = (TestApiService) context.getBean("apiService");
        System.out.println(testApiService.hello("zhanghai"));

        // 接口测试、数据源测试
        TestDataSourceService testDataSourceService = (TestDataSourceService) context.getBean("dataSourceService");
        System.out.println(testDataSourceService.dataA());
        System.out.println(testDataSourceService.dataB());
        System.out.println(testDataSourceService.dataC());
        System.out.println(testDataSourceService.dataD());
        System.out.println(testDataSourceService.dataE());
        System.out.println(testDataSourceService.dataF());

        Main.main(args);
    }

}
