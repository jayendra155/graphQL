package net.sneakyarcher.graphql.accounts.service;

import static net.sneakyarcher.graphql.accounts.constants.SecurityConstants.REMEMBER_ME_SECRET;

import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import lombok.extern.slf4j.Slf4j;
import net.sneakyarcher.graphql.accounts.util.JWTUtils;

/**
 * @author jayendravikramsingh
 *         <p>
 *         <p>
 *         19/07/20
 */
@Slf4j
public class PersistentJwtTokenBasedRememberMeServices extends PersistentTokenBasedRememberMeServices {
    
    private Random random;
    
    public PersistentJwtTokenBasedRememberMeServices(String key, UserDetailsService userDetailsService,
            PersistentTokenRepository tokenRepository) {
        super(key, userDetailsService, tokenRepository);
        random = new Random();
    }
    
    @Override
    protected String[] decodeCookie(String cookieValue) throws InvalidCookieException {
        try {
            DecodedJWT jwt = JWTUtils.decodeJWT(cookieValue, REMEMBER_ME_SECRET);
            
            return new String[] { jwt.getId(), jwt.getSubject() };
        } catch (JWTDecodeException e) {
            log.warn(e.getMessage());
            throw new InvalidCookieException(e.getMessage());
        }
    }
    
    @Override
    protected String encodeCookie(String[] cookieTokens) {
        log.info("Encoding cookie");
        return JWT.create().withClaim("id", cookieTokens[0]).withSubject(cookieTokens[1])
        
                  .withExpiresAt(new Date(System.currentTimeMillis() + getTokenValiditySeconds() * 1000L))
                  .withIssuedAt(new Date()).sign(Algorithm.HMAC512(REMEMBER_ME_SECRET.getBytes()));
    }
    
    @Override
    protected String generateSeriesData() {
        return UUID.randomUUID().toString();
    }
    
    @Override
    protected String generateTokenData() {
        byte[] newToken = new byte[16];
        random.nextBytes(newToken);
        return new String(Base64.getEncoder().encode(newToken));
    }
    
    @Override
    protected boolean rememberMeRequested(HttpServletRequest request, String parameter) {
        return Optional.ofNullable((Boolean) request.getAttribute("remember-me")).orElse(false);
    }
}
