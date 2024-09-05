package com.crio.contest.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.crio.contest.entity.User;

public interface UserRepository extends MongoRepository<User, Long>{
    
}
