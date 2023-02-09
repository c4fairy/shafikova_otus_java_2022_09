package ru.otus.dto;

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

    public PhoneDto(
            long id,
            String number
    ) {
        this.id = id;
        this.number = number;
    }
}
