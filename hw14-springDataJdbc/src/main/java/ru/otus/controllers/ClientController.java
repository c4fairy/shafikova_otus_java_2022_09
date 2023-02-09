package ru.otus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.dto.ClientDto;
import ru.otus.services.ClientService;

import java.util.List;

@Controller
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping(value = {"/", "/clients/list"})
    public String clientsListView(Model model) {
        List<ClientDto> clients = clientService.findAll();
        model.addAttribute("clients", clients);
        return "clientList";
    }

    @GetMapping(value = "/client/create")
    public String clientCreateView() {
        return "clientCreate";
    }
}
