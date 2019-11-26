package com.wish.doraemon.dao.repository;

import com.wish.doraemon.dao.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
