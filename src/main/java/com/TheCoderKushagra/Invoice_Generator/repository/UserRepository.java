package com.TheCoderKushagra.Invoice_Generator.repository;

import com.TheCoderKushagra.Invoice_Generator.entity.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<Users, String> {
    Users findByUserName(String username);
    void deleteByUserName(String username);
}
