package ru.otus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClientDto {
    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("address")
    private AddressDto address;

    @JsonProperty("phones")
    private List<PhoneDto> phones;

    public ClientDto(
            long id,
            String name,
            AddressDto address,
            List<PhoneDto> phones
    ) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
    }
}
