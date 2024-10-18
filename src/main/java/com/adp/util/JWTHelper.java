package com.adp.util;

import java.security.interfaces.RSAPublicKey;

import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

public class JWTHelper {
  public static JwtDecoder jwtDecoder(RSAPublicKey publicKey) {
    return NimbusJwtDecoder.withPublicKey(publicKey).build();
  }
}
