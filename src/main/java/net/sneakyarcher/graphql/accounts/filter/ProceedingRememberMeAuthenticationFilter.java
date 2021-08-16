package net.sneakyarcher.graphql.accounts.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jayendravikramsingh
 *         <p>
 *         <p>
 *         19/07/20
 */
@Slf4j
public class ProceedingRememberMeAuthenticationFilter extends RememberMeAuthenticationFilter {
    
    private AuthenticationSuccessHandler successHandler;
    
    public ProceedingRememberMeAuthenticationFilter(AuthenticationManager authenticationManager,
            RememberMeServices rememberMeServices) {
        super(authenticationManager, rememberMeServices);
    }
    
    @Override
    public void setAuthenticationSuccessHandler(AuthenticationSuccessHandler successHandler) {
        this.successHandler = successHandler;
    }
    
    @Override
    protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            Authentication authResult) {
        
        if (successHandler == null) {
            log.warn("Success handler is null");
            return;
        }
        
        try {
            successHandler.onAuthenticationSuccess(request, response, authResult);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
