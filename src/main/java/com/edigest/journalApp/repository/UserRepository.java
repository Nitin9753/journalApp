package com.edigest.journalApp.repository;

import com.edigest.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    public User findByUsername(String username);
    public void deleteByUsername(String username);

}
