package com.example.demo.service;

import com.example.demo.dto.ClientDTO;
import com.example.demo.entitie.Client;

import java.util.List;

public interface ClientService {
    Client getById(Integer id);

    ClientDTO save(ClientDTO clientDTO);

    void delete(Integer id);

    List<Client> getAll();

    void attendEvent(Integer clientId, Integer eventId);

    void payment();

}
