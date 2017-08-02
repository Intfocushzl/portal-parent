package com.yonghui.portal.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger2 配置类
 * Created by zhanghai on 2017/8/2.
 */

@EnableWebMvc
@EnableSwagger2 // 使swagger2生效
@Configuration  // 配置注解，自动在本类上下文加载一些环境变量信息
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo()) // 对所有api进行监控
                .select()           // 选择哪些路径和API会生成document
                .apis(RequestHandlerSelectors.basePackage("com.yonghui.portal.controller.api")) // 指定controller的包
                .paths(PathSelectors.any()) // 对所有路径进行监控
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("接口列表 v1.0.2")           // 设置文档的标题
                .description("数据化运营平台")      // 设置文档的描述
                //.termsOfServiceUrl("http://localhost:9090/swagger-ui.html") //设置文档的License信息
                .version("1.0.2")       // 设置文档的版本信息
                .build();
    }
}
