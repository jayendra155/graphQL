package net.sneakyarcher.graphql.accounts.util;

import static net.sneakyarcher.graphql.accounts.constants.SecurityConstants.TOKEN_PREFIX;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.Authentication;
import lombok.extern.slf4j.Slf4j;
import net.sneakyarcher.graphql.accounts.model.domain.Role;
import net.sneakyarcher.graphql.accounts.model.domain.User;

/**
 * @author jayendravikramsingh
 *         <p>
 *         <p>
 *         19/07/20
 */
@Slf4j
public class JWTUtils {
    
    public static String createJWTToken(Authentication auth, String secret) {
        String[] roles = ((User) auth.getPrincipal()).getAuthorities().stream().map(Role::getAuthority)
                                                     .toArray(String[]::new);
        return JWT.create().withSubject(((User) auth.getPrincipal()).getUsername()).withArrayClaim("roles", roles)
                  .withExpiresAt(new Date(System.currentTimeMillis() + 3 * 24 * 60 * 60 * 1000))
                  .sign(Algorithm.HMAC512(secret.getBytes()));
    }
    
    public static DecodedJWT decodeJWT(String token, String secret) {
        return JWT.require(Algorithm.HMAC512(secret.getBytes())).build().verify(token.replace(TOKEN_PREFIX, ""));
    }
    
}
