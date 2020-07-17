package net.sneakyarcher.graphql.accounts.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import net.sneakyarcher.graphql.accounts.model.domain.Role;

/**
 * @author jayendravikramsingh
 *         <p>
 *         <p>
 *         10/07/20
 */
public interface RoleRepository extends Neo4jRepository<Role, Long> {

    Optional<Role> findByAuthority(String authority);
    
    Set<Role> findByAuthorityIn(Collection<String> authority);
}
