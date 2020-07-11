package net.sneakyarcher.graphql.accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.neo4j.annotation.EnableNeo4jAuditing;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableNeo4jAuditing
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
