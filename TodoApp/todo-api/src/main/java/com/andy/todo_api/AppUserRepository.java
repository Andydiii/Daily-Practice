package com.andy.todo_api;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
    Optional<AppUser> findByEmail(String email);
}
