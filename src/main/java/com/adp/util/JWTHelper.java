package com.adp.util;

import java.security.interfaces.RSAPublicKey;
import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

public class JWTHelper {

    // This will check that the JWT was signed by the private key (using the public key)
  public static JwtDecoder jwtDecoder(RSAPublicKey publicKey) {
    return NimbusJwtDecoder.withPublicKey(publicKey).build();
  }

   // We extract the claims from the header
  public static Map<String, Object> extractClaims(String token, JwtDecoder jwtDecoder) {
    Jwt jwt = jwtDecoder.decode(token);
    return jwt.getClaims();
  }

  public static JwtAuthenticationConverter jwtAuthenticationConverter() {
    
      JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
      converter.setJwtGrantedAuthoritiesConverter((jwt) -> {
          // Extract role claim from JWT and map to Spring Security authority
          String role = jwt.getClaimAsString("role");
          System.out.println("In Authenticator: " + role);

          return Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+role.toUpperCase()));
      });
      converter.setPrincipalClaimName("userId");
      System.out.println("In Authenticator v2: " + converter);
      return converter;
  }

}
