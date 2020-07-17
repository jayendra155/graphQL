package net.sneakyarcher.graphql.accounts;

import java.util.Arrays;
import java.util.Base64;

import org.junit.jupiter.api.Test;

/**
 * @author jayendravikramsingh
 *         <p>
 *         <p>
 *         08/07/20
 */
public class SampleTest {
    
    @Test
    public void byteArrayToStringAndBack() {
        String sample = "Hello";
        byte[] array = sample.getBytes();
        String byteArrayString = Arrays.toString(array);
        byteArrayString.replace("[","");
        
    }
}
