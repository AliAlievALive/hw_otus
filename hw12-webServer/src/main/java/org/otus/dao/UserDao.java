package org.otus.dao;

import java.util.Optional;
import org.otus.model.Account;

public interface UserDao {

    Optional<Account> findById(long id);

    Optional<Account> findByLogin(String login);
}
