package com.wish.doraemon.dao.repository;

import com.wish.doraemon.dao.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByName(String name);

    void deleteUserByName(String name);
}
