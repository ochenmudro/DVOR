package com.example.demo.controller;

import com.example.demo.dto.ClientDTO;
import com.example.demo.entitie.Client;
import com.example.demo.mapper.ClientMapper;
import com.example.demo.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/clients/")
public class ClientController {

    private final ClientService clientService;
    private final ClientMapper clientMapper;

    public ClientController(ClientService clientService, ClientMapper clientMapper) {
        this.clientService = clientService;
        this.clientMapper = clientMapper;
    }

    @GetMapping
    public ResponseEntity<String> getClient(@RequestParam Integer clientId) {
        if (clientId == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Client client = this.clientService.getById(clientId);
        if (client == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(client.toString(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@RequestBody @Valid ClientDTO clientDTO) {
        if (clientDTO == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        this.clientService.save(clientDTO);
        return new ResponseEntity<>(clientDTO, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ClientDTO> updateClient(@RequestBody @Valid ClientDTO clientDTO) {
        if (clientDTO == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        this.clientService.save(clientDTO);
        return new ResponseEntity<>(clientDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Client> deleteClient(@PathVariable("id") Integer id) {
        Client client = this.clientService.getById(id);
        if (client == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        this.clientService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "all")
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        List<ClientDTO> clients = this.clientService.getAll().stream().map(clientMapper::mapToDTO).toList();
        if (clients.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @PutMapping(value = "attend/")
    public ResponseEntity<Client> attendEvent(@RequestParam(value = "clientId") String clientId,
                                              @RequestParam(value = "eventId") String eventId) {
        Integer cId = Integer.parseInt(clientId);
        Integer eId = Integer.parseInt(eventId);
        clientService.attendEvent(cId, eId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
