package ru.otus.services;

import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.dto.ClientDto;
import ru.otus.mapper.ClientMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ClientServiceImpl implements ClientService {
    private final DBServiceClient dbServiceClient;

    public ClientServiceImpl(DBServiceClient dbServiceClient) {
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    public List<ClientDto> findAll() {
        List<Client> clients = Optional.ofNullable(this.dbServiceClient.findAll()).orElse(Collections.emptyList());
        return clients.stream().map(ClientMapper::toClientDto).toList();
    }

    @Override
    public ClientDto findById(Long id) {
        Client client = this.dbServiceClient.getClient(id).orElse(null);
        return ClientMapper.toClientDto(client);
    }

    @Override
    public void addClient(String clientName, String street, String[] phoneNumbers) {
        if (
                clientName != null && !clientName.isEmpty()
                        && street != null && !street.isEmpty()
        ) {
            Client client = new Client(clientName);

            Address address = new Address(street);
            address.setClient(client);
            client.setAddress(address);

            List<Phone> phones = Arrays.stream(Optional.ofNullable(phoneNumbers).orElse(new String[]{}))
                    .map(phoneNumber -> {
                        Phone phone = new Phone(phoneNumber);
                        phone.setClient(client);
                        return phone;

                    }).toList();
            client.setPhones(phones);

            dbServiceClient.saveClient(client);
        }
    }
}
