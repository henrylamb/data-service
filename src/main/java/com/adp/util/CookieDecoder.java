package com.adp.util;

import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.nimbusds.jose.Algorithm;
import com.auth0.jwt.JWT;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import jakarta.servlet.http.Cookie;

import jakarta.servlet.http.HttpServletRequest;

public class CookieDecoder {

    private final Algorithm algorithm;

    public CookieDecoder(String secret) {
        this.algorithm = Algorithm.RSA256;
    }

    public Map<String, String> decodeCookie(HttpServletRequest request) throws JWTVerificationException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("jwt")) {
                    String token = cookie.getValue();
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);

                    // Extract claims from the token
                    Map<String, String> claims = new HashMap<>();
                    claims.put("email", decodedJWT.getClaim("email").asString());
                    claims.put("role", decodedJWT.getClaim("role").asString());
                    claims.put("department", decodedJWT.getClaim("department").asString());

                    return claims;
                }
            }
        }
        throw new JWTVerificationException("No valid JWT token found");
    }
}
