package com.tp39;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    /**
     * Find a role by its name
     * @param name the role name (e.g., "ROLE_ADMIN", "ROLE_USER")
     * @return Optional containing the role if found
     */
    Optional<Role> findByName(String name);

    /**
     * Check if a role exists by name
     * @param name the role name
     * @return true if role exists
     */
    boolean existsByName(String name);
}
