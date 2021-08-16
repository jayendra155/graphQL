package net.sneakyarcher.graphql.accounts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import net.sneakyarcher.graphql.accounts.model.domain.User;
import net.sneakyarcher.graphql.accounts.repository.UserRepository;

/**
 * @author jayendravikramsingh
 *         <p>
 *         <p>
 *         10/07/20
 */
@Slf4j
@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public User getUserByUsername(String username) {
        log.info("Searching for user with username: {}", username);
        return this.userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format("%s username not found.", username)));
    }
}
