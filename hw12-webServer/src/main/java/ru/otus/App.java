package ru.otus;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.cfg.Configuration;
import ru.otus.core.repository.DataTemplateHibernate;
import ru.otus.core.repository.HibernateUtils;
import ru.otus.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.model.User;
import ru.otus.crm.service.DbServiceClientImpl;
import ru.otus.crm.service.DbServiceUserImpl;
import ru.otus.server.WebServer;
import ru.otus.server.WebServerImpl;
import ru.otus.services.ClientServiceImpl;
import ru.otus.services.TemplateProcessorImpl;
import ru.otus.services.UserServiceImpl;


public class App {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) throws Exception {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUsername = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUsername, dbPassword).executeMigrations();

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class, User.class);
        var transactionManager = new TransactionManagerHibernate(sessionFactory);

        var clientTemplate = new DataTemplateHibernate<>(Client.class);
        var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);
        var clientService = new ClientServiceImpl(dbServiceClient);

        var userTemplate = new DataTemplateHibernate<>(User.class);
        var dbServiceUser = new DbServiceUserImpl(transactionManager, userTemplate);
        var userService = new UserServiceImpl(dbServiceUser);

        var objectMapper = new ObjectMapper();
        var templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        WebServer webServer = new WebServerImpl(WEB_SERVER_PORT, clientService, userService, objectMapper, templateProcessor);
        webServer.start();
        webServer.join();
    }
}