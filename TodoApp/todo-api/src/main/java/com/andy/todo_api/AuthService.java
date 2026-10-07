package com.andy.todo_api;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service 
public class AuthService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public int register(RegisterUserRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        // validation for email and password. reject invalid emial/password and reject existing email
        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please Enter a valid Email or/and Password");
        }

        if (appUserRepository.findByEmail(email).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "The email has already been registered");
        }

        AppUser newAppUserWithDefaultId = new AppUser(email, passwordEncoder.encode(password));
        AppUser newAppUserWithGeneratedId = appUserRepository.save(newAppUserWithDefaultId);
        return newAppUserWithGeneratedId.getId();
    }

    public int authenticate(LoginRequest request) {
        // 1. read email and password
        String email = request.getEmail();
        String password = request.getPassword();

        // 2. reject blank values with 400 bad request
        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email or password");
        }

        // 3. reject unregistered email by 401 UNAUTHORIZED
        AppUser user = appUserRepository.findByEmail(email).orElseThrow(() -> {
            return new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        });
        
        // 4. compare the input passord with stored encodedpassword, if passed => return user id, if failed => 401 unauthorized.
        // first argument has to be the input password, second has to be the stored encoded password.
        if (passwordEncoder.matches(password, user.getPasswordHash())) {
            return user.getId();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }
    }
}
