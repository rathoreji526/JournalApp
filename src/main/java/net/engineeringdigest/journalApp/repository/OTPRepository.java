package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.model.OTP;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OTPRepository extends MongoRepository<OTP, String>{
    public OTP findByEmail(String email);
}
