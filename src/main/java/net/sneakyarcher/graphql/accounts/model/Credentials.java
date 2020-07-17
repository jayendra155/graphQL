package net.sneakyarcher.graphql.accounts.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jayendravikramsingh
 *         <p>
 *         <p>
 *         17/07/20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Credentials {
    
    private String username;
    
    private String password;
}
