package com.tp39;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface userRepository extends JpaRepository<User, Integer> {
    List<User> findByNameContainingIgnoreCase(String name);
    List<User> findByRole(String role);
    User findByEmail(String email);
    Page<User> findAll(Pageable pageable);
}