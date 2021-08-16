package net.sneakyarcher.graphql.accounts.model.domain;

import java.util.Date;
import java.util.Optional;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Required;
import org.neo4j.ogm.annotation.typeconversion.DateString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import lombok.Data;

/**
 * @author jayendravikramsingh
 *         <p>
 *         <p>
 *         19/07/20
 */
@Data
@NodeEntity
public class PersistentLogin {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @Index(unique = true)
    private String series;
    
    @Required
    private String token;
    
    @Required
    @DateString
    private Date lastUsed;
    
    @CreatedDate
    @DateString
    private Date createdAt;
    
    @LastModifiedDate
    @DateString
    private Date updatedAt;
    
    @Relationship(direction = "INCOMING")
    private User user;
    
    public Date getUpdatedAt() {
        return Optional.ofNullable(updatedAt).flatMap(c -> Optional.ofNullable(new Date(c.getTime()))).orElse(null);
    }
    
    public Date getCreatedAt() {
        return Optional.ofNullable(createdAt).flatMap(c -> Optional.ofNullable(new Date(c.getTime()))).orElse(null);
    }
}
