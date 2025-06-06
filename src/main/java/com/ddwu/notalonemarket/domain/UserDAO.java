package com.ddwu.notalonemarket.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUsername(String username);
}