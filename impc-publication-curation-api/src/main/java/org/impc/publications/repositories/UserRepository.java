package org.impc.publications.repositories;

import org.bson.types.ObjectId;
import org.impc.publications.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findUserByUsername(String username);

}
