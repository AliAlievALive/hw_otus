package org.otus.services;

import lombok.AllArgsConstructor;
import org.otus.crm.service.DBAccountService;

@AllArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {

    private final DBAccountService serviceAccount;

    @Override
    public boolean isAdmin(String login, String password) {
        return serviceAccount.isAdminUser(login, password);
    }
}
