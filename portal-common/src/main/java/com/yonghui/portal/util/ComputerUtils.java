package com.yonghui.portal.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Author : 杨杨
 * Date : 2017/06/01
 * Description :
 */
public class ComputerUtils {

    public static String getHostName() {
        try {
            InetAddress ia = InetAddress.getLocalHost();
            String host = ia.getHostName();//获取计算机主机名
            return host;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String getIp() {
        try {
            InetAddress ia = InetAddress.getLocalHost();
            String IP = ia.getHostAddress();//获取计算机IP
            return IP;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String getDomain() {
        try {
            InetAddress ia = InetAddress.getLocalHost();
            String domain = ia.getHostAddress();//获取计算机IP
            return domain;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return "";
    }
    public static void main(String[] args) {
        try {
            InetAddress ia = InetAddress.getLocalHost();

            System.out.println("IP:"+ia.getHostAddress());

            System.out.println("计算机主机名:"+ia.getHostName());

            System.out.println("?:"+ia.getCanonicalHostName());

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

}
