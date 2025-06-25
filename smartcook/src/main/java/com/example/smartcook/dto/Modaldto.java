package com.example.smartcook.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users") // This tells Spring this is a MongoDB document
public class Modaldto {

    @Id
    private String id;
    private String email;
    private String password;

    // Default constructor (required by MongoDB)
    public Modaldto() {}

    public Modaldto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}