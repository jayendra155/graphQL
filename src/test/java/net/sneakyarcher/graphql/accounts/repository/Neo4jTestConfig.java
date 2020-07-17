package net.sneakyarcher.graphql.accounts.repository;

import java.io.File;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.ogm.drivers.embedded.driver.EmbeddedDriver;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.annotation.EnableNeo4jAuditing;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import net.sneakyarcher.graphql.accounts.config.Neo4JOperationsConfigurer;
import net.sneakyarcher.graphql.accounts.model.domain.User;

/**
 * @author jayendravikramsingh
 *         <p>
 *         <p>
 *         11/07/20
 */
@Configuration
@ComponentScan(basePackageClasses = { UserRepository.class, User.class, Neo4JOperationsConfigurer.class })
@EnableNeo4jRepositories(basePackageClasses = { UserRepository.class })
@EnableTransactionManagement
@EnableNeo4jAuditing
@AutoConfigurationPackage
public class Neo4jTestConfig {
    
    @Bean
    public SessionFactory sessionFactory() {
        EmbeddedDriver driver = new EmbeddedDriver(graphDatabaseService(),
                                                   new org.neo4j.ogm.config.Configuration.Builder().database("accounts")
                                                                                                   .uri("bolt://localhost:3001")
                                                                                                   .build());
        return new SessionFactory(driver, User.class.getPackageName());
    }
    
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new Neo4jTransactionManager(sessionFactory());
    }
    
    @Bean
    public GraphDatabaseService graphDatabaseService() {
        return new GraphDatabaseFactory().newEmbeddedDatabaseBuilder(new File("accounts.db")).newGraphDatabase();
        
    }
    
}

