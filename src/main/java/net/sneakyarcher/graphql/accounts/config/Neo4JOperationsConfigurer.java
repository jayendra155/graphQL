package net.sneakyarcher.graphql.accounts.config;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import org.neo4j.ogm.session.event.Event;
import org.neo4j.ogm.session.event.EventListener;
import org.neo4j.ogm.session.event.PreSaveEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.extern.slf4j.Slf4j;
import net.sneakyarcher.graphql.accounts.model.domain.User;

/**
 * @author jayendravikramsingh
 *         <p>
 *         <p>
 *         11/07/20
 */
@Slf4j
@Configuration
//@AutoConfigureAfter(value = { SessionFactory.class })
public class Neo4JOperationsConfigurer {
    
    @Bean
    public EventListener customEventListener() {
        
        EventListener eventListener = new EventListener() {
            
            @Override
            public void onPreSave(Event event) {
                PreSaveEvent ev = (PreSaveEvent) event;
                log.debug("Custom event configurer before save invoked");
                if (ev.getObject() instanceof User && ev.isNew()) {
                    User user = (User) event.getObject();
                    LocalDateTime expiryTime = ZonedDateTime.now(ZoneId.of("UTC")).plus(3, ChronoUnit.DAYS)
                                                            .toLocalDateTime();
                    log.info("Setting expiry time for user to {}", expiryTime.toString());
                    user.setExpiryTime(expiryTime);
                }
            }
            
            @Override
            public void onPostSave(Event event) {
            
            }
            
            @Override
            public void onPreDelete(Event event) {
            
            }
            
            @Override
            public void onPostDelete(Event event) {
            
            }
        };
        //        session.register(eventListener);
        return eventListener;
    }
    
}
