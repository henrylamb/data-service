import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

public class CookieDecoder {

    private final Algorithm algorithm;

    public CookieDecoder(RSAPublicKey publicKey) {
        this.algorithm = Algorithm.RSA256(publicKey, null); // Use the public key for verification
    }

    public Map<String, String> decodeCookie(HttpServletRequest request) throws JWTVerificationException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user")) {
                    String token = cookie.getValue();
                    JWTVerifier verifier = JWT.require(algorithm).build(); // This will throw an exception if the token is invalid
                    DecodedJWT decodedJWT = verifier.verify(token);

                    // Extract claims from the token
                    Map<String, String> claims = new HashMap<>();
                    claims.put("role", decodedJWT.getClaim("role").asString());
                    claims.put("userId", decodedJWT.getClaim("userId").asString());

                    return claims;
                }
            }
        }
        return null;
    }
}