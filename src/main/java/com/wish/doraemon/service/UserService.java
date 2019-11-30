package com.wish.doraemon.service;

import com.wish.doraemon.dao.model.User;
import com.wish.doraemon.dao.repository.UserRepository;
import com.wish.doraemon.exception.UserNameAlreadyExistException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void createUser(Map<String, String> registerUser) {
        // 查找是否已存在相同用户名
        Optional<User> optionalUser = userRepository.findByName(registerUser.get("username"));
        if (optionalUser.isPresent()) {
            throw new UserNameAlreadyExistException("User name already exist!Please choose another user name");
        }

//        // 保存用户
//        User user = new User();
//        user.setUsername(registerUser.get("username"));
//        user.setPassword(bCryptPasswordEncoder.encode(registerUser.get("password")));
//        user.setRole(RoleEnum.ADMIN.value());
//        userRepository.save(user);
    }

    public User findUserByUserName(String name) {
        return userRepository.findByName(name)
            .orElseThrow(() -> new UsernameNotFoundException("No user found with username " + name));
    }

    public void deleteUserByUserName(String name) {
        userRepository.deleteUserByName(name);
    }

    public Page<User> getAllUser(int pageNo, int pageSize) {
        return userRepository.findAll(PageRequest.of(pageNo, pageSize));
    }
}
