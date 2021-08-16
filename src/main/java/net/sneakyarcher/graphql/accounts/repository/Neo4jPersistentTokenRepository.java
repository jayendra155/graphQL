package net.sneakyarcher.graphql.accounts.repository;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import net.sneakyarcher.graphql.accounts.exceptions.NodeEntityNotFoundException;
import net.sneakyarcher.graphql.accounts.mappers.PersistentTokenMapper;
import net.sneakyarcher.graphql.accounts.model.domain.PersistentLogin;
import net.sneakyarcher.graphql.accounts.model.domain.User;
import net.sneakyarcher.graphql.accounts.service.UserService;

/**
 * @author jayendravikramsingh
 *         <p>
 *         <p>
 *         19/07/20
 */
@Slf4j
@Component
public class Neo4jPersistentTokenRepository implements PersistentTokenRepository {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private PersistenLoginRepository repository;
    
    private PersistentTokenMapper mapper = PersistentTokenMapper.INSTANCE;
    
    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        User user = this.userService.getUserByUsername(token.getUsername());
        log.debug("Saving persistent token. {series:{}, token:{}, lastUsed:{}}", token.getSeries());
        PersistentLogin persistentLogin = mapper.fromToken(token);
        persistentLogin.setUser(user);
        this.repository.save(persistentLogin);
    }
    
    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        log.debug("Updating token for series: {}", series);
        PersistentLogin login = this.repository.findBySeries(series).orElseThrow(
                () -> new NodeEntityNotFoundException(PersistentLogin.class, Map.of("series", series)));
        login.setToken(tokenValue);
        login.setLastUsed(lastUsed);
        this.repository.save(login);
    }
    
    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        PersistentLogin persistedToken = this.repository.findBySeries(seriesId).orElseThrow(
                () -> new NodeEntityNotFoundException(PersistentLogin.class, Map.of("series", seriesId)));
        return new PersistentRememberMeToken(persistedToken.getUser().getUsername(),persistedToken.getSeries(),
                                             persistedToken.getToken(), persistedToken.getLastUsed());
    }
    
    @Override
    public void removeUserTokens(String username) {
        this.repository.deleteAllByUserUsername(username);
    }
}
