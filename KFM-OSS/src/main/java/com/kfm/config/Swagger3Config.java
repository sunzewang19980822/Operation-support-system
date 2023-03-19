package com.kfm.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableOpenApi
public class Swagger3Config {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo());
//                .select()
//                增加下行会之扫描配置有API操作的类，如果不配置自动会加载控制器类
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
//                .paths(PathSelectors.any())
//                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("vueOSS 接口文档")
                .description("更多请咨询服务开发者 John。")
                .contact(new Contact("John。", "http://itlaocing.com", ""))
                .version("1.0")
                .build();
    }
}