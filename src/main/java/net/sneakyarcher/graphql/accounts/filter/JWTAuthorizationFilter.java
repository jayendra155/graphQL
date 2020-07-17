package net.sneakyarcher.graphql.accounts.filter;

import static net.sneakyarcher.graphql.accounts.constants.SecurityConstants.JWT_AUTH_HEADER_KEY;
import static net.sneakyarcher.graphql.accounts.constants.SecurityConstants.SECRET;
import static net.sneakyarcher.graphql.accounts.constants.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jayendravikramsingh
 *         <p>
 *         <p>
 *         17/07/20
 */
@Slf4j
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    
    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
    throws IOException, ServletException {
        String header = req.getHeader(JWT_AUTH_HEADER_KEY);
        
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }
        
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }
    
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(JWT_AUTH_HEADER_KEY);
        if (token != null) {
            // parse the token.
            DecodedJWT verifiedDecodedJWT = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
                                   .verify(token.replace(TOKEN_PREFIX, ""));
            String user = verifiedDecodedJWT.getSubject();
            String[] roles = verifiedDecodedJWT.getClaim("roles").asArray(String.class);
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, Arrays.stream(roles).map(
                        SimpleGrantedAuthority::new).collect(Collectors.toList()));
            }
            return null;
        }
        return null;
    }
}
