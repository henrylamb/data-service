package com.adp.config;

import java.security.interfaces.RSAPublicKey;

import org.hibernate.annotations.Filter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.adp.util.JWTHelper;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Value("${rsa.public-key}")
  RSAPublicKey publicKey;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.GET, "/", "/job/page", "/job/**").permitAll()
            .anyRequest().authenticated())

        .oauth2ResourceServer((oauth2ResourceServer) ->
            oauth2ResourceServer.jwt((customizer) ->
                customizer.decoder(JWTHelper.jwtDecoder(publicKey))))
        .build();
  }

}
