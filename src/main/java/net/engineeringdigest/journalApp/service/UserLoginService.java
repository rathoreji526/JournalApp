package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.exceptions.UserNotExistsException;
import net.engineeringdigest.journalApp.exceptions.WrongPasswordException;
import net.engineeringdigest.journalApp.model.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import net.engineeringdigest.journalApp.security.JwtUtil;
import net.engineeringdigest.journalApp.utilities.CommonUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLoginService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtil jwtUtil;

    public String loginUser(String username , String password){
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UserNotExistsException("User with given username does not exists!");
        }
        if(!CommonUtilities.matches(password , user.getPassword())){
            throw new WrongPasswordException("Password you entered is incorrect");
        }
        String jwtToken  = jwtUtil.generateJwtToken(user.getUsername(),user.getEmail() , user.getPhone() , user.getRoles());
        return jwtToken;
    }

}
