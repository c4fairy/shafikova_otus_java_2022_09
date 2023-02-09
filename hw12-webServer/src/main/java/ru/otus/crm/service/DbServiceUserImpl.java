package ru.otus.crm.service;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.sessionmanager.TransactionManager;
import ru.otus.crm.model.User;

import java.util.Optional;

public class DbServiceUserImpl implements DBServiceUser {
    private final DataTemplate<User> userDataTemplate;
    private final TransactionManager transactionManager;

    public DbServiceUserImpl(TransactionManager transactionManager, DataTemplate<User> userDataTemplate) {
        this.userDataTemplate = userDataTemplate;
        this.transactionManager = transactionManager;
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return this.transactionManager.doInReadOnlyTransaction(session ->
                userDataTemplate.findByEntityField(session, "username", username).stream().findFirst()
        );
    }

    @Override
    public User saveUser(User user) {
        return transactionManager.doInTransaction(session -> {
            var userCloned = user.clone();
            if (user.getId() == null || user.getId() == 0) {
                userDataTemplate.insert(session, userCloned);
            } else {
                userDataTemplate.update(session, userCloned);
            }
            return userCloned;
        });
    }

}
