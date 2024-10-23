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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static com.adp.util.JWTHelper.jwtDecoder;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

  @Value("${rsa.public-key}")
  private RSAPublicKey publicKey;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    return http
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
          .requestMatchers("/job/page").permitAll()
          .requestMatchers("/job/{id}").permitAll()
          .requestMatchers("/job").permitAll()
          .requestMatchers("/").permitAll()
            .anyRequest().authenticated())
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt
            .decoder(JWTHelper.jwtDecoder(publicKey)) // Use NimbusJwtDecoder with public key
            .jwtAuthenticationConverter(JWTHelper.jwtAuthenticationConverter()))) // Extract roles

        .build();
  }
  // we can use the userId from the jwt like this by calling it in a REST method
  // in the controller
  // Authentication authentication =
  // SecurityContextHolder.getContext().getAuthentication();
  //
  // // Extract the userId from the principal
  // String userId = (String) authentication.getPrincipal();
  @Bean
  public UrlBasedCorsConfigurationSource corsConfigurationSource() {
      CorsConfiguration configuration = new CorsConfiguration();
      configuration.addAllowedOrigin("*"); // Allow all origins
      configuration.addAllowedMethod("*"); // Allow all methods (GET, POST, etc.)
      configuration.addAllowedHeader("*"); // Allow all headers
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", configuration);
      return source;
  }

}
