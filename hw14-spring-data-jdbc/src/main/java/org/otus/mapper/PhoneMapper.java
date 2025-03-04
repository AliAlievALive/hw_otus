package org.otus.mapper;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.mapstruct.Mapper;
import org.otus.crm.model.Phone;

@Mapper(componentModel = "spring")
public interface PhoneMapper {
    default List<Phone> map(String phone) {
        return Arrays.stream(phone.split(",")).map(ph -> new Phone(ph, null)).toList();
    }

    default String map(List<Phone> phones) {
        return phones.stream()
                .map(Phone::getNumber)
                .filter(Objects::nonNull)
                .reduce((a, b) -> a + "," + b)
                .orElse("");
    }
}
