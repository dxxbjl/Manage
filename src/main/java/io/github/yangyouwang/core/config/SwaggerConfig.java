package io.github.yangyouwang.core.config;

import io.github.yangyouwang.common.annotation.ApiVersion;
import io.github.yangyouwang.common.constant.ApiVersionConstant;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author yangyouwang
 * @title: SwaggerConfig
 * @projectName crud
 * @description: swagger配置
 * @date 2021/3/207:13 PM
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger.enable}")
    private boolean enable;

    /**
     * swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
     */
    @Bean
    public Docket createRestApi() {
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        //设置所有接口的请求头
        ticketPar.name("token").description("用户token信息")
                .modelRef(new ModelRef("string")).parameterType("header")
                //header中的ticket参数非必填，传空也可以
                .required(false).build();
        pars.add(ticketPar.build());    //根据每个方法名也知道当前方法在设置什么参数
        return new Docket(DocumentationType.SWAGGER_2)
                //接扣文档的相关信息
                .apiInfo(apiInfo())
                //配置是否启用swagger
                .enable(enable)
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);
    }

    @Bean
    public Docket appV1(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName(ApiVersionConstant.SWAGGER_API_V1)
                .select()
                .apis(input -> {
                    ApiVersion apiVersion = input.getHandlerMethod().getMethodAnnotation(ApiVersion.class);
                    if(apiVersion != null && Arrays.asList(apiVersion.group()).contains(ApiVersionConstant.SWAGGER_API_V1)){
                        return true;
                    }
                    return false;
                })
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(globalOperation());
    }

    private List<Parameter> globalOperation(){
        List<Parameter> pars = new ArrayList<>();
        ParameterBuilder tokenPar = new ParameterBuilder();
        tokenPar.name("Authorization").description("token").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        ParameterBuilder versionPar = new ParameterBuilder();
        versionPar.name("version").description("版本").modelRef(new ModelRef("string")).parameterType("path").defaultValue("v1").required(true).build();
        pars.add(versionPar.build());
        return pars;
    }

    /***
     * 构建 api文档的详细信息函数
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("crud-api接口文档")
                .description("powered by wang")
                .termsOfServiceUrl("http://www.wbd.plus/")
                .contact(new Contact("杨有旺", "http://www.wbd.plus/", "616505453@qq.com"))
                .version("1.0")
                .build();
    }
}