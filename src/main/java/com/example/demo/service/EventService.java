package com.example.demo.service;

import com.example.demo.dto.EventDTO;
import com.example.demo.entitie.Client;
import com.example.demo.entitie.Coach;
import com.example.demo.entitie.Event;

import java.util.List;

public interface EventService {
    Event getById(Integer id);

    EventDTO save(EventDTO eventDTO);

    void delete(Integer id);

    List<EventDTO> getAll();

    List<EventDTO> getAllFutureEvents();

    List<EventDTO> getAllFutureEventsByCoach(Coach coach);

    List<EventDTO> getByClient(Integer clientId);

    List<EventDTO> getByFitnessRoom(Integer id);
}
