package com.yonghui.portal.util;

import com.sun.management.OperatingSystemMXBean;
import org.apache.log4j.Logger;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.util.Map;
import java.util.Properties;

/**
 * Created by 20160988 on 2017/1/12.
 */
public class GetComputer {
    private static final Logger logger = Logger.getLogger(GetComputer.class);

    private static Map<String, String> map = System.getenv();

    private static OperatingSystemMXBean osmb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    /**
     * 获取用户名
     *
     * @return
     */
    public static String getUserName() {
        logger.info(map.get("USERNAME"));
        return map.get("USERNAME");
    }

    /**
     * 获取计算机名
     *
     * @return
     */
    public static String getComputerName() {
        logger.info(map.get("COMPUTERNAME"));
        return map.get("COMPUTERNAME");
    }

    /**
     * 获取计算机域名
     *
     * @return
     */
    public static String getUserDomain() {
        logger.info(map.get("USERDOMAIN"));
        return map.get("USERDOMAIN");
    }

    /**
     * 获取本机ip
     *
     * @return
     */
    public static String getIp() {
        String ip = getInetAddress().getHostAddress().toString();
        logger.info("本机IP：" + ip);
        return ip;
    }

    /**
     * 本机名称
     *
     * @return
     */
    public static String getHostName() {
        String hostName = getInetAddress().getHostName().toString(); //获取本机计算机名称
        logger.info("本机名称:" + hostName);
        return hostName;
    }

    /**
     * 得到计算机的ip,名称,操作系统名称,操作系统版本
     *
     * @return
     */
    public static InetAddress getInetAddress() {
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addr;
    }

    // 检测当前JVM内存使用情况

    /**
     * 系统物理内存总计
     *
     * @return
     */
    public static long getTotalPhysicalMemorySize() {
        return osmb.getTotalPhysicalMemorySize() / 1024 / 1024;
    }

    /**
     * 系统物理可用内存总计
     *
     * @return
     */
    public static long getFreePhysicalMemorySize() {
        return osmb.getFreePhysicalMemorySize() / 1024 / 1024;
    }


    /**
     * 获取jvm虚拟机可以控制的最大内存数量
     * 虚拟机（这个进程）能构从操作系统那里挖到的最大的内存
     *
     * @return
     */
    public static long getMaxControl() {
        return Runtime.getRuntime().maxMemory() / 1024 / 1024;
    }

    /**
     * 获取jvm虚拟机当前已使用的内存数量
     * 虚拟机现在已经从操作系统那里挖过来的内存大小
     *
     * @return
     */
    public static long getCurrentUse() {
        return Runtime.getRuntime().totalMemory() / 1024 / 1024;
    }

    /**
     * jvm虚拟机挖过来而又没有用上的内存
     *
     * @return
     */
    public static long getFreeMemory() {
        return Runtime.getRuntime().freeMemory() / 1024 / 1024;
    }

    /**
     * jvm可用的处理器数量
     *
     * @return
     */
    public static long getAvailableProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * 线程总数
     *
     * @return
     */
    /*public static long getTotalThread() {
        //return (MaxProcessMemory - JVMMemory - ReservedOsMemory) / (ThreadStackSize);
        // 获得线程总数
        ThreadGroup parentThread;
        for (parentThread = Thread.currentThread().getThreadGroup(); parentThread
                .getParent() != null; parentThread = parentThread.getParent())
            ;
        return parentThread.activeCount();
    }*/

    /**
     * 系统参数对象
     *
     * @return
     */
    public static Properties getProperties() {
        return System.getProperties();
    }

    public static void main(String[] args) {
        /*System.out.println(getIp());
        System.out.println(getHostName());
        System.out.println(getUserDomain());
        System.out.println(getComputerName());
        System.out.println(getUserName());*/
        System.out.println("==========system");
        System.out.println(getTotalPhysicalMemorySize());
        System.out.println(getFreePhysicalMemorySize());

        System.out.println("==========jvm");
        System.out.println(getMaxControl());
        System.out.println(getCurrentUse());
        System.out.println(getFreeMemory());

    }
}
