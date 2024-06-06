package ru.t1.opencschool.authorization.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    private JwtService jwtService;


    @BeforeEach
    void setUp() {
        String jwtSigningKey = "53A73E5F1C4E0A2D3B5F2D784E6A1B423D6F247D1F6E5C3A596D635A75327855";
        jwtService = new JwtService();
        Key key = Keys.hmacShaKeyFor(jwtSigningKey.getBytes());
        String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());
        jwtService.setJwtSigningKey(base64Key); // Устанавливаем настоящий ключ для подписи
    }

    @Test
    void extractUserName_shouldExtractCorrectUserName() {
        // Arrange
        String token = Jwts.builder()
                .setSubject("testUser")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 100000 * 60 * 24))
                .signWith(Keys.hmacShaKeyFor(jwtService.getSigningKey().getEncoded()), SignatureAlgorithm.HS256)
                .compact();

        // Act
        String userName = jwtService.extractUserName(token);

        // Assert
        assertEquals("testUser", userName);
    }

    @Test
    void generateToken_shouldReturnValidToken() {
        // Arrange
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testUser");

        // Act
        String token = jwtService.generateToken(userDetails);

        // Assert
        assertNotNull(token);
        // Проверяем, что токен может быть расшифрован и что он не просрочен
        Jwts.parser()
                .setSigningKey(jwtService.getSigningKey())
                .build()
                .parseClaimsJws(token);
    }

    @Test
    void isTokenValid_shouldReturnTrueForValidToken() {
        // Arrange
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testUser");

        String token = jwtService.generateToken(userDetails);

        // Act
        boolean isValid = jwtService.isTokenValid(token, userDetails);

        // Assert
        assertTrue(isValid);
    }

    @Test
    void isTokenValid_shouldReturnFalseForInvalidToken() {
        // Arrange
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testUser");

        String token = jwtService.generateToken(userDetails);
        UserDetails invalidUserDetails = mock(UserDetails.class);
        when(invalidUserDetails.getUsername()).thenReturn("invalidUser");

        // Act
        boolean isValid = jwtService.isTokenValid(token, invalidUserDetails);

        // Assert
        assertFalse(isValid);
    }


    @Test
    void extractAllClaims_shouldExtractAllClaims() {

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testUser");

        // Arrange
        String token = jwtService.generateToken(userDetails);

        // Act
        Claims claims = jwtService.extractAllClaims(token);

        // Assert
        assertNotNull(claims);
        assertEquals("testUser", claims.getSubject());
    }
}
