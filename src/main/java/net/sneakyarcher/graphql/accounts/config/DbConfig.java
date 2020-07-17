package net.sneakyarcher.graphql.accounts.config;

import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import lombok.extern.slf4j.Slf4j;
import net.sneakyarcher.graphql.accounts.model.domain.Role;
import net.sneakyarcher.graphql.accounts.model.domain.User;
import net.sneakyarcher.graphql.accounts.model.enums.AccountStatus;
import net.sneakyarcher.graphql.accounts.repository.RoleRepository;
import net.sneakyarcher.graphql.accounts.repository.UserRepository;

/**
 * @author jayendravikramsingh
 *         <p>
 *         <p>
 *         15/07/20
 */
@Slf4j
@Configuration
@Profile({ "local" })
@AutoConfigureAfter(value = { Neo4JOperationsConfigurer.class })
public class DbConfig {
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @PostConstruct
    public void prefillData() {
        if (roleRepository.findByAuthority("ROLE_ADMIN").isEmpty()) {
            Role admin = new Role();
            Role appManager = new Role();
            Role user = new Role();
            admin.setAuthority("ROLE_ADMIN");
            user.setAuthority("ROLE_USER");
            appManager.setAuthority("ROLE_APP_MANAGER");
            roleRepository.saveAll(List.of(admin, user, appManager));
        }
        
        if (userRepository.findAll(PageRequest.of(0, 3)).getTotalElements() < 3) {
            User regularUser = this.getUser("regularuser", "password", Set.of("ROLE_USER"));
            User adminUser = this.getUser("adminuser", "password", Set.of("ROLE_USER", "ROLE_ADMIN"));
            User superUser = this.getUser("superuser", "password", Set.of("ROLE_USER", "ROLE_ADMIN", "ROLE_APP_MANAGER"));
            userRepository.saveAll(List.of(regularUser, adminUser, superUser));
        }
    }
    
    private User getUser(String username, String password, Set<String> roles) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.addAuthority(roleRepository.findByAuthorityIn(roles));
        return user;
    }
}
