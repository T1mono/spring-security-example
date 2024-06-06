package ru.t1.opencschool.authorization.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.t1.opencschool.authorization.dto.JwtAuthenticationResponseDto;
import ru.t1.opencschool.authorization.dto.UserSignInRequestDto;
import ru.t1.opencschool.authorization.dto.UserSignUpRequestDto;
import ru.t1.opencschool.authorization.exceptions.InternalServerErrorException;
import ru.t1.opencschool.authorization.roles.Role;
import ru.t1.opencschool.authorization.users.UserAccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Тесты на авторизацию пользователя.
 */
@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void signUp_shouldCreateUserAndReturnToken() throws Exception {
        // Arrange
        UserSignUpRequestDto request = new UserSignUpRequestDto("testUser", "test@example.com", "testPassword");
        UserAccount user = UserAccount.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();
        String jwtToken = "testJwtToken";
        when(jwtService.generateToken(any(UserAccount.class))).thenReturn(jwtToken);

        // Act
        JwtAuthenticationResponseDto response = authenticationService.signUp(request);

        // Assert
        verify(userService).create(any(UserAccount.class));
        assertEquals(jwtToken, response.getToken());
    }

    @Test
    void signUp_shouldThrowException_whenUserCreationFails() {
        // Arrange
        UserSignUpRequestDto request = new UserSignUpRequestDto("testUser", "test@example.com", "testPassword");
        doThrow(new InternalServerErrorException("Failed to create user")).when(userService).create(any(UserAccount.class));

        // Act & Assert
        assertThrows(InternalServerErrorException.class, () -> authenticationService.signUp(request));
    }

    @Test
    void signIn_shouldAuthenticateUserAndReturnToken() {
        // Arrange
        UserSignInRequestDto request = new UserSignInRequestDto("testUser", "testPassword");
        UserDetails user = mock(UserDetails.class);
        String jwtToken = "testJwtToken";

        // Создаем мок для UserDetailsService
        UserDetailsService userDetailsService = mock(UserDetailsService.class);
        // Настраиваем мок, чтобы он возвращал пользователя при вызове loadUserByUsername
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(user);

        // Устанавливаем мок в UserService
        when(userService.userDetailsService()).thenReturn(userDetailsService);

        // Настраиваем мок для JwtService, чтобы он возвращал токен
        when(jwtService.generateToken(any(UserDetails.class))).thenReturn(jwtToken);

        // Act
        JwtAuthenticationResponseDto response = authenticationService.signIn(request);

        // Assert
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        assertEquals(jwtToken, response.getToken());
    }
}