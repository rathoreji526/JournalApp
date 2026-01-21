package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.exceptions.OTPExpiredException;
import net.engineeringdigest.journalApp.exceptions.TooFastOTPRequestException;
import net.engineeringdigest.journalApp.exceptions.WrongOTPException;
import net.engineeringdigest.journalApp.model.OTP;
import net.engineeringdigest.journalApp.repository.OTPRepository;
import net.engineeringdigest.journalApp.utilities.CommonUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class OTPService {

    @Autowired
    OTPRepository otpRepository;

    //generate otp  & store hashed otp in the DB  -> verify otp ->
    public String generateOTP(String email , String purpose){

        Optional<OTP> dbOTP = otpRepository
                .findTopByEmailAndUsedFalseOrderByCreatedAtDesc(email);

        if(dbOTP.isPresent()){
            LocalDateTime created = dbOTP.get().getCreatedAt();

            if(created.plusMinutes(2).isAfter(LocalDateTime.now())){
                throw new TooFastOTPRequestException("New OTP can be requested after 2 minutes.");
            }
        }

        String rawOTP = CommonUtilities.OTPGenerator();
        OTP otp = new OTP();
        Date expiry = Date.from(Instant.now().plus(10 , ChronoUnit.MINUTES));

        otp.setHashedOTP(CommonUtilities.encode(rawOTP));
        otp.setCreatedAt(LocalDateTime.now());
        otp.setUsed(false);
        otp.setPurpose(purpose);
        otp.setEmail(email);
        otp.setExpiry(expiry);

        otpRepository.save(otp);
        return rawOTP;
    }

    public boolean verifyOTP(String email , String otp , String purpose){

        if(otp==null || otp.isBlank()){
            throw new OTPExpiredException("OTP not provided!");
        }
        OTP dbOTP = otpRepository
                .findTopByEmailAndUsedFalseOrderByCreatedAtDesc(email)
                .orElseThrow(() -> new OTPExpiredException("OTP not found or expired!"));

        if(!CommonUtilities.matches(otp , dbOTP.getHashedOTP())){
            throw new WrongOTPException("Incorrect otp.");
        }
        if(Instant.now().isAfter(dbOTP.getExpiry().toInstant())){
            throw new OTPExpiredException("This otp is expired!");
        }
        if(!dbOTP.getPurpose().equals(purpose)){
            throw new OTPExpiredException("Incorrect OTP.");
        }

        dbOTP.setUsed(true);
        otpRepository.save(dbOTP);
        return true;
    }

}
