package net.sneakyarcher.graphql.accounts;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

/**
 * @author jayendravikramsingh
 *         <p>
 *         <p>
 *         25/07/20
 */
@Component
@Endpoint(id = "dummy")
public class DummyEndpoint {
    
    private String string = "new value";
    
    @ReadOperation
    public String returnValue() {
        return string;
    }
    
    @WriteOperation
    public String update(@Selector String newString) {
        string = newString;
        return string;
    }
}
