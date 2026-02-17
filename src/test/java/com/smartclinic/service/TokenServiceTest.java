package com.smartclinic.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TokenServiceTest {

    @Test
    void shouldGenerateAndParseToken() {
        TokenService tokenService = new TokenService("supersecuresecretkeysupersecuresecretkey123");
        String token = tokenService.generateToken("admin");
        assertEquals("admin", tokenService.extractSubject(token));
    }
}
