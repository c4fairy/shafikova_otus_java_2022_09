package ru.otus.crm.service;

import ru.otus.crm.model.User;

import java.util.Optional;

public interface DBServiceUser {
    User saveUser(User user);
    Optional<User> getByUsername(String username);
}
