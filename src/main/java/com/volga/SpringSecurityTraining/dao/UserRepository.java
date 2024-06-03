package com.volga.SpringSecurityTraining.dao;

import com.volga.SpringSecurityTraining.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstByUsername(String username);

    User save(User user);

    boolean existsByEmail(String email);
}
