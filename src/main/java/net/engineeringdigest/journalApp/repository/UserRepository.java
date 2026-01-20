package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User , ObjectId> {
    public User findByUsername(String username);
    public User findByEmail(String email);
    public User findByPhone(String phone);
}
