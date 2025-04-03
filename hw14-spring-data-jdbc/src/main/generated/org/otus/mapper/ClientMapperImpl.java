package org.otus.mapper;

import java.util.List;
import javax.annotation.processing.Generated;
import org.otus.ClientDto;
import org.otus.crm.model.Address;
import org.otus.crm.model.Client;
import org.otus.crm.model.Phone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-03T20:16:16+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class ClientMapperImpl implements ClientMapper {

    @Autowired
    private PhoneMapper phoneMapper;

    @Override
    public Client toClient(ClientDto client) {
        if ( client == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        Address address = null;
        List<Phone> phones = null;

        id = client.getId();
        name = client.getName();
        address = map( client.getAddress() );
        phones = phoneMapper.map( client.getPhones() );

        Client client1 = new Client( id, name, address, phones );

        return client1;
    }
}
