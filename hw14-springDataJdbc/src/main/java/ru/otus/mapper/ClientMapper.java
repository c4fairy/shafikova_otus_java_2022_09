package ru.otus.mapper;

import ru.otus.model.Address;
import ru.otus.model.Client;
import ru.otus.model.Phone;
import ru.otus.dto.AddressDto;
import ru.otus.dto.ClientDto;
import ru.otus.dto.PhoneDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientMapper {
    private ClientMapper(){}

    public static Client fromClientDto(ClientDto clientDto) {
        if (clientDto != null) {
            Address address = AddressMapper.fromAddressDto(clientDto.getAddress());

            Set<Phone> phones = Optional.ofNullable(clientDto.getPhones()).orElse(new ArrayList<>())
                    .stream()
                    .map(phoneDTO -> PhoneMapper.fromPhoneDto(phoneDTO))
                    .collect(Collectors.toSet());

            return new Client(clientDto.getId(), clientDto.getName(), address, phones);
        }
        return null;
    }

    public static ClientDto toClientDto(Client client) {
        if (client != null) {
            AddressDto addressDTO = AddressMapper.toAddressDto(client.getAddress());
            List<PhoneDto> phoneDTOs =
                    Optional.ofNullable(client.getPhones()).orElse(Collections.emptySet())
                            .stream()
                            .map(PhoneMapper::toPhoneDto)
                            .toList();
            return new ClientDto(client.getId(), client.getName(), addressDTO, phoneDTOs);
        }
        return null;
    }
}
