package org.otus.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import org.otus.ClientDto;
import org.otus.crm.model.Client;
import org.otus.crm.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class ClientsController {

    private final ClientService service;

    @GetMapping("/")
    public String clients() {
        return "clients";
    }

    @GetMapping("/clients/create")
    public String clientsCreateView(Model model) {
        model.addAttribute("client", new ClientDto());
        return "clientCreate";
    }

    @GetMapping("/clients")
    public String clientsListView(Model model) {
        List<Client> clients = service.findAll();
        model.addAttribute("clients", clients);
        return "clientsList";
    }
}
