package org.mifos.chatbot.server.repository;

import org.mifos.chatbot.server.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

@EnableMongoRepositories
@Repository
public interface ClientRepository extends MongoRepository<Client, Integer> {

}
