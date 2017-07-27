package com.yonghui.portal.init;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Enumeration;
import java.util.Properties;

/**
 * 加载tomcat下*.properties配置文件
 */
@Component
public class InitProperties {
    Logger logger = Logger.getLogger(InitProperties.class);
    private static InitProperties propertiesReaderWrite;
    private static Properties propertiesRead;
    private static Properties propertiesWrite;
    private static String[] propertiesList = {"portal-admin-jdbc"};
    public static String APP_BASE_URL;

    public static synchronized InitProperties getInstance() {
        if (null == propertiesReaderWrite) {
            propertiesReaderWrite = new InitProperties();
        }
        return propertiesReaderWrite;
    }

    public InitProperties() {
        try {
            for (int i = 0; i < propertiesList.length; i++) {
                propertiesRead = new Properties();
                propertiesWrite = new Properties();
                // 获取tomcat目录下的文件
                File fileCatalina = new File(System.getProperty("catalina.home") + "/" + propertiesList[i] + ".properties");
                if (fileCatalina.exists()) {
                    logger.info("读写配置文件信息: " + System.getProperty("catalina.home") + "/" + propertiesList[i] + ".properties");
                    // 获取配置文件输入流
                    InputStream inStream = new FileInputStream(fileCatalina);
                    // 载入输入流
                    propertiesRead.load(inStream);
                    // app接口地址 后期删除此代码
                    APP_BASE_URL = propertiesRead.getProperty("APP_BASE_URL");
                    intAppUrl();

                    // 取得配置文件里所有的key值
                    Enumeration enumeration = propertiesRead.propertyNames();
                    // 读取并写入项目资源文件
                    File file = new File(InitProperties.class.getClassLoader().getResource("/").getPath() + "/" + propertiesList[i] + ".properties");
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    OutputStream outputStream = new FileOutputStream(file);
                    while (enumeration.hasMoreElements()) {
                        String key = (String) enumeration.nextElement();
                        logger.info(propertiesList[i] + ":" + key + "=" + propertiesRead.getProperty(key));
                        propertiesWrite.setProperty(StringEscapeUtils.unescapeJava(key), StringEscapeUtils.unescapeJava(propertiesRead.getProperty(key)));
                    }
                    propertiesWrite.store(outputStream, propertiesList[i]);
                    outputStream.close();
                } else {
                    logger.warn(System.getProperty("catalina.home") + "/" + propertiesList[i] + ".properties (tomcat下没有找到配置文件)");
                }
            }
        } catch (IOException e) {
            logger.error("读写配置文件信息失败: " + e.getMessage(), e);
        }
    }


    //APP的常量接口  后期删除以下代码
    //菜单
    public static String APP_BASE_GET_REPORT_URL;      //获取生意概况菜单列表
    public static String APP_BASE_GET_KPI_URL;      //仪表盘列表
    public static String APP_BASE_GET_ANALYSE_URL;      //获取报表菜单列表
    public static String APP_BASE_GET_APP_URL;      //获取专题菜单列表
    //        public static final String APP_BASE_POST_REPORT_URL = APP_BASE_URL + "/report";      //创建生意概况菜单
    public static String APP_BASE_POST_KPI_URL;      //仪表盘
    public static String APP_BASE_POST_ANALYSE_URL;      //创建报表菜单
    public static String APP_BASE_POST_APP_URL;      //创建专题菜单
    //角色
    public static String APP_BASE_GET_ROLE_URL;      //获取角色列表
    public static String APP_BASE_POST_ROLE_URL;      //创建角色
    //菜单与角色
    //角色与菜单
    //群组
    public static String APP_BASE_GET_GROUP_URL;      //获取群组列表
    public static String APP_BASE_POST_GROUP_URL;      //创建群组
    //用户
    public static String APP_BASE_GET_USER_URL;      //获取用户列表
    public static String APP_BASE_POST_USER_URL;      //创建用户

    //用户与角色
    public void intAppUrl() {
        //APP的常量接口
        //菜单
        APP_BASE_GET_REPORT_URL = APP_BASE_URL + "/reports";      //获取生意概况菜单列表
        APP_BASE_GET_KPI_URL = APP_BASE_URL + "/kpis";      //仪表盘列表
        APP_BASE_GET_ANALYSE_URL = APP_BASE_URL + "/analyses";      //获取报表菜单列表
        APP_BASE_GET_APP_URL = APP_BASE_URL + "/apps";      //获取专题菜单列表
        //        public static final String APP_BASE_POST_REPORT_URL = APP_BASE_URL + "/report";      //创建生意概况菜单
        APP_BASE_POST_KPI_URL = APP_BASE_URL + "/kpi";      //仪表盘
        APP_BASE_POST_ANALYSE_URL = APP_BASE_URL + "/analyse";      //创建报表菜单
        APP_BASE_POST_APP_URL = APP_BASE_URL + "/app";      //创建专题菜单
        //角色
        APP_BASE_GET_ROLE_URL = APP_BASE_URL + "/roles";      //获取角色列表
        APP_BASE_POST_ROLE_URL = APP_BASE_URL + "/role";      //创建角色
        //菜单与角色
        //角色与菜单
        //群组
        APP_BASE_GET_GROUP_URL = APP_BASE_URL + "/groups";      //获取群组列表
        APP_BASE_POST_GROUP_URL = APP_BASE_URL + "/group";      //创建群组
        //用户
        APP_BASE_GET_USER_URL = APP_BASE_URL + "/users";      //获取用户列表
        APP_BASE_POST_USER_URL = APP_BASE_URL + "/user";      //创建用户
        //用户与角色
    }

}