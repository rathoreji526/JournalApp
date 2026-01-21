package net.engineeringdigest.journalApp.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Document(collection = "otp")
@NoArgsConstructor
public class OTP {
    @Id
    private String email;
    @NonNull
    private String purpose;
    private String hashedOTP;
    private boolean used;
    private LocalDateTime createdAt;
    private Date expiry;
}
