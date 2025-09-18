package com.motohub.controller;

import com.motohub.model.User;
import com.motohub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // Registro de usuario
    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);  // Devuelve el usuario creado como JSON
    }

    // Login de usuario
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        
        return "Usuario logueado con email: " + user.getEmail();
    }
}
