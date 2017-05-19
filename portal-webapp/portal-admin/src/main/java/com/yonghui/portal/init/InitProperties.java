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
                        logger.info(propertiesList[i] + "=" + propertiesRead.getProperty(key));
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

}