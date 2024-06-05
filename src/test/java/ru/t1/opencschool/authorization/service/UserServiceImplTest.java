package ru.t1.opencschool.authorization.service;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.t1.opencschool.authorization.repository.UserAccountRepository;
import ru.t1.opencschool.authorization.roles.Role;
import ru.t1.opencschool.authorization.users.UserAccount;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserAccountRepository userAccountRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void save_shouldSaveUser() {
        // Arrange
        UserAccount userAccount = new UserAccount(1L,"testUser", "test@example.com", "testPassword", Role.ROLE_USER);
        when(userAccountRepository.save(userAccount)).thenReturn(userAccount);

        // Act
        UserAccount savedUser = userService.save(userAccount);

        // Assert
        verify(userAccountRepository).save(userAccount);
        assertEquals(userAccount, savedUser);
    }

    @Test
    void create_shouldCreateUser() {
        // Arrange
        UserAccount userAccount = new UserAccount(1L,"testUser", "test@example.com", "testPassword", Role.ROLE_USER);
        when(userAccountRepository.existsByUsername(userAccount.getUsername())).thenReturn(false);
        when(userAccountRepository.existsByEmail(userAccount.getEmail())).thenReturn(false);
        when(userAccountRepository.save(userAccount)).thenReturn(userAccount);

        // Act
        UserAccount createdUser = userService.create(userAccount);

        // Assert
        verify(userAccountRepository).existsByUsername(userAccount.getUsername());
        verify(userAccountRepository).existsByEmail(userAccount.getEmail());
        verify(userAccountRepository).save(userAccount);
        assertEquals(userAccount, createdUser);
    }

    @Test
    void create_shouldThrowException_whenUserAlreadyExists() {
        // Arrange
        UserAccount userAccount = new UserAccount(1L,"testUser", "test@example.com", "testPassword", Role.ROLE_USER);
        when(userAccountRepository.existsByUsername(userAccount.getUsername())).thenReturn(true);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.create(userAccount));
        verify(userAccountRepository).existsByUsername(userAccount.getUsername());
        verify(userAccountRepository, never()).existsByEmail(anyString());
        verify(userAccountRepository, never()).save(any(UserAccount.class));
    }

    @Test
    void getByUsername_shouldReturnUser() {
        // Arrange
        String username = "testUser";
        UserAccount userAccount = new UserAccount(1L,username, "test@example.com", "testPassword", Role.ROLE_USER);
        when(userAccountRepository.findByUsername(username)).thenReturn(Optional.of(userAccount));

        // Act
        UserAccount foundUser = userService.getByUsername(username);

        // Assert
        verify(userAccountRepository).findByUsername(username);
        assertEquals(userAccount, foundUser);
    }

    @Test
    void getByUsername_shouldThrowException_whenUserNotFound() {
        // Arrange
        String username = "testUser";
        when(userAccountRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> userService.getByUsername(username));
        verify(userAccountRepository).findByUsername(username);
    }

    @Test
    void getCurrentUser_shouldReturnCurrentUser() {
        // Arrange
        String username = "testUser";
        UserAccount userAccount = new UserAccount(1L, username, "test@example.com", "testPassword", Role.ROLE_USER);
        when(userAccountRepository.findByUsername(username)).thenReturn(Optional.of(userAccount));

        // Act
        UserAccount currentUser = userService.getCurrentUser();

        // Assert
        verify(userAccountRepository).findByUsername(username);
        assertEquals(userAccount, currentUser);
    }

//    @Test
//    void getAdmin_shouldGrantAdminRoleToCurrentUser()  {
//        // Arrange
//        String username = "testUser";
//        UserAccount userAccount = new UserAccount(1L,username, "test@example.com", "testPassword", Role.ROLE_USER);
//        when(userAccountRepository.findByUsername(username)).thenReturn(Optional.of(userAccount));
//
//        // Установка аутентификации в контексте безопасности
//        SecurityContext securityContext = mock(SecurityContext.class);
//        Authentication authentication = mock(Authentication.class);
//        when(authentication.getName()).thenReturn(username);
//        when(securityContext.getAuthentication()).thenReturn(authentication);
//        SecurityContextHolder.setContext(securityContext);
//
//        // Act
//        userService.getAdmin();
//
//        // Assert
//        verify(userAccountRepository).findByUsername(username);
//        verify(userAccountRepository).save(userAccount);
//        assertEquals(Role.ROLE_ADMIN, userAccount.getRole());
//
//        // Очистка контекста безопасности после теста
//        SecurityContextHolder.clearContext();
//    }

}