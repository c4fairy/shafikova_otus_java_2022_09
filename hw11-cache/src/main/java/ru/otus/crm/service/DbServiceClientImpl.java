package ru.otus.crm.service;

import ru.otus.cache.Cache;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.sessionmanager.TransactionManager;
import ru.otus.crm.model.Client;

import java.util.List;
import java.util.Optional;

public class DbServiceClientImpl implements DBServiceClient {

    private final DataTemplate<Client> clientDataTemplate;
    private final TransactionManager transactionManager;
    private final Cache<String, Client> cache;

    public DbServiceClientImpl(TransactionManager transactionManager, DataTemplate<Client> clientDataTemplate) {
        this(transactionManager, clientDataTemplate, null);
    }

    public DbServiceClientImpl(TransactionManager transactionManager, DataTemplate<Client> clientDataTemplate, Cache<String, Client> cache) {
        this.transactionManager = transactionManager;
        this.clientDataTemplate = clientDataTemplate;
        this.cache = cache;
    }

    @Override
    public Client saveClient(Client client) {
        return transactionManager.doInTransaction(session -> {
            var clientCloned = client.clone();
            if (client.getId() == null) {
                clientDataTemplate.insert(session, clientCloned);
                saveIntoCache(clientCloned);
                return clientCloned;
            }
            clientDataTemplate.update(session, clientCloned);
            saveIntoCache(clientCloned);
            return clientCloned;
        });
    }

    @Override
    public Optional<Client> getClient(long id) {
        Client client = getFromCache(id);
        if (client == null) {
            Optional<Client> clientFromDB =
                    transactionManager.doInTransaction(connection -> clientDataTemplate.findById(connection, id));
            clientFromDB.ifPresent(this::saveIntoCache);
            return clientFromDB;
        }
        else {
            return Optional.of(client);
        }
    }

    @Override
    public List<Client> findAll() {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var clientList = clientDataTemplate.findAll(session);
            clientList.forEach(this::saveIntoCache);
            return clientList;
        });
    }

    private void saveIntoCache(Client client) {
        if (cache != null && client != null) {
            this.cache.put(String.valueOf(client.getId()), client);
        }
    }

    private Client getFromCache(Long id) {
        return this.cache == null ? null : this.cache.get(String.valueOf(id));
    }
}
