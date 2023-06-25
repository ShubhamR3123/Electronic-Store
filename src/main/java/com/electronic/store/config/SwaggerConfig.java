package com.electronic.store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(getInfo()).securitySchemes(Arrays.asList()).select()
//                .apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
                .select().paths(PathSelectors.any()).build().apiInfo(getInfo());
    }



    private ApiInfo getInfo(){
return new ApiInfo(
        "ELECTRONIC STORE APPLICATION",
                "Developed By Shubham..",
                "V2",
                "Terms of service",
                new Contact("ShubhamIT", "www.shubhamit.com", "contactus@shubhamit .com"),
                "License of API", "API license URL", Collections.emptyList());
    }
//        return new ApiInfo("Electronic Store Application: Backend Course",
//                "This code is developed by  Shubham","V1",null,new Contact("Shubham IT", "Shubhamdhokchaule3108@gmail.com", "https://Shubhamdhokchaule3108@gmail.com")
//
//
//
//
//             "Terms of services", "", "License of Apis", "Shubhamdhokchaule3108@gmail.com", "https://Shubhamdhokchaule3108@gmail.com");
//
//    }
}
