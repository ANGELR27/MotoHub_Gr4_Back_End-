package com.motohub.controller;

import com.motohub.dto.AuthRequest;
import com.motohub.model.User;
import com.motohub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;  // Para manejar la autenticación

    // Registro de usuario
    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);  // Devuelve el usuario creado como JSON
    }

    // Login de usuario
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest request) {
        try {
            // Autenticar al usuario con AuthenticationManager
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            // Si la autenticación es exitosa, devolver una respuesta 200 OK
            return ResponseEntity.ok("Usuario logueado con éxito");

        } catch (AuthenticationException e) {
            // Si las credenciales no son correctas, devolver error 401 Unauthorized
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }
}
