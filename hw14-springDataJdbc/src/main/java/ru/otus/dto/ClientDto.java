package ru.otus.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
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

    @JsonCreator
    public ClientDto(
            @JsonProperty(value = "id", defaultValue = "0") long id,
            @JsonProperty("name") String name,
            @JsonProperty("address") AddressDto address,
            @JsonProperty("phones") List<PhoneDto> phones
    ) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
    }
}
