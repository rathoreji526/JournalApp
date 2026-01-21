package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.DTO.UserCredentialsDTO;
import net.engineeringdigest.journalApp.DTO.UpdatePasswordDTO;
import net.engineeringdigest.journalApp.DTO.UserDTO;
import net.engineeringdigest.journalApp.enums.RoleEnum;
import net.engineeringdigest.journalApp.exceptions.*;
import net.engineeringdigest.journalApp.model.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import net.engineeringdigest.journalApp.utilities.CommonUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;


    public String saveUser(UserDTO userDTO){

        if(userRepository.findByUsername(userDTO.getUsername()) == null){
            User user = new User();
            user.setUsername(userDTO.getUsername());
            if(userDTO.getPassword().length() < 6){
                throw new PasswordLengthException("Password length should be at least 6.");
            }
            user.setPassword(CommonUtilities.encode(userDTO.getPassword()));
            user.setEmail(userDTO.getEmail());
            if(userDTO.getPhone()!=null){
                user.setPhone(userDTO.getPhone());
            }
            user.setRoles(List.of(RoleEnum.ROLE_USER.name()));
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            userRepository.save(user);
        }else throw new UserAlreadyExistsException("User already exists!");
        return "User saved successfully.";
    }

    public String deleteUser( UserDetails userDetails){
        User user = this.findByUsername(userDetails.getUsername());

        userRepository.deleteById(user.getId());
        return "User deleted successfully";
    }

    public String updatePassword(UpdatePasswordDTO updatePasswordDTO , String username){
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UserNotExistsException("User with the given username does not exist.");
        }
        String oldEncodedPassword = CommonUtilities.encode(updatePasswordDTO.getOldPassword());
        String newEncodedPassword = CommonUtilities.encode(updatePasswordDTO.getNewPassword());
        String dbEncodedPassword = user.getPassword();

        if(!CommonUtilities.matches(updatePasswordDTO.getOldPassword() , dbEncodedPassword)){
            throw new WrongPasswordException("The current password you entered is incorrect.");
        }
        if(CommonUtilities.matches(updatePasswordDTO.getNewPassword() , dbEncodedPassword)){
            throw new SamePasswordException("New password must be different from your current password.");
        }
        if(updatePasswordDTO.getNewPassword().length() < 6){
            throw new PasswordLengthException("New password length should be at least 6.");
        }
        user.setPassword(CommonUtilities.encode(updatePasswordDTO.getNewPassword()));
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
        return "Password updated successfully!";
    }

    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UserNotExistsException("User not found!");
        }
        return user;
    }

    public String saveEntry(User user){
        userRepository.save(user);
        return "Entry saved successfully";
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

}
