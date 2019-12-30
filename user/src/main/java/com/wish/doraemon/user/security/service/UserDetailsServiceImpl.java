package com.wish.doraemon.user.security.service;

import com.wish.doraemon.user.dao.model.User;
import com.wish.doraemon.user.dao.repository.UserRepository;
import com.wish.doraemon.user.security.jwt.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepository.findByName(name)
            .orElseThrow(() -> new UsernameNotFoundException("No user found with username " + name));
        return new JwtUser(user.getId(), user.getName(), user.getPassword(), user.getRoles());
    }

}
