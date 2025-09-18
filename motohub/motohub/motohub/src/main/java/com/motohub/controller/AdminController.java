package com.motohub.controller;

import com.motohub.dto.AuthRequest;
import com.motohub.dto.AuthResponse;
import com.motohub.model.Admin;
import com.motohub.security.JwtUtil;
import com.motohub.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registerAdmin(@RequestBody Admin admin) {
        if (!admin.getEmail().endsWith("@motohub.com")) {
            return ResponseEntity.badRequest().body("El email debe terminar en @motohub.com");
        }
        Admin savedAdmin = adminService.registerAdmin(admin);
        return ResponseEntity.ok(savedAdmin);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        Optional<Admin> adminOpt = adminService.findByEmail(request.getEmail());
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            if (adminService.checkPassword(admin, request.getPassword())) {
                String token = jwtUtil.generateToken(admin.getEmail());
                return ResponseEntity.ok(new AuthResponse(token));
            } else {
                return ResponseEntity.status(401).body(null);
            }
        } else {
            return ResponseEntity.status(401).body(null);
        }
    }
}
