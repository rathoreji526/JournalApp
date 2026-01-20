package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.DTO.UserCredentialsDTO;
import net.engineeringdigest.journalApp.DTO.UpdatePasswordDTO;
import net.engineeringdigest.journalApp.DTO.UserDTO;
import net.engineeringdigest.journalApp.exceptions.*;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/create")
    public ResponseEntity<String> saveUser(@RequestBody UserDTO userDTO){
        try{
            userService.saveUser(userDTO);
        }catch (UserAlreadyExistsException |
                PasswordLengthException e){
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
        return new ResponseEntity<>("User saved successfully." , HttpStatus.CREATED);
    }


    @PatchMapping("/updatePassword/{username}")
    public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordDTO updatePasswordDTO ,@PathVariable String username){
        try{
            userService.updatePassword(updatePasswordDTO , username);
        }catch (UserNotExistsException |
                WrongPasswordException |
                PasswordLengthException |
                SamePasswordException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
        return new ResponseEntity<>("Password updated successfully." , HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(@AuthenticationPrincipal UserDetails userdetails){
        try{
            userService.deleteUser(userdetails);
        }catch (UserNotExistsException |
                WrongPasswordException e){
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
        return new ResponseEntity<>("User deleted successfully." , HttpStatus.OK);
    }
}
