package net.engineeringdigest.journalApp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import net.engineeringdigest.journalApp.model.Role;
import net.engineeringdigest.journalApp.model.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class JwtUtil {

    @Value("${jwt.token.expiration.time}")
    private Long expirationTime;
    @Value("${jwt.secret.password}")
    private String secretPassword;



    @Autowired
    UserService userService;


    /// generating token
    public String generateJwtToken(String username,
                                   String email,
                                   String mobNum,
                                   List<String> roleName){
        Map<String , Object> claims = new HashMap<>();
        claims.put("username" , username);
        claims.put("email" , email);
        claims.put("mobNum" , mobNum);
        claims.put("roles" , roleName);

        String jwtToken = Jwts
                .builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .setId(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS256,secretPassword)
                .compact();

//        String jwtToken = Jwts
//                .builder()
//                .setExpiration(new Date(System.currentTimeMillis()+expirationTime))
//                .setIssuedAt(new Date())
//                .signWith(SignatureAlgorithm.HS256,secretPassword)
//                .setClaims(claims)
//                .compact();
        log.info("\njwt token created {}\n",jwtToken);
        return jwtToken;
    }

    /// decrypting token
    public Claims decryptToken(String jwtToken){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretPassword)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
        return claims;
    }

    /// validate token
    public boolean isTokenValid(String jwtToken){
        Claims claims = this.decryptToken(jwtToken);
        String email = claims.get("email", String.class);
        String mobNum = claims.get("mobNum", String.class);
        List<String> tokenRoles = claims.get("roles", List.class);

        User user = userService.getUserByEmail(email);

        if(user == null) {
            return false;
        }

        List<String> userRoles = user.getRoles();
        //check all roles

        for(String tokenRole : tokenRoles){
            boolean flag = false;
            for(String userRole : userRoles){
                if(userRole.equals(tokenRole)){
                    flag = true;
                    break;
                }
            }
            if(!flag) {

                System.out.println("Role mismatch");
                return false;
            }
        }
        return true;
    }

    @PostConstruct
    public void print(){

        String username = "kamal_kumar";
        String password = "kamal@123";
        String email = "kamal123@gmail.com";
        String phone = "9812323428";
        List<String> role = new ArrayList<>();
//        role.add("admin");


        log.info(expirationTime+" ");
//        String token = this.generateJwtToken(email , phone , role);
//        System.out.println("token is: "+token);

//        Claims decryptedToken = this.decryptToken(token);

//        System.out.println("decrypted token is: "+decryptedToken.toString());

//        System.out.println("token status valid or not: "+this.isTokenValid(token));

//        log.info("db password is: {}",dbPass);

    }


    //sign up token generation
    public String generateSignUpToken(String email){
        Map<String , Object> claims = new HashMap<>();
        claims.put("sub", "SIGNUP");
        claims.put("email" , email);
        claims.put("purpose" , "SIGNUP");

        String jwtToken = Jwts
                .builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS256 , secretPassword)
                .compact();
        return jwtToken;
    }


    //sign up token verify
    public String validateSignupToken(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(secretPassword)
                .parseClaimsJws(token)
                .getBody();

        if (!"SIGNUP".equals(claims.getSubject())) {
            throw new RuntimeException("Invalid token subject");
        }

        if (!"SIGNUP".equals(claims.get("purpose"))) {
            throw new RuntimeException("Invalid token purpose");
        }

        return claims.get("email", String.class);
    }


}
