package ru.otus.services;

import ru.otus.crm.model.User;
import ru.otus.crm.service.DBServiceUser;

import java.util.Objects;

public class UserServiceImpl implements UserService {
    private final DBServiceUser dbServiceUser;

    public UserServiceImpl(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    public boolean authenticate(String username, String password) {
        if (username != null) {
            User user = dbServiceUser.getByUsername(username).orElse(null);
            return user != null && Objects.equals(user.getPassword(), password);
        }
        return false;
    }

    @Override
    public boolean isExists(String username) {
        return dbServiceUser.getByUsername(username).isPresent();
    }

    @Override
    public void createUser(String username, String password) {
        dbServiceUser.saveUser(new User(username, password));
    }
}
