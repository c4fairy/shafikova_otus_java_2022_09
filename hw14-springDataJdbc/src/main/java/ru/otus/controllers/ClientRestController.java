package ru.otus.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.dto.ClientDto;
import ru.otus.services.ClientService;

@RestController
public class ClientRestController {
    private final ClientService clientService;

    public ClientRestController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/api/client/save")
    public ResponseEntity<?> clientSave(@RequestBody ClientDto client) {
        ClientDto processedClientDTO = clientService.saveClient(client);
        return ResponseEntity.ok(processedClientDTO);
    }
}