package org.otus.crm.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.otus.ClientDto;
import org.otus.core.repository.ClientRepository;
import org.otus.crm.model.Client;
import org.otus.mapper.ClientMapper;
import org.otus.sessionmanager.TransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    private final ClientRepository repository;
    private final TransactionManager transactionManager;
    private final ClientMapper mapper;

    @Override
    public Client saveClient(ClientDto clientModel) {
        Client client = mapper.toClient(clientModel);
        return transactionManager.doInTransaction(() -> {
            var savedClient = repository.save(client);
            log.info("saved client: {}", savedClient);
            return savedClient;
        });
    }

    @Override
    public List<Client> findAll() {
        List<Client> clientList = new ArrayList<>(repository.findAll());
        log.info("clientList:{}", clientList);
        return clientList;
    }
}
