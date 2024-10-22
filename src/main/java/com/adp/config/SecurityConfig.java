package com.adp.config;

import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import com.adp.util.JwtAuthorizationFilter;
import com.adp.util.JWTHelper;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Value("${rsa.public-key}")
  RSAPublicKey publicKey;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    JwtDecoder jwtDecoder = JWTHelper.jwtDecoder(publicKey);

    return http
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.GET, "/", "/job/page", "/job/**").permitAll()
            .requestMatchers(HttpMethod.POST, "/admin/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/admin/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated())
        .addFilterBefore(new JwtAuthorizationFilter(jwtDecoder), AbstractPreAuthenticatedProcessingFilter.class) // We get the claims from the token
        .build();
  }

}
