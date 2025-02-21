package org.otus.services;

import lombok.Getter;
import org.hibernate.cfg.Configuration;
import org.otus.core.repository.DataTemplateHibernate;
import org.otus.core.repository.HibernateUtils;
import org.otus.core.sessionmanager.TransactionManagerHibernate;
import org.otus.crm.dbmigrations.MigrationsExecutorFlyway;
import org.otus.crm.model.Address;
import org.otus.crm.model.Client;
import org.otus.crm.model.Phone;
import org.otus.crm.service.DBAccountService;
import org.otus.crm.service.DBClientService;
import org.otus.crm.service.DbAccountServiceImpl;
import org.otus.crm.service.DbClientServiceImpl;
import org.otus.model.Account;

@Getter
public class DBManager {
    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";
    private final DBClientService dbClientService;
    private final DBAccountService dbAccountService;

    public DBManager() {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var sessionFactory = HibernateUtils.buildSessionFactory(
                configuration, Client.class, Address.class, Phone.class, Account.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);
        ///
        var clientTemplate = new DataTemplateHibernate<>(Client.class);
        var accountTemplate = new DataTemplateHibernate<>(Account.class);
        ///
        dbClientService = new DbClientServiceImpl(transactionManager, clientTemplate);
        dbAccountService = new DbAccountServiceImpl(transactionManager, accountTemplate);
    }
}
