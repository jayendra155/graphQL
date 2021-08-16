package net.sneakyarcher.graphql.accounts.repository;

import java.util.Optional;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import net.sneakyarcher.graphql.accounts.model.domain.PersistentLogin;

/**
 * @author jayendravikramsingh
 *         <p>
 *         <p>
 *         19/07/20
 */
public interface PersistenLoginRepository extends Neo4jRepository<PersistentLogin, Long> {
    
    Optional<PersistentLogin> findByUserUsername(String username);
    
    void deleteAllByUserUsername(String username);
    
    Optional<PersistentLogin> findBySeries(String series);
}
