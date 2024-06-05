package ru.t1.opencschool.authorization.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.t1.opencschool.authorization.dto.JwtAuthenticationResponseDto;
import ru.t1.opencschool.authorization.dto.UserSignInRequestDto;
import ru.t1.opencschool.authorization.dto.UserSignUpRequestDto;
import ru.t1.opencschool.authorization.exceptions.InternalServerErrorException;
import ru.t1.opencschool.authorization.users.UserAccount;
import ru.t1.opencschool.authorization.roles.Role;

/**
 * Сервис авторизации.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponseDto signUp(UserSignUpRequestDto request) throws Exception{

        var user = UserAccount.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        try {
            // Сохранение пользователя в базе данных
            userService.create(user);
        } catch (Exception e) {
            // Обработка исключения, если возникла ошибка при сохранении пользователя
            throw new InternalServerErrorException("Failed to create user", e);
        }
;

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponseDto(jwt);
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponseDto signIn(UserSignInRequestDto request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponseDto(jwt);
    }
}
