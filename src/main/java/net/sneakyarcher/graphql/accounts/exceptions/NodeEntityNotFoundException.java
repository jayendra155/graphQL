package net.sneakyarcher.graphql.accounts.exceptions;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author jayendravikramsingh
 *         <p>
 *         <p>
 *         19/07/20
 */
public class NodeEntityNotFoundException extends RuntimeException {
    
    public NodeEntityNotFoundException(Class clazz, Map<String, String> criteria) {
        //@formatter:off
        super(clazz.getSimpleName() + " was not found found for: " + criteria.entrySet().stream()
                                                                .map(pair -> pair.getKey() + "->" + pair.getValue())
                                                                .collect(Collectors.joining(",")));
        //@formatter:on
    }
}
