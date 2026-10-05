package com.andy.todo_api;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "app_user") 
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id;

    // unique and non-null
    @Column(nullable = false, unique = true)
    private String email;

    // non-null
    @Column(nullable = false)
    private String passwordHash;

    // preserve no-arg empty ctor for JPA when its retrieving exisitng user.
    // for more details, check backend.md repository part-task.java
    protected AppUser() {}

    // another ctor for creating a new appUser.
    // There’s no id argument because PostgreSQL generates the ID when the user is saved. The passwordHash argument must already be encoded—we’ll add that step when we build registration. This constructor does not register or save anyone by itself.
    public AppUser(String email, String passwordHash) {
        this.email = email;
        this.passwordHash = passwordHash;
    }
}