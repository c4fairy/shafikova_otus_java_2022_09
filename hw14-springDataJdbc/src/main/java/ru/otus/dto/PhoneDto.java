package ru.otus.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PhoneDto {
    @JsonProperty("id")
    private long id;

    @JsonProperty("number")
    private String number;

    @JsonCreator
    public PhoneDto(
            @JsonProperty(value = "id", defaultValue = "0") long id,
            @JsonProperty("number") String number
    ) {
        this.id = id;
        this.number = number;
    }
}
