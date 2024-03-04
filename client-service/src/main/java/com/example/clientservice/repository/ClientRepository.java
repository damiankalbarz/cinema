package com.example.clientservice.repository;

import com.example.clientservice.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClientRepository extends MongoRepository<Client,String> {
}
