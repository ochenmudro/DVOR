package com.example.demo.service.impl;

import com.example.demo.DVORApplication;
import com.example.demo.dto.ClientDTO;
import com.example.demo.entitie.Client;
import com.example.demo.entitie.Event;
import com.example.demo.mapper.ClientMapper;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.EventRepository;
import com.example.demo.service.ClientService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.logging.Logger;

@Service
public class ClientServiceImpl implements ClientService {

    private static final Logger log = Logger.getLogger(DVORApplication.class.getName());
    private final ClientMapper clientMapper;
    private final ClientRepository clientRepository;
    private final EventRepository eventRepository;

    public ClientServiceImpl(ClientMapper clientMapper, ClientRepository clientRepository, EventRepository eventRepository) {
        this.clientMapper = clientMapper;
        this.clientRepository = clientRepository;
        this.eventRepository = eventRepository;
    }


    @Override
    public ClientDTO save(ClientDTO clientDTO) {
        Client client = clientMapper.mapToEntity(clientDTO);
//        List<Event> events = new ArrayList<>();
//        List<Integer> eventsId = client.getEventsId();
//        for (Integer id : eventsId) {
//            Event event = eventRepository.findById(id)
//                    .orElseThrow(() -> new EntityNotFoundException("Event with id " + id));
//            events.add(event);
//        }
//        client.setEvents(events);
        Client savedClient = clientRepository.save(client);
        log.info("Client is saved");
        return clientMapper.mapToDTO(savedClient);
    }

    @Override
    public void delete(Integer id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client with id " + id));
        clientRepository.delete(client);
    }

    @Override
    public Client getById(Integer id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> {
                    log.warning("Client with id " + id + " not found");
                    return new EntityNotFoundException("Client with id " + id);
                });
    }

    @Override
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    @Override
    public void attendEvent(Integer clientId, Integer eventId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client with id " + clientId));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event with id " + clientId));
        if (event.getFitnessRoom().getSize() > event.getClients().size()
                && client.getAccount() >= event.getFitnessRoom().getEventCost()) {
            client.addEvent(event);
            event.addClient(client);
            clientRepository.save(client);
        }
        else log.info("the place is full");
    }

    @Override
    @Scheduled(fixedRate = 60000)
    public void payment() {

    }

}
