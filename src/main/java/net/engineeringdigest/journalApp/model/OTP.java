package net.engineeringdigest.journalApp.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "otp")
public class OTP {
    @Id
    private String email;
    private String hashedOTP;
    private boolean used;
    private Date expiry;
}
