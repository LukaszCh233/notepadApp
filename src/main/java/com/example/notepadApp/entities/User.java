package com.example.notepadApp.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "userData")
@Getter
@Setter
public class User {
    @Id
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private String role;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
