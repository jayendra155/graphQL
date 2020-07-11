package net.sneakyarcher.graphql.accounts.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import net.sneakyarcher.graphql.accounts.model.User;
import net.sneakyarcher.graphql.accounts.model.enums.AccountStatus;

/**
 * @author jayendravikramsingh
 *         <p>
 *         <p>
 *         11/07/20
 */
@Slf4j
public class UserRepositoryTest extends BaseRepositoryTest {
    
    @Autowired
    private UserRepository userRepository;
    
    @Test
    public void basicRepoTest() {
        assertThat(userRepository, is(notNullValue()));
    }
    
    @Test
    public void saveUser() {
        
        User user = new User();
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setPassword("dkjcnsdkjcns");
        user.setUsername("hello");
        log.info(user.toString());
        User save = this.userRepository.save(user);
        Assertions.assertNotNull(save.getId(), "Id is not null after saving");
        Assertions.assertNotNull(save.getCreatedAt(), "created at timestamp is not null after saving");
        Assertions.assertNotNull(save.getUpdatedAt(), "last modified timestamp is not null after saving");
    }
    
}
