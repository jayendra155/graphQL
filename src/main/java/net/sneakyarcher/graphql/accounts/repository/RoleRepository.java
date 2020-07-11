package net.sneakyarcher.graphql.accounts.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import net.sneakyarcher.graphql.accounts.model.Role;

/**
 * @author jayendravikramsingh
 *         <p>
 *         <p>
 *         10/07/20
 */
public interface RoleRepository extends Neo4jRepository<Role, Long> {

}
