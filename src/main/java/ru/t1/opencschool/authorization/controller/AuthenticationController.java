package ru.t1.opencschool.authorization.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.t1.opencschool.authorization.dto.JwtAuthenticationResponseDto;
import ru.t1.opencschool.authorization.dto.UserSignInRequestDto;
import ru.t1.opencschool.authorization.dto.UserSignUpRequestDto;
import ru.t1.opencschool.authorization.service.AuthenticationService;

/**
 * Контроллер регистрации и авторизации пользователя.
 */
@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/sign-up")
    public JwtAuthenticationResponseDto signUp(@RequestBody @Valid UserSignUpRequestDto request) throws Exception {
        return authenticationService.signUp(request);
    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/sign-in")
    public JwtAuthenticationResponseDto signIn(@RequestBody @Valid UserSignInRequestDto request) {
        return authenticationService.signIn(request);
    }
}
