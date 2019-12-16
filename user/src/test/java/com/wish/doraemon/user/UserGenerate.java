package com.wish.doraemon.user;

import com.wish.doraemon.user.dao.model.RoleEnum;
import com.wish.doraemon.user.dao.model.User;
import com.wish.doraemon.user.dao.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration
public class UserGenerate {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void addAdmin() {
        User user = new User();
        user.setName("admin");
        user.setPassword(new BCryptPasswordEncoder().encode("123456"));
        List<String> roles = new ArrayList<>(1);
        roles.add(RoleEnum.ADMIN.value());
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Test
    public void getPassword() {
        String password = new BCryptPasswordEncoder().encode("123456");
        System.out.println(password);
    }
}
