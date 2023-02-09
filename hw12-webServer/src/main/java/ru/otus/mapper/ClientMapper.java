package ru.otus.mapper;

import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.dto.AddressDto;
import ru.otus.dto.ClientDto;
import ru.otus.dto.PhoneDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientMapper {
    private ClientMapper(){}

    public static Client fromClientDto(ClientDto clientDto) {
        if (clientDto != null) {
            Client client = new Client(clientDto.getId(), clientDto.getName());

            Address address = AddressMapper.fromAddressDto(clientDto.getAddress());
            address.setClient(client);
            client.setAddress(address);

            List<Phone> phones = Optional.ofNullable(clientDto.getPhones()).orElse(new ArrayList<>())
                    .stream()
                    .map(phoneDto -> {
                        Phone phone = PhoneMapper.fromPhoneDto(phoneDto);
                        phone.setClient(client);
                        return phone;
                    })
                    .toList();
            client.setPhones(phones);

            return client;
        }
        return null;
    }

    public static ClientDto toClientDto(Client client) {
        if (client != null) {
            AddressDto addressDto = AddressMapper.toAddressDto(client.getAddress());
            List<PhoneDto> phoneDtos =
                    Optional.ofNullable(client.getPhones()).orElse(new ArrayList<>())
                            .stream()
                            .map(PhoneMapper::toPhoneDto)
                            .toList();
            return new ClientDto(client.getId(), client.getName(), addressDto, phoneDtos);
        }
        return null;
    }
}
