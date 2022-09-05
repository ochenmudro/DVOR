package com.example.demo.controller;

import com.example.demo.dto.CoachDTO;
import com.example.demo.dto.EventDTO;
import com.example.demo.entitie.Coach;
import com.example.demo.entitie.Event;
import com.example.demo.service.CoachService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/coaches/")
public class CoachController {


    private final CoachService coachService;

    public CoachController(CoachService coachService) {
        this.coachService = coachService;
    }

    @GetMapping
    public ResponseEntity<Coach> getCoach(@RequestParam Integer coachId) {
        if (coachId == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Coach coach = this.coachService.getById(coachId);
        if (coach == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(coach, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CoachDTO> createCoach(@RequestBody @Valid CoachDTO coachDTO) {
        if (coachDTO == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        this.coachService.save(coachDTO);
        return new ResponseEntity<>(coachDTO, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CoachDTO> updateCoach(@RequestBody @Valid CoachDTO coachDTO) {
        if (coachDTO == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        this.coachService.save(coachDTO);
        return new ResponseEntity<>(coachDTO, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Coach> deleteCoach(@RequestParam Integer id) {
        Coach coach = this.coachService.getById(id);
        if (coach == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        this.coachService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "all")
    public ResponseEntity<List<Coach>> getAllCoaches() {
        List<Coach> coaches = this.coachService.getAll();
        if (coaches.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(coaches, HttpStatus.OK);
    }

    @PostMapping(value = "schedule")
    public ResponseEntity<EventDTO> scheduleEvent(@RequestParam Integer coachId, @RequestBody @Valid EventDTO eventDTO) {
        coachService.scheduleEvent(eventDTO, coachId);
        return new ResponseEntity<>(eventDTO, HttpStatus.OK);
    }

    @GetMapping(value = "productive")
    public ResponseEntity<CoachDTO> getMostProductiveCoach(){
        return new ResponseEntity<>(coachService.getMostProductiveCoach(), HttpStatus.OK);
    }
}
