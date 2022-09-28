package com.example.demo.controller;

import com.example.demo.dto.EventDTO;
import com.example.demo.entitie.Coach;
import com.example.demo.entitie.Event;
import com.example.demo.service.CoachService;
import com.example.demo.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/events")
public class EventController {
    private final EventService eventService;
    private final CoachService coachService;

    public EventController(EventService eventService, CoachService coachService) {
        this.eventService = eventService;
        this.coachService = coachService;
    }

    @GetMapping
    public ResponseEntity<List<EventDTO>> getAll() {
        List<EventDTO> events = this.eventService.getAll();
        if (events.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/future")
    public ResponseEntity<List<EventDTO>> getAllFutureEvents() {
        List<EventDTO> events = this.eventService.getAllFutureEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/coach")
    public ResponseEntity<List<EventDTO>> getAllFutureEventsByCoach(@RequestParam Integer coachId) {
        Coach coach = this.coachService.getById(coachId);
        List<EventDTO> events = this.eventService.getAllFutureEventsByCoach(coach);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/client")
    public ResponseEntity<List<EventDTO>> getAllFutureEventsByClient(@RequestParam Integer clientId) {
        List<EventDTO> events = this.eventService.getByClient(clientId);
        System.out.println("completed");
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/froom")
    public List<EventDTO> getAllEventsByFitnessRoom(@RequestParam Integer frId) {
        return eventService.getByFitnessRoom(frId);
    }
}
