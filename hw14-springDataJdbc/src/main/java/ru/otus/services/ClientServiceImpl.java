package ru.otus.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dto.ClientDto;
import ru.otus.mapper.ClientMapper;
import ru.otus.model.Client;
import ru.otus.repository.ClientRepository;
import ru.otus.sessionmanager.TransactionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final TransactionManager transactionManager;

    public ClientServiceImpl(ClientRepository clientRepository, TransactionManager transactionManager) {
        this.clientRepository = clientRepository;
        this.transactionManager = transactionManager;
    }

    @Override
    @Transactional
    public ClientDto saveClient(ClientDto clientDTO) {
        Client client = ClientMapper.fromClientDto(clientDTO);
        Client savedClient = transactionManager.doInTransaction(() -> this.clientRepository.save(client));
        return ClientMapper.toClientDto(savedClient);
    }

    @Override
    public Optional<ClientDto> getClient(long id) {
        Client client = this.clientRepository.findById(id).orElse(null);
        return Optional.ofNullable(ClientMapper.toClientDto(client));
    }

    @Override
    public List<ClientDto> findAll() {
        List<ClientDto> clients = new ArrayList<>();
        this.clientRepository.findAll().forEach(client -> clients.add(ClientMapper.toClientDto(client)));
        return clients;
    }
}
