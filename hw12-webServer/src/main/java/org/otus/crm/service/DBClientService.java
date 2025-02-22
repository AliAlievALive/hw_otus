package org.otus.crm.service;

import java.util.List;
import java.util.Optional;
import org.otus.crm.model.Client;

public interface DBClientService {

    Client saveClient(Client client);

    Optional<Client> getClient(long id);

    List<Client> findAll();
}
