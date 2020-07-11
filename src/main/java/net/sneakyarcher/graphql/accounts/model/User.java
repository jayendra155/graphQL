package net.sneakyarcher.graphql.accounts.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Version;
import org.neo4j.ogm.annotation.typeconversion.EnumString;
import org.neo4j.ogm.id.InternalIdStrategy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.Data;
import net.sneakyarcher.graphql.accounts.model.enums.AccountStatus;

/**
 * @author jayendravikramsingh
 *         <p>
 *         <p>
 *         07/07/20
 */
@Data
@NodeEntity
public class User implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = InternalIdStrategy.class)
    private Long id;
    
    @Index(unique = true)
    private String username;
    
    @JsonIgnore
    private String password;
    
    @EnumString(value = AccountStatus.class)
    private AccountStatus accountStatus;
    
    @Relationship(type = "hasRole", direction = Relationship.INCOMING)
    private Set<Role> authorities;
    
    @Version
    private Long version;
    
    @CreatedDate
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    private LocalDateTime updatedAt;
    
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }
    
    @Override
    public boolean isEnabled() {
        return AccountStatus.ACTIVE.equals(accountStatus);
    }
    
    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username='" + username + '\'' + ", password='" + password + '\''
                + ", accountStatus=" + accountStatus + ", authorities=" + authorities + ", version=" + version
                + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }
}
