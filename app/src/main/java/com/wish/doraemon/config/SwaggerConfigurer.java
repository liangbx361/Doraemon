package com.wish.doraemon.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@Profile("dev")
@EnableSwagger2
public class SwaggerConfigurer {

    @Bean
    public Docket buildDocket() {
        Predicate<RequestHandler> selector1 = RequestHandlerSelectors.basePackage("com.wish.doraemon.user.controller");
        Predicate<RequestHandler> selector2 = RequestHandlerSelectors.basePackage("com.wish.doraemon.study.english.controller");

        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(buildApiInfo())
            .select()
            .apis(Predicates.or(selector1, selector2))
            .paths(PathSelectors.any())
            .build()
            .enable(true);
    }

    private ApiInfo buildApiInfo() {
        return new ApiInfoBuilder()
            .title("Doraemon API文档")
            .contact(new Contact("梁宝贤", "", "1876830228@qq.com"))
            .version("1.0")
            .build();
    }



}
