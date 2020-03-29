package com.doraemon.user.service;

import com.doraemon.user.dao.model.User;
import com.doraemon.user.dao.repository.UserRepository;
import com.doraemon.user.exception.UserNameAlreadyExistException;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User create(User user) {
        // 查找是否已存在相同用户名
        Optional<User> optionalUser = userRepository.findByName(user.getName());
        if (optionalUser.isPresent()) {
            throw new UserNameAlreadyExistException("User name already exist! Please choose another user name");
        }

        // 保存用户
        return userRepository.save(user);
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public User findById(Integer id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new UsernameNotFoundException("No user found with id " + id));
    }

    public Page<User> getAllUser(int pageNo, int pageSize) {
        return userRepository.findAll(PageRequest.of(pageNo, pageSize));
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}
