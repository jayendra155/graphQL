package net.sneakyarcher.graphql.accounts.filter;

import static net.sneakyarcher.graphql.accounts.constants.SecurityConstants.JWT_AUTH_HEADER_KEY;
import static net.sneakyarcher.graphql.accounts.constants.SecurityConstants.SECRET;
import static net.sneakyarcher.graphql.accounts.constants.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import net.sneakyarcher.graphql.accounts.model.Credentials;
import net.sneakyarcher.graphql.accounts.model.domain.Role;
import net.sneakyarcher.graphql.accounts.model.domain.User;

/**
 * @author jayendravikramsingh
 *         <p>
 *         <p>
 *         17/07/20
 */
@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    
    private AuthenticationManager authenticationManager;
    
    private ObjectMapper objectMapper;
    
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, ApplicationContext applicationContext) {
        super();
        this.authenticationManager = authenticationManager;
        this.objectMapper = applicationContext.getBean(ObjectMapper.class);
    }
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
    throws AuthenticationException {
        try {
            Credentials creds = this.objectMapper.readValue(req.getInputStream(), Credentials.class);
            
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword(),
                                                            new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
            Authentication auth) throws IOException, ServletException {
        String[] roles = ((User) auth.getPrincipal()).getAuthorities().stream().map(Role::getAuthority)
                                                       .toArray(String[]::new);
        log.debug("Username: {} has roles :{}", ((User) auth.getPrincipal()).getUsername(), Arrays.toString(roles));
        String token = JWT.create().withSubject(((User) auth.getPrincipal()).getUsername())
                          .withArrayClaim("roles", roles)
                          .withExpiresAt(new Date(System.currentTimeMillis() + 3 * 24 * 60 * 60 * 1000))
                          .sign(Algorithm.HMAC512(SECRET.getBytes()));
        res.addHeader(JWT_AUTH_HEADER_KEY, TOKEN_PREFIX + token);
    }
}
