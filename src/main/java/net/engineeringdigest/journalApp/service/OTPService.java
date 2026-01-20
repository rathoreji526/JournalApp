package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.exceptions.OTPExpiredException;
import net.engineeringdigest.journalApp.exceptions.WrongOTPException;
import net.engineeringdigest.journalApp.model.OTP;
import net.engineeringdigest.journalApp.repository.OTPRepository;
import net.engineeringdigest.journalApp.utilities.CommonUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class OTPService {

    @Autowired
    OTPRepository otpRepository;

    //generate otp  & store hashed otp in the DB  -> verify otp ->

    public String generateOTP(String email){
        String rawOTP = CommonUtilities.OTPGenerator();
        OTP otp = new OTP();
        Date expiry = Date.from(Instant.now().plus(10 , ChronoUnit.MINUTES));

        otp.setHashedOTP(CommonUtilities.encode(rawOTP));
        otp.setUsed(false);
        otp.setEmail(email);
        otp.setExpiry(expiry);

        otpRepository.save(otp);
        return rawOTP;
    }

    public boolean verifyOTP(String email , String otp){
        OTP dbOTP = otpRepository.findByEmail(email);
        boolean matched =  CommonUtilities.matches(otp , dbOTP.getHashedOTP());
        if(otp == null){
            throw new OTPExpiredException("OTP not found or expired!");
        }
        if(!matched){
            throw new WrongOTPException("Incorrect otp");
        }
        if(dbOTP.isUsed()){
            throw new OTPExpiredException("This otp is already used");
        }
        if(Instant.now().isAfter(dbOTP.getExpiry().toInstant())){
            throw new OTPExpiredException("This otp is expired!");
        }
        dbOTP.setUsed(true);
        otpRepository.save(dbOTP);
        return true;
    }

}
