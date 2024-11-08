package com.example.MovieBooking.table;

import jakarta.persistence.*;

@Entity
public class Admin {
    @Id
    @Column(name = "username")
    private String username;

    @Column (name = "password")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
