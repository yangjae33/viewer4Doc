package com.yangql.viewer4doc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;

@Configuration
@EnableSwagger2
public class Swagger2Config {
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Viewer4doc-User-API")
                .version("1.0")
                .description("viewer4doc 의 User API 입니다.")
//                .license("라이센스 작성")
//                .licenseUrl("라이센스 URL 작성")
                .build();
    }
    @Bean
    public Docket api(){

        ArrayList<ResponseMessage> responseMessages = new ArrayList<ResponseMessage>();
        responseMessages.add(
                new ResponseMessageBuilder()
                        .code(400)
                        .message("Bad Request")
                        .build());
        responseMessages.add(
                new ResponseMessageBuilder()
                        .code(201)
                        .message("Created")
                        .build());
        responseMessages.add(
                new ResponseMessageBuilder()
                        .code(200)
                        .message("Ok")
                        .build());

        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(this.apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();

    }
}
