package ru.t1.opencschool.authorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.t1.opencschool.authorization.users.UserAccount;

import java.util.Optional;

/**
 * JPA репозиторий для класса {@link UserAccount}.
 */
@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
