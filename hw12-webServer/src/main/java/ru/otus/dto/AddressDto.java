package ru.otus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddressDto {
    @JsonProperty("id")
    private long id;

    @JsonProperty("street")
    private String street;

    public AddressDto(
            long id,
            String street
    ) {
        this.id = id;
        this.street = street;
    }
}
