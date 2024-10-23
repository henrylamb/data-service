package com.adp.filter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.adp.util.JWTHelper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthorizationFilter extends AbstractPreAuthenticatedProcessingFilter {

    private final JwtDecoder jwtDecoder;

    public JwtAuthorizationFilter(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        return null;
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            Map<String, Object> claims = JWTHelper.extractClaims(token, jwtDecoder);
            return createAuthentication(claims);
        }
        return null;
    }

    private Authentication createAuthentication(Map<String, Object> claims) {
        // Extract necessary information from claims
        String username = (String) claims.get("sub"); // Assuming "sub" claim contains the username
        String role = (String) claims.get("role"); // Assuming "role" claim contains the role

        // Create authorities based on the role
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(role);

        // Create and return the Authentication object
        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }

    // @Override
    // protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
    //     SecurityContextHolder.getContext().setAuthentication(authResult);
    //     chain.doFilter(request, response);
    // }
}