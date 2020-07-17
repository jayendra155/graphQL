package net.sneakyarcher.graphql.accounts.model.domain;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Version;
import org.neo4j.ogm.annotation.typeconversion.DateString;
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
    
    private LocalDateTime expiryTime;
    
    @Relationship(type = "hasAuthority", direction = Relationship.INCOMING)
    private Set<Role> authorities;
    
    @Version
    private Long version;
    
    @CreatedDate
    @DateString
    private Date createdAt;
    
    @LastModifiedDate
    @DateString
    private Date updatedAt;
    
    public Date getUpdatedAt() {
        return Optional.ofNullable(updatedAt).flatMap(c -> Optional.ofNullable(new Date(c.getTime()))).orElse(null);
    }
    
    public Date getCreatedAt() {
        return Optional.ofNullable(createdAt).flatMap(c -> Optional.ofNullable(new Date(c.getTime()))).orElse(null);
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return !AccountStatus.LOCKED.equals(accountStatus);
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return AccountStatus.ACTIVE.equals(accountStatus);
    }
    
    public Set<Role> addAuthority(Role role) {
        authorities = Optional.ofNullable(authorities).orElse(new HashSet<>());
        authorities.add(role);
        return Collections.unmodifiableSet(authorities);
    }
    
    public Set<Role> addAuthority(Collection<Role> roles) {
        authorities = Optional.ofNullable(authorities).orElse(new HashSet<>());
        authorities.addAll(roles);
        return Collections.unmodifiableSet(authorities);
    }
    
    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username='" + username + '\'' + ", password='" + password + '\''
                + ", accountStatus=" + accountStatus + ", expiryTime=" + expiryTime + ", authorities=" + authorities
                + ", version=" + version + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }
}
