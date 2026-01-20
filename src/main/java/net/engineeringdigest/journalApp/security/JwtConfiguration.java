package net.engineeringdigest.journalApp.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Slf4j
@Component
public class JwtConfiguration {

        @Autowired
        JwtFilter jwtFilter;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
            return http.csrf(csrf -> csrf.disable())
                    .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                    .authorizeHttpRequests(
                            auth -> auth
                                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                    .requestMatchers("/app/**","/signup/**").permitAll()
                                    .anyRequest()
                                    .authenticated()
                    ).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .addFilterBefore(jwtFilter , UsernamePasswordAuthenticationFilter.class)
                    .build();
        }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(
                "http://localhost:5500",
                "http://127.0.0.1:5500"
        ));
        config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }


}
