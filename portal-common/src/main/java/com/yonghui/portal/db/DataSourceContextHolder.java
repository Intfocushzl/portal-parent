package com.yonghui.portal.db;

/**
 * 数据源类型管理工具
 * 数据源的名称常量和一个获得和设置上下文环境的类，主要负责改变上下文数据源的名称
 * Created by 张海 on 2017/04/28.
 */
public class DataSourceContextHolder {

    /**
     * 线程本地环境
     * 避免多线程操作数据源时互相干扰,数据源标识保存在线程变量中，使用ThreadLocal作线程隔离
     */
    private static final ThreadLocal<String> CONTEXTHOLDER = new ThreadLocal<String>();

    /**
     * 设置数据源类型
     * @param dataSource
     */
    public static void set(String dataSource) {
        CONTEXTHOLDER.set(dataSource);
    }

    /**
     * 获取数据源类型
     * @return
     */
    public static String get() {
        String dataSource = CONTEXTHOLDER.get();
        // 如果没有指定数据源，使用默认数据源 mysql从库
        if (null == dataSource) {
            CONTEXTHOLDER.set(DataSourceEnum.MYSQL_PORTAL_SLAVE.getDefault());
        }
        return CONTEXTHOLDER.get();
    }

    /**
     * 清除数据源类型
     */
    public static void remove() {
        CONTEXTHOLDER.remove();
    }

}
