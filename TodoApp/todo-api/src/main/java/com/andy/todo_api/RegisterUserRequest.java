package com.andy.todo_api;

// The client sends the password so the backend can check and encode it before saving. The request should not contain id or passwordHash: PostgreSQL generates the ID, and the backend creates the hash. We’ll add the encoding and endpoint after this class.
public class RegisterUserRequest {
    private String email;
    private String password;

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;    
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

