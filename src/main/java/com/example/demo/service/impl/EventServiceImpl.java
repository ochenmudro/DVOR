package com.example.demo.service.impl;

import com.example.demo.DVORApplication;
import com.example.demo.dto.EventDTO;
import com.example.demo.entitie.Client;
import com.example.demo.entitie.Coach;
import com.example.demo.entitie.Event;
import com.example.demo.mapper.EventMapper;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.EventRepository;
import com.example.demo.service.CoachService;
import com.example.demo.service.EventService;
import com.example.demo.service.FitnessRoomService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

@Service
public class EventServiceImpl implements EventService {
    private static final Logger log = Logger.getLogger(DVORApplication.class.getName());
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final ClientRepository clientRepository;
    private final CoachService coachService;
    private final FitnessRoomService fitnessRoomService;

    public EventServiceImpl(EventRepository eventRepository, EventMapper eventMapper, CoachService coachService,
                            FitnessRoomService fitnessRoomService, ClientRepository clientRepository) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.coachService = coachService;
        this.fitnessRoomService = fitnessRoomService;
        this.clientRepository = clientRepository;
    }

    @Override
    public Event getById(Integer id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> {
                    log.warning("Event with id " + id + " not found");
                    return new EntityNotFoundException("Event with id " + id);
                });
    }

    @Override
    public EventDTO save(EventDTO eventDTO) {
        Event event = eventMapper.mapToEntity(eventDTO);
        List<Client> clients = new ArrayList<>();
        List<Integer> clientsId = event.getClientsId();
        for (Integer id : clientsId) {
            Client client = clientRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Client with id " + id));
            clients.add(client);
        }
        event.setClients(clients);
        Event savedEvent = eventRepository.save(event);
        log.info("Event is saved");
        return eventMapper.mapToDTO(savedEvent);
    }

    @Override
    public void delete(Integer id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event with id " + id));
        eventRepository.delete(event);
    }

    @Override
    public List<EventDTO> getAll() {
        List<Event> events = eventRepository.findAll();
        return events.stream().map(eventMapper::mapToDTO).toList();
    }

    @Override
    public List<EventDTO> getAllFutureEvents() {
        ZoneOffset zoneOffset = ZoneId.systemDefault().getRules().getOffset(Instant.now());
        OffsetDateTime today = Calendar.getInstance().toInstant().atOffset(zoneOffset);
        List<Event> events = eventRepository.getAllFutureEvents(today);
        return events.stream().map(eventMapper::mapToDTO).toList();
    }

    @Override
    public List<EventDTO> getAllFutureEventsByCoach(Coach coach) {
        ZoneOffset zoneOffset = ZoneId.systemDefault().getRules().getOffset(Instant.now());
        OffsetDateTime today = Calendar.getInstance().toInstant().atOffset(zoneOffset);
        Integer coachId = coach.getId();
        List<Event> futureEvents = eventRepository.getAllFutureEventsByCoach(today, coachId);
        return futureEvents.stream().map(eventMapper::mapToDTO).toList();
    }

    @Override
    public List<EventDTO> getByClient(Integer clientId) {
        List<Event> events = eventRepository.findByClient(clientId);
        return events.stream().map(eventMapper::mapToDTO).toList();
    }

    @Override
    public List<EventDTO> getByFitnessRoom(Integer id) {
        List<Event> events = eventRepository.findByFitnessRoomId(id);
        return events.stream().map(eventMapper::mapToDTO).toList();
    }
}
