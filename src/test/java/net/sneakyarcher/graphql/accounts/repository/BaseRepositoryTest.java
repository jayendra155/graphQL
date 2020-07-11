package net.sneakyarcher.graphql.accounts.repository;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jayendravikramsingh
 *         <p>
 *         <p>
 *         11/07/20
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@DataNeo4jTest
@ContextConfiguration(classes = { Neo4jTestConfig.class })
public class BaseRepositoryTest {
    
}
