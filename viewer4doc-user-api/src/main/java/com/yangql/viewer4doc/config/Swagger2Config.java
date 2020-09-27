package com.yangql.viewer4doc.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2Config {
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Viewer4doc-User-API")
                .version("1.0")
                .description("viewer4doc 의 User API 입니다.")
                .build();
    }

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(this.apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build()
                .globalOperationParameters(authParam());

    }
    private List<Parameter> authParam(){
        Parameter parameter = new ParameterBuilder()
                .name("Authorization: ")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .defaultValue("eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjMwLCJlbWFpbCI6InRlc3QxIn0.uVHzuqkAwnxdOcH9TMju1RcbbfeqVaVJ_y5fwVoCfeY")
                .required(true)
                .build();
        List<Parameter> params = new ArrayList<>();
        params.add(parameter);
        return params;
    }

}
