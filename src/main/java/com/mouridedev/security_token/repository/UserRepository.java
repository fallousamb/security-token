package com.mouridedev.security_token.repository;


import com.mouridedev.security_token.entities.User;
import com.mouridedev.security_token.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {


    /**
     * Finds a user by their email.
     *
     * @param username the user's email
     * @return the user or empty if not found
     */
    Optional<User> findByEmail(final String username);

    /**
     * Finds a user by their role.
     *
     * @param role the role of the user
     * @return a user with the specified role or null if not found
     */
    User findByRole(Role role);
}
