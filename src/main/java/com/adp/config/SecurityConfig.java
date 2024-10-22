package com.adp.config;

import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


import com.adp.util.JWTHelper;

import static com.adp.util.JWTHelper.jwtDecoder;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  @Value("${rsa.public-key}")
  private RSAPublicKey publicKey;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    return http
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
//            .requestMatchers(HttpMethod.GET, "/", "/job/page", "/job/**").permitAll()
//            .requestMatchers(HttpMethod.POST, "/admin/**").hasRole("ADMIN")
//            .requestMatchers(HttpMethod.PUT, "/admin/**").hasRole("ADMIN")
//            .requestMatchers(HttpMethod.DELETE, "/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated())
        //.addFilterBefore(new JwtAuthorizationFilter(jwtDecoder), AbstractPreAuthenticatedProcessingFilter.class) // We get the claims from the token
            .oauth2ResourceServer(oauth2 -> oauth2
                    .jwt(jwt -> jwt
                            .decoder(JWTHelper.jwtDecoder(publicKey)) // Use NimbusJwtDecoder with public key
                            .jwtAuthenticationConverter(JWTHelper.jwtAuthenticationConverter()))) // Extract roles

            .build();
  }
  //we can use the userId from the jwt like this by calling it in a REST method in the controller
  //  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
  //
  //        // Extract the userId from the principal
  //        String userId = (String) authentication.getPrincipal();

}
