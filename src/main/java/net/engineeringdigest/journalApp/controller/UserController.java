package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.DTO.UpdatePasswordDTO;
import net.engineeringdigest.journalApp.DTO.UserDTO;
import net.engineeringdigest.journalApp.exceptions.*;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/create")
    public String saveUser(@RequestBody UserDTO userDTO){
        userService.saveUser(userDTO);
        return "User saved successfully.";
    }


    @PatchMapping("/updatePassword/{username}")
    public String updatePassword(@RequestBody UpdatePasswordDTO updatePasswordDTO ,@PathVariable String username){
        userService.updatePassword(updatePasswordDTO , username);
        return "Password updated successfully.";
    }

    @DeleteMapping("/deleteUser")
    public String deleteUser(@AuthenticationPrincipal UserDetails userdetails){
        userService.deleteUser(userdetails);
        return  "User deleted successfully.";
    }
}
