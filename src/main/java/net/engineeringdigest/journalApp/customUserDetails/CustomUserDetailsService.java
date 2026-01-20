package net.engineeringdigest.journalApp.customUserDetails;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.model.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
//        log.info("\nusername is: {}\n\n",username);

        User user = userService.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        return new CustomUserDetails(user);
    }
}
