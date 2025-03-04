package org.otus.crm.service;

import java.util.List;
import org.otus.ClientDto;
import org.otus.crm.model.Client;

public interface ClientService {

    Client saveClient(ClientDto client);

    List<Client> findAll();
}
