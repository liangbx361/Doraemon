package com.wish.doraemon;

import com.wish.doraemon.dao.model.RoleEnum;
import com.wish.doraemon.dao.model.User;
import com.wish.doraemon.dao.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserGenerate {

    @Autowired
    private UserRepository userRepository;

//    @Test
    public void addAdmin() {
        User user = new User();
        user.setName("admin");
        user.setPassword(new BCryptPasswordEncoder().encode("123456"));
        List<String> roles = new ArrayList<>(1);
        roles.add(RoleEnum.ADMIN.value());
        user.setRoles(roles);
        userRepository.save(user);
    }
}
