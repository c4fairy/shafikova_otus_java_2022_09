package ru.otus.mapper;

import ru.otus.crm.model.Phone;
import ru.otus.dto.PhoneDto;

public class PhoneMapper {
    private PhoneMapper() {}

    public static PhoneDto toPhoneDto(Phone phone) {
        return phone != null ? new PhoneDto(phone.getId(), phone.getNumber()) : null;
    }

    public static Phone fromPhoneDto(PhoneDto phoneDto) {
        return phoneDto != null ? new Phone(phoneDto.getId(), phoneDto.getNumber()) : null;
    }
}
