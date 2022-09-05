package com.example.demo.service.impl;

import com.example.demo.DVORApplication;
import com.example.demo.dto.CoachDTO;
import com.example.demo.dto.EventDTO;
import com.example.demo.entitie.Coach;
import com.example.demo.entitie.Event;
import com.example.demo.mapper.CoachMapper;
import com.example.demo.mapper.EventMapper;
import com.example.demo.repository.CoachRepository;
import com.example.demo.repository.EventRepository;
import com.example.demo.service.CoachService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.logging.Logger;

@FunctionalInterface
interface CoachSalary {
    Double getSalary(Coach coach);
}

@Service
public class CoachServiceImpl implements CoachService {
    private static final Logger log = Logger.getLogger(DVORApplication.class.getName());
    private final CoachRepository coachRepository;
    private final CoachMapper coachMapper;
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    CoachSalary salary = (coach) -> {
        List<Event> events = coach.getEvents();
        List<Double> costs = events.stream().map(event -> event.getFitnessRoom().getEventCost()).toList();
        Double sum = 0.0;
        for (Double cost : costs)
            sum += cost;
        return sum;
    };

    public CoachServiceImpl(CoachRepository coachRepository, CoachMapper coachMapper, EventRepository eventRepository, EventMapper eventMapper) {
        this.coachRepository = coachRepository;
        this.coachMapper = coachMapper;
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    @Override
    public Coach getById(Integer id) {
        return coachRepository.findById(id)
                .orElseThrow(() -> {
                    log.warning("Coach with id " + id + " not found");
                    return new EntityNotFoundException("Coach with id " + id);
                });
    }

    @Override
    public CoachDTO save(CoachDTO coachDTO) {
        Coach coach = coachMapper.mapToEntity(coachDTO);
        Coach savedCoach = coachRepository.save(coach);
        log.info("Coach is saved");

        return coachMapper.mapToDTO(savedCoach);
    }

    @Override
    public void delete(Integer id) {
        Coach coach = coachRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Coach with id " + id));
        coachRepository.delete(coach);
    }

    @Override
    public List<Coach> getAll() {
        return coachRepository.findAll();
    }

    @Override
    public void scheduleEvent(EventDTO eventDTO, Integer coachId) {
        Event event = eventMapper.mapToEntity(eventDTO);
        List<Event> events = eventRepository.findByFitnessRoomId(event.getFitnessRoom().getId());
        if (events.stream().noneMatch(event1 -> event1.getDateTime().compareTo(event.getDateTime()) == 0)) {
            Coach coach = coachRepository.findById(coachId)
                    .orElseThrow(() -> new EntityNotFoundException("Coach with id " + coachId));
            event.setCoach(coach);
            eventRepository.save(event);
            log.info("event saved");
        } else
            log.info("booked");
    }

    @Override
    public CoachDTO getMostProductiveCoach() {
        List<Coach> coaches = coachRepository.findAll();
        Coach coach = new Coach();
        Double max = 0.0;
        for (Coach coach1 : coaches) {
            if (salary.getSalary(coach1) > max) {
                coach = coach1;
                max = salary.getSalary(coach1);
            }
        }
        return coachMapper.mapToDTO(coach);
    }
}
