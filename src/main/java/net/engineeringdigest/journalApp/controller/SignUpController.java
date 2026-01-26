package net.engineeringdigest.journalApp.controller;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.DTO.EmailAndOTPDTO;
import net.engineeringdigest.journalApp.DTO.EmailDTO;
import net.engineeringdigest.journalApp.DTO.SignUpDTO;
import net.engineeringdigest.journalApp.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/signup")
public class SignUpController {

    @Autowired
    SignUpService signUpService;

    @PostMapping("/otpRequest")
    public String otpRequest(@RequestBody EmailDTO dto){
            signUpService.sendSignUpMail(dto);
            return "OTP sent to your email.";
    }

    @PostMapping("/verifyOTP")
    public String verifyOTP(@RequestBody EmailAndOTPDTO dto){
            String response = signUpService.verifyOTP(dto);
            return response;
    }

    @PostMapping("/completeSignUp")
    public String completeSignUp(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody  SignUpDTO dto
            ){
            String token = authHeader.replace("Bearer ","");
            String jwtToken = signUpService.completeSignUp(token , dto);
            return jwtToken;

    }

}
