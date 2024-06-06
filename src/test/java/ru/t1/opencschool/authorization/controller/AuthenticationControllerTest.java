package ru.t1.opencschool.authorization.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.t1.opencschool.authorization.dto.JwtAuthenticationResponseDto;
import ru.t1.opencschool.authorization.dto.UserSignInRequestDto;
import ru.t1.opencschool.authorization.dto.UserSignUpRequestDto;
import ru.t1.opencschool.authorization.service.AuthenticationService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Тесты контроллера регистрации и авторизации пользователя.
 */
@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    @Test
    public void testSignUp() throws Exception {
        // Arrange
        UserSignUpRequestDto signUpRequest = new UserSignUpRequestDto();
        signUpRequest.setUsername("testUser");
        signUpRequest.setPassword("testPassword");
        signUpRequest.setEmail("test@example.com");

        JwtAuthenticationResponseDto expectedResponse = new JwtAuthenticationResponseDto();
        expectedResponse.setToken("testToken");

        when(authenticationService.signUp(signUpRequest)).thenReturn(expectedResponse);

        // Act and Assert
        mockMvc.perform(post("/api/v1/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(signUpRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void testSignIn() throws Exception {
        // Arrange
        UserSignInRequestDto signInRequest = new UserSignInRequestDto();
        signInRequest.setUsername("testUser");
        signInRequest.setPassword("testPassword");

        JwtAuthenticationResponseDto expectedResponse = new JwtAuthenticationResponseDto();
        expectedResponse.setToken("testToken");

        when(authenticationService.signIn(signInRequest)).thenReturn(expectedResponse);

        // Act and Assert
        mockMvc.perform(post("/api/v1/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(signInRequest)))
                .andExpect(status().isOk());
    }

    // Метод для преобразования объекта в JSON
    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}