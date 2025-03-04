package org.otus.mapper;

import org.mapstruct.Mapper;
import org.otus.ClientDto;
import org.otus.crm.model.Address;
import org.otus.crm.model.Client;

@Mapper(componentModel = "spring", uses = PhoneMapper.class)
public interface ClientMapper {
    Client toClient(ClientDto client);

    default String map(Address address) {
        return address != null ? address.toString() : null;
    }

    default Address map(String address) {
        return address != null ? new Address(null, address, null) : null;
    }
}
