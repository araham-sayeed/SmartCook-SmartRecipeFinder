package com.example.smartcook.service;

import com.example.smartcook.dto.Modaldto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class ModalService {

    @Autowired
    private MongoTemplate mongoTemplate;
    // for login
    public boolean login(String email, String password) {
        // Query to find the user by email and password
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email).and("password").is(password));
        Modaldto user = mongoTemplate.findOne(query, Modaldto.class, "users");

        return user != null;
    }

    //for sign up
    public boolean signup(String email, String password) {
        // Check if the user already exists
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
        boolean exists = mongoTemplate.exists(query, Modaldto.class, "users");

        if (exists) return false;

        // Create a new user and save to MongoDB
        Modaldto newUser = new Modaldto(email, password);
        mongoTemplate.save(newUser, "users");

        return true;
    }
}