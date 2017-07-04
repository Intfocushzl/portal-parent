package com.yonghui.portal.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;
public class PropertiesTools {

    /**
     * 读取属性文件值
     *
     * @param name    key
     * @param fileURL 文件url
     * @return
     */
    public static String getFileIO(String name, String fileURL) {
        Properties prop = new Properties();
        InputStream in = PropertiesTools.class.getResourceAsStream(fileURL);
        try {
            prop.load(in);
            return prop.getProperty(name);
        } catch (IOException e) {
//            IhkMsyfException.myPrintStackTrace(e);
        } finally {
            try {
                if(in!=null) in.close();
            } catch (IOException e) {
//                IhkMsyfException.myPrintStackTrace(e);
            }
        }
        return null;
    }

    /**
     * 写入属性文件值
     *
     * @param key     key值
     * @param value   value值
     * @param fileURL 文件路径
     */
    public static void writeData(String key, String value, String fileURL) {
        Properties prop = new Properties();
        InputStream fis = null;
        OutputStream fos = null;
        try {
            URL url = PropertiesTools.class.getResource(fileURL);
            File file = new File(url.toURI());
            if (!file.exists())
                file.createNewFile();
            fis = new FileInputStream(file);
            prop.load(fis);
            fis.close();//一定要在修改值之前关闭fis
            fos = new FileOutputStream(file);
            prop.setProperty(key, value);
            prop.store(fos, "Update '" + key + "' value");
            fos.close();
        } catch (IOException e) {
//            IhkMsyfException.myPrintStackTrace(e);
        } catch (URISyntaxException e) {
//            IhkMsyfException.myPrintStackTrace(e);
        } finally {
            try {
                fos.close();
                fis.close();
            } catch (IOException e) {
//                IhkMsyfException.myPrintStackTrace(e);
            }
        }
    }

    public static void main(String[] args) {
        PropertiesTools.writeData("name", "microsoft", "gxyTest.properties");
        System.out.println(PropertiesTools.getFileIO("name", "gxyTest.properties"));
    }
}
