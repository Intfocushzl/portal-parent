package com.yonghui.portal.db;

import com.yonghui.portal.annotation.DataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * 定义AOP切面以便拦截所有带有注解@DataSource的方法，
 * 取出注解的值作为数据源标识放到DynamicDataSourceHolder的线程变量中
 * Created by 张海 on 2017/04/28.
 */
public class DataSourceAspectBean {

    private String defaultDataSource = null;

    /**
     * 拦截目标方法，获取由@DataSource指定的数据源标识，设置到线程存储中以便切换数据源
     * @param joinPoint
     * @throws Throwable
     */
    public void before(JoinPoint joinPoint) throws Throwable {
        Class<?> target = joinPoint.getTarget().getClass();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 默认使用目标类型的注解，如果没有则使用其实现接口的注解
        for (Class<?> clazz : target.getInterfaces()) {
            resolveDataSource(clazz, signature.getMethod());
        }
        resolveDataSource(target, signature.getMethod());
        System.out.println("======= [通过注解切换数据源] ====== Use the data source: " + DataSourceContextHolder.get());
    }

    /**
     * 提取目标对象方法注解和类型注解中的数据源标识
     * 注解@DataSource既可以加在方法上，也可以加在接口或者接口的实现类上，
     * 优先级别：方法>实现类>接口。
     * 如果接口、接口实现类以及方法上分别加了@DataSource注解来指定数据源，则优先以方法上指定的为准。
     * @param clazz
     * @param method
     */
    private void resolveDataSource(Class<?> clazz, Method method) {
        try {
            DataSource dataSource = null;
            Class<?>[] types = method.getParameterTypes();
            // 默认使用类型注解
            if (clazz.isAnnotationPresent(DataSource.class)) {
                dataSource = clazz.getAnnotation(DataSource.class);
            }
            // 方法注解可以覆盖类型注解
            Method m = clazz.getMethod(method.getName(), types);
            if (m != null && m.isAnnotationPresent(DataSource.class)) {
                dataSource= m.getAnnotation(DataSource.class);
            }
            // 设置数据源
            if (null != dataSource && StringUtils.hasText(dataSource.value())){
                DataSourceContextHolder.set(dataSource.value());
            }else{
                DataSourceContextHolder.set(defaultDataSource);
            }
        } catch (Exception e) {
            System.out.println("======= [通过注解切换数据源异常] ====== " + clazz + ":" + e.getMessage());
        }
    }

    public void after(JoinPoint joinPoint) {
        DataSourceContextHolder.remove();
    }

    public void setDefaultDataSource(String defaultDataSource) {
        Assert.notNull(defaultDataSource, "This 'defaultDataSource' must not be null 请在配置文件中设置默认数据源.");
        this.defaultDataSource = defaultDataSource;
    }

}
