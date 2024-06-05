package ru.t1.opencschool.authorization.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.t1.opencschool.authorization.users.UserAccount;

public interface UserService {
    UserAccount save(UserAccount userAccount);

    UserAccount create(UserAccount userAccount);

    UserAccount getByUsername(String username);

    UserDetailsService userDetailsService();

    UserAccount getCurrentUser();

    @Deprecated
    void getAdmin();
}
