package net.engineeringdigest.journalApp.controller;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.DTO.EmailAndOTPDTO;
import net.engineeringdigest.journalApp.DTO.EmailDTO;
import net.engineeringdigest.journalApp.DTO.SignUpDTO;
import net.engineeringdigest.journalApp.exceptions.InvalidEmailException;
import net.engineeringdigest.journalApp.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/signup")
public class SignUpController {


    @Autowired
    SignUpService signUpService;


    @PostMapping("/otpRequest")
    public ResponseEntity<String> otpRequest(@RequestBody EmailDTO dto){
        try{
            signUpService.sendSignUpMail(dto);
            return new ResponseEntity<>("OTP sent to your email." , HttpStatus.OK);
        }catch (InvalidEmailException e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/verifyOTP")
    public ResponseEntity<String > verifyOTP(@RequestBody EmailAndOTPDTO dto){
        try{
//            log.info("\notp is {}\n",dto.getOTP());
            String response = signUpService.verifyOTP(dto);
            return new ResponseEntity<>(response , HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/completeSignUp")
    public ResponseEntity<String> completeSignUp(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody  SignUpDTO dto
            ){
        try{
            String token = authHeader.replace("Bearer ","");
            String jwtToken = signUpService.completeSignUp(token , dto);
            return new ResponseEntity<>(jwtToken , HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }

}
