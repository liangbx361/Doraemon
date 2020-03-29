package com.doraemon.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication()
@ComponentScan(basePackages = {"com.doraemon"})
@EntityScan(basePackages = {"com.doraemon"})
@EnableJpaRepositories(basePackages = {"com.doraemon"})
public class DoraemonApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DoraemonApplication.class, args);
    }
}
