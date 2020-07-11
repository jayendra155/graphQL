package net.sneakyarcher.graphql.accounts.model;

import java.util.UUID;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author jayendravikramsingh
 *         <p>
 *         <p>
 *         10/07/20
 */
@Document
public class SigninHistory {
    
    private ObjectId id;
    
    private UUID userId;
    
    
}
