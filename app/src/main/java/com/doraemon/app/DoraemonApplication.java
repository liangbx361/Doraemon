package com.doraemon.app;

import com.doraemon.user.dao.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = {"com.doraemon"})
public class DoraemonApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DoraemonApplication.class, args);

        User user = new User();
    }
}
