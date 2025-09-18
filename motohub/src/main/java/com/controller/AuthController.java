package com.motohub.controller;

import com.motohub.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // Endpoint para login del admin sin validar credenciales
    @PostMapping("/admin/login")
    public ResponseEntity<Map<String, String>> loginAdmin() {
        String token = jwtUtil.generateToken("admin"); // genera token para admin
        return ResponseEntity.ok(Map.of("token", token));
    }
}
