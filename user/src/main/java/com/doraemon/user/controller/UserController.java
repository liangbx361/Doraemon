package com.doraemon.user.controller;

import com.doraemon.user.security.jwt.JwtHandler;
import com.doraemon.user.service.UserService;
import com.doraemon.user.dao.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(UserApiPath.USER)
public class UserController {

    private final UserService userService;
    private final HttpServletRequest request;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, HttpServletRequest request, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.request = request;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 创建新用户
     */
    @PostMapping("")
    @PreAuthorize("hasAnyRole('admin')")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userService.create(user);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/current")
    public ResponseEntity<User> queryCurrentUser() {
        User user = userService.findById(getCurrentUserId());
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> queryUser(@PathVariable Integer id) {
        User user = userService.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("")
    @PreAuthorize("hasAnyRole('admin')")
    public ResponseEntity<Page<User>> queryUsers(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                                 @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Page<User> users = userService.getAllUser(pageNo, pageSize);
        return ResponseEntity.ok().body(users);
    }

    @PutMapping("/current")
    public ResponseEntity<User> updateCurrentUser(@RequestBody User user) {
        user.setId(getCurrentUserId());
        user = userService.update(user);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) {
        user.setId(Integer.valueOf(id));
        user = userService.update(user);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable String id) {
        userService.delete(Integer.valueOf(id));
        return ResponseEntity.ok().build();
    }

    private Integer getCurrentUserId() {
        String authorization = request.getHeader("Authorization");
        String token = authorization.replace(JwtHandler.TOKEN_PREFIX, "");
        String userId = JwtHandler.getInstance().getUserId(token);
        return Integer.valueOf(userId);
    }
}
