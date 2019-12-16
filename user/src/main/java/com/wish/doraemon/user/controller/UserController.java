package com.wish.doraemon.user.controller;

import com.wish.doraemon.user.dao.model.User;
import com.wish.doraemon.user.security.jwt.JwtHandler;
import com.wish.doraemon.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final HttpServletRequest request;

    @Autowired
    public UserController(@Lazy UserService userService, HttpServletRequest request) {
        this.userService = userService;
        this.request = request;
    }

    @GetMapping("/current")
    public ResponseEntity<User> queryUser() {
        String authorization = request.getHeader("Authorization");
        String token = authorization.replace(JwtHandler.TOKEN_PREFIX, "");
        String username = JwtHandler.getInstance().getUsernameByToken(token);
        User user = userService.findUserByUserName(username);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Page<User>> queryUsers(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                                 @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Page<User> users = userService.getAllUser(pageNo, pageSize);
        return ResponseEntity.ok().body(users);
    }

    
}
