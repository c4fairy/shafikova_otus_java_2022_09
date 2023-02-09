package ru.otus.services;

import ru.otus.dto.ClientDto;

import java.util.List;

public interface ClientService {
    List<ClientDto> findAll();
    ClientDto findById(Long id);
    void addClient(String name, String address, String[] phones);
}
