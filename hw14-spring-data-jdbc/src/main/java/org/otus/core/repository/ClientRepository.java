package org.otus.core.repository;

import java.util.List;
import org.otus.crm.model.Client;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends ListCrudRepository<Client, Long> {
    List<Client> findAll();
}
