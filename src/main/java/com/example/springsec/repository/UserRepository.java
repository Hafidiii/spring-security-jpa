package com.example.springsec.repository;

import com.example.springsec.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("SELECT DISTINCT u FROM User u INNER JOIN u.roles r WHERE  r.name='ROLE_ADMIN'")
    List<User> findAllAdminMembers();
}
