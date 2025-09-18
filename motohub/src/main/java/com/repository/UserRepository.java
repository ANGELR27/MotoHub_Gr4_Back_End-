package com.motohub.repository;

import com.motohub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // Método para buscar usuario por correo
    Optional<User> findByEmail(String email);
}
