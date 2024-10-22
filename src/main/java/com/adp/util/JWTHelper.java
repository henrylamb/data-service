package com.adp.util;

import java.security.interfaces.RSAPublicKey;
import java.util.Map;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

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
}
