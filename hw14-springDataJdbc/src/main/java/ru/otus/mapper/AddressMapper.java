package ru.otus.mapper;

import ru.otus.model.Address;
import ru.otus.dto.AddressDto;

public class AddressMapper {
    private AddressMapper(){}

    public static AddressDto toAddressDto(Address address) {
        return address != null ? new AddressDto(address.getId(), address.getStreet()) : null;
    }

    public static Address fromAddressDto(AddressDto addressDto) {
        return addressDto != null ?  new Address(addressDto.getId(), addressDto.getStreet()) : null;
    }
}
