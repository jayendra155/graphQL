package net.sneakyarcher.graphql.accounts.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.ExposureConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import net.sneakyarcher.graphql.accounts.model.domain.Role;
import net.sneakyarcher.graphql.accounts.model.domain.User;

/**
 * @author jayendravikramsingh
 *         <p>
 *         <p>
 *         10/07/20
 */
@Configuration
public class RepositoryConfig implements RepositoryRestConfigurer {
    
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(User.class, Role.class);
        config.setBasePath("/api");
        config.setMaxPageSize(50);
        ExposureConfiguration exposureConfiguration = config.getExposureConfiguration();
        exposureConfiguration.withItemExposure(
                (metadata, httpMethods) -> httpMethods.disable(HttpMethod.PATCH, HttpMethod.PUT, HttpMethod.POST,
                                                               HttpMethod.DELETE));
    }
}
