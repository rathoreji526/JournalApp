package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.DTO.EmailAndOTPDTO;
import net.engineeringdigest.journalApp.DTO.EmailDTO;
import net.engineeringdigest.journalApp.DTO.SignUpDTO;
import net.engineeringdigest.journalApp.DTO.UserDTO;
import net.engineeringdigest.journalApp.model.Role;
import net.engineeringdigest.journalApp.model.User;
import net.engineeringdigest.journalApp.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class SignUpService {
    @Autowired
    UserService userService;

    @Autowired
    OTPService otpService;

    @Autowired
    EmailService emailService;

    @Autowired
    JwtUtil jwtUtil;

    //user will enter his signup details/first verify user's email(email) => generate username of user  => enter user's information

    public void sendSignUpMail(EmailDTO dto){
        String otp = otpService.generateOTP(dto.getEmail());
        emailService.sendEmail( dto.getEmail(),"Your OTP for Journal App Sign-Up" , "Hello "+ dto.getEmail()+
                "\n" +
                "Thank you for signing up for Journal App.\n" +
                "\n" +
                "Your OTP is: " +otp+
                "\n" +
                "This OTP is valid for 10 minutes. Please do not share it with anyone.\n" +
                "\n" +
                "Thanks,\n" +
                "The Journal App Team" );
    }


    //verify password and return a signup token so user can enter his/her details and register to our platform ** token will valid only for 10 minutes!
    public String verifyOTP(EmailAndOTPDTO dto){
        otpService.verifyOTP(dto.getEmail() , dto.getOTP());
        return "OTP verified\n\n"+jwtUtil.generateSignUpToken(dto.getEmail());
    }

    //after verifying OTP register user from user's information
    public String completeSignUp(String token , SignUpDTO dto){
        String userEmail = jwtUtil.validateSignupToken(token);
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(userEmail);
        userDTO.setUsername(dto.getUsername());
        userDTO.setPassword(dto.getPassword());
        userService.saveUser(userDTO);

        //after saving we have to return jwt token to the user so user can continue to his/her profile
        User user = userService.findByUsername(dto.getUsername());
        String mobile = user.getPhone();
        String username = user.getUsername();
        List<String> roles = user.getRoles();
        return jwtUtil.generateJwtToken(username , userEmail , mobile , roles);
    }

}
