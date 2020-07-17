package net.sneakyarcher.graphql.accounts.model.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.security.core.GrantedAuthority;
import lombok.Data;

/**
 * @author jayendravikramsingh
 *         <p>
 *         <p>
 *         08/07/20
 */
@Data
@NodeEntity
public class Role implements GrantedAuthority {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @Index(unique = true)
    private String authority;
    
    private String desc;
    
}
