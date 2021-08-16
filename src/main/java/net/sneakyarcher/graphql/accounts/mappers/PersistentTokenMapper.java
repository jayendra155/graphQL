package net.sneakyarcher.graphql.accounts.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import net.sneakyarcher.graphql.accounts.model.domain.PersistentLogin;

/**
 * @author jayendravikramsingh
 *         <p>
 *         <p>
 *         19/07/20
 */
@Mapper
public interface PersistentTokenMapper {
    
    PersistentTokenMapper INSTANCE = Mappers.getMapper(PersistentTokenMapper.class);
    
    @Mapping(source = "date", target = "lastUsed")
    PersistentLogin fromToken(PersistentRememberMeToken token);
    
//    @Mapping(source = "lastUsed", target = "date")
//    PersistentRememberMeToken fromNodeEntity(PersistentLogin loginToken);
    
}
