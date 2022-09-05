package com.example.demo.service;

import com.example.demo.dto.CoachDTO;
import com.example.demo.dto.EventDTO;
import com.example.demo.entitie.Coach;

import java.util.List;

public interface CoachService {
    Coach getById(Integer id);

    CoachDTO save(CoachDTO coachDTO);

    void delete(Integer id);

    List<Coach> getAll();

    void scheduleEvent(EventDTO eventDTO, Integer coachId);

    CoachDTO getMostProductiveCoach();
}
