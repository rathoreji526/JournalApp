package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.DTO.UserLoginDTO;
import net.engineeringdigest.journalApp.exceptions.UserNotExistsException;
import net.engineeringdigest.journalApp.exceptions.WrongPasswordException;
import net.engineeringdigest.journalApp.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class UserLoginController {

    @Autowired
    UserLoginService userLoginService;

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginDTO user){
        try{
            String jwtToken =  userLoginService.loginUser(user.getUsername() , user.getPassword());
            return new ResponseEntity<>(jwtToken , HttpStatus.OK);
        }catch (UserNotExistsException |
                WrongPasswordException e){
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}
