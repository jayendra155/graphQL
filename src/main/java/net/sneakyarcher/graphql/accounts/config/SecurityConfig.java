package net.sneakyarcher.graphql.accounts.config;

import static net.sneakyarcher.graphql.accounts.constants.SecurityConstants.REMEMBER_ME_SECRET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import lombok.extern.slf4j.Slf4j;
import net.sneakyarcher.graphql.accounts.filter.JWTAuthenticationFilter;
import net.sneakyarcher.graphql.accounts.filter.JWTAuthorizationFilter;
import net.sneakyarcher.graphql.accounts.filter.ProceedingRememberMeAuthenticationFilter;
import net.sneakyarcher.graphql.accounts.repository.Neo4jPersistentTokenRepository;
import net.sneakyarcher.graphql.accounts.service.GraphDbUserDetailsService;
import net.sneakyarcher.graphql.accounts.service.PersistentJwtTokenBasedRememberMeServices;

/**
 * @author jayendravikramsingh
 *         <p>
 *         <p>
 *         10/07/20
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    public BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private Neo4jPersistentTokenRepository neo4jPersistentTokenRepository;
    
    @Autowired
    private GraphDbUserDetailsService graphDbUserDetailsService;
    
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        //@formatter:off
        http.csrf().disable().sessionManagement()
                             .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.GET, "/actuator/**", "/api/**", "/dummy/**").permitAll()
            .antMatchers(HttpMethod.POST, "/actuator/**").hasRole("APP_MANAGER")
            .anyRequest().authenticated()
            .and()
            .addFilter(new ProceedingRememberMeAuthenticationFilter(authenticationManager(),
                                                                    new PersistentJwtTokenBasedRememberMeServices(
                                                                            REMEMBER_ME_SECRET,
                                                                            graphDbUserDetailsService,
                                                                            neo4jPersistentTokenRepository)))
            .addFilter(new JWTAuthenticationFilter(authenticationManager(), getApplicationContext()))
//            .rememberMe(httpSecurityRememberMeConfigurer -> {
//                httpSecurityRememberMeConfigurer.tokenValiditySeconds(180 * 24 * 60 * 60);
//                httpSecurityRememberMeConfigurer.alwaysRemember(true);
//                httpSecurityRememberMeConfigurer.rememberMeCookieName("remember-me");
//                httpSecurityRememberMeConfigurer.tokenRepository(neo4jPersistentTokenRepository);
//                httpSecurityRememberMeConfigurer.key(REMEMBER_ME_SECRET);
//                httpSecurityRememberMeConfigurer.rememberMeCookieDomain("sample.me");
//                httpSecurityRememberMeConfigurer.userDetailsService(graphDbUserDetailsService);
//                httpSecurityRememberMeConfigurer.rememberMeParameter("remember-me");
//                httpSecurityRememberMeConfigurer.useSecureCookie(false);
//            })
            .addFilter(new JWTAuthorizationFilter(authenticationManager()))
            ;
        //@formatter:on
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(graphDbUserDetailsService).passwordEncoder(passwordEncoder);
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
    
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }
}
