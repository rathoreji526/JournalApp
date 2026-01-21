package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.model.OTP;
import org.springframework.data.mongodb.core.convert.ObjectPath;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OTPRepository extends MongoRepository<OTP, String>{
    public Optional<OTP> findTopByEmailAndUsedFalseOrderByCreatedAtDesc(String email);
}
