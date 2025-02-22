package org.otus.crm.service;

import org.otus.core.repository.DataTemplate;
import org.otus.core.sessionmanager.TransactionManager;
import org.otus.crm.model.Account;

public class DbAccountServiceImpl implements DBAccountService {
    private final DataTemplate<Account> accountDataTemplate;
    private final TransactionManager transactionManager;

    public DbAccountServiceImpl(TransactionManager transactionManager, DataTemplate<Account> accountDataTemplate) {
        this.transactionManager = transactionManager;
        this.accountDataTemplate = accountDataTemplate;
    }

    @Override
    public boolean isAdminUser(String login, String password) {
        return transactionManager.doInReadOnlyTransaction(
                session -> accountDataTemplate.findByEntityField(session, "login", login).stream()
                                .findFirst()
                                .filter(account -> account.getPassword().equals(password))
                                .filter(account -> account.getRole().equals("admin"))
                                .orElse(null)
                        != null);
    }
}
