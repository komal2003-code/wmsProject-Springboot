
package com.wms.controller;

import com.wms.entity.User;
import com.wms.service.UserService;
import com.wms.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService service;

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return service.register(user);
    }
    @PostMapping("/login")
    public String login(@RequestBody User user) {

        User dbUser = service.findByUsername(user.getUsername());

        if (!encoder.matches(user.getPassword(), dbUser.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(user.getUsername());
    }
    @GetMapping("/hello")
    public String hello() {
        return "Hello Admin";
    }
    
}