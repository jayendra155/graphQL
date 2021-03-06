package net.sneakyarcher.graphql.accounts.controller;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Base64;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import net.sneakyarcher.graphql.accounts.model.Credentials;

/**
 * @author jayendravikramsingh
 *         <p>
 *         <p>
 *         08/07/20
 */
@Slf4j
@RestController
@RequestMapping("/signin")
public class SigninController {
    
    @PostMapping
    public ResponseEntity signinCall(@RequestBody Credentials credentials) {
        return null;
    }

}
