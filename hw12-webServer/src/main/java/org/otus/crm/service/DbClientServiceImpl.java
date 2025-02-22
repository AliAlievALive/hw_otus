package org.otus.crm.service;

import java.util.List;
import java.util.Optional;
import org.otus.cache.HwCache;
import org.otus.cache.MyCache;
import org.otus.core.repository.DataTemplate;
import org.otus.core.sessionmanager.TransactionManager;
import org.otus.crm.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbClientServiceImpl implements DBClientService {
    private static final Logger log = LoggerFactory.getLogger(DbClientServiceImpl.class);

    private final DataTemplate<Client> clientDataTemplate;
    private final TransactionManager transactionManager;
    private final HwCache<Long, Client> cache;

    public DbClientServiceImpl(TransactionManager transactionManager, DataTemplate<Client> clientDataTemplate) {
        this.transactionManager = transactionManager;
        this.clientDataTemplate = clientDataTemplate;
        this.cache = new MyCache<>();
    }

    @Override
    public Client saveClient(Client client) {
        return transactionManager.doInTransaction(session -> {
            var clientCloned = client.clone();
            if (client.getId() == null) {
                var savedClient = clientDataTemplate.insert(session, clientCloned);
                log.info("created client: {}", clientCloned);
                return savedClient;
            }
            var savedClient = clientDataTemplate.update(session, clientCloned);
            log.info("updated client: {}", savedClient);
            cache.put(savedClient.getId(), savedClient);
            return savedClient;
        });
    }

    @Override
    public Optional<Client> getClient(long id) {
        Client cachedClient = cache.get(id);
        if (cachedClient != null) {
            log.info("Loaded client from cache: {}", cachedClient);
            return Optional.of(cachedClient);
        }
        return transactionManager.doInReadOnlyTransaction(session -> {
            var clientOptional = clientDataTemplate.findById(session, id);
            log.info("client: {}", clientOptional);
            clientOptional.ifPresent(client -> cache.put(id, client));
            return clientOptional;
        });
    }

    @Override
    public List<Client> findAll() {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var clientList = clientDataTemplate.findAll(session);
            log.info("clientList:{}", clientList);
            clientList.forEach(client -> cache.put(client.getId(), client));
            return clientList;
        });
    }
}
