package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.DTO.UserLoginDTO;
import net.engineeringdigest.journalApp.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String loginUser(@RequestBody UserLoginDTO user){
            String jwtToken =  userLoginService.loginUser(user.getUsername() , user.getPassword());
            return jwtToken;
    }
}
