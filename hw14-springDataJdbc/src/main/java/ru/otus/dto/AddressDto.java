package ru.otus.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
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

    @JsonCreator()
    public AddressDto(
            @JsonProperty(value = "id", defaultValue = "0") long id,
            @JsonProperty("street") String street
    ) {
        this.id = id;
        this.street = street;
    }
}
