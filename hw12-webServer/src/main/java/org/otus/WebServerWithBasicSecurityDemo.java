package org.otus;

import org.otus.crm.service.DBAccountService;
import org.otus.crm.service.DBClientService;
import org.otus.server.UsersWebServer;
import org.otus.server.UsersWebServerWithFilterBasedSecurity;
import org.otus.services.*;

/*
    Полезные для демо ссылки

    // Стартовая страница
    http://localhost:8080

    // Страница пользователей
    http://localhost:8080/clients

*/
public class WebServerWithBasicSecurityDemo {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";

    public static void main(String[] args) throws Exception {
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        var databaseManager = new DBManager();
        DBClientService dbClientService = databaseManager.getDbClientService();
        DBAccountService dbAccountService = databaseManager.getDbAccountService();
        UserAuthService authService = new UserAuthServiceImpl(dbAccountService);

        UsersWebServer usersWebServer = new UsersWebServerWithFilterBasedSecurity(
                WEB_SERVER_PORT, authService, templateProcessor, dbClientService);

        usersWebServer.start();
        usersWebServer.join();
    }
}
