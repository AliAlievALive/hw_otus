package org.otus.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import org.otus.ClientDto;
import org.otus.crm.model.Client;
import org.otus.crm.service.ClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ClientsRestController {
    private final ClientService clientsService;

    @GetMapping("/api/clients")
    public List<Client> getClientByName() {
        return clientsService.findAll();
    }

    @PostMapping("/api/clients")
    public Client saveClient(@RequestBody ClientDto clientModel) {
        return clientsService.saveClient(clientModel);
    }
}
