package com.example.demo.job;

import com.example.demo.dto.EventDTO;
import com.example.demo.entitie.Client;
import com.example.demo.entitie.Event;
import com.example.demo.mapper.ClientMapper;
import com.example.demo.mapper.EventMapper;
import com.example.demo.service.ClientService;
import com.example.demo.service.EventService;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StepProcessor implements Tasklet {
    private EventService eventService;
    private ClientService clientService;
    private EventMapper eventMapper;
    private ClientMapper clientMapper;

    public StepProcessor(EventService eventService, ClientService clientService, EventMapper eventMapper, ClientMapper clientMapper) {
        this.eventService = eventService;
        this.clientService = clientService;
        this.eventMapper = eventMapper;
        this.clientMapper = clientMapper;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        ZoneOffset zoneOffset = ZoneId.systemDefault().getRules().getOffset(Instant.now());
        OffsetDateTime today = Calendar.getInstance().toInstant().atOffset(zoneOffset);
        List<EventDTO> pastEvents = new ArrayList<>();

        for (EventDTO event : eventService.getAll()) {
            if (today.compareTo(event.getDateTime().plusMinutes(event.getDuration().toNanoOfDay())) > 0)
                pastEvents.add(event);
        }
        List<Client> clients = clientService.getAll();
        clients.forEach(client -> {
            for (EventDTO event : pastEvents) {
                client.getEventsId().forEach(event1 -> {
                    if (event1.equals(event.getId())) {
                        Event eventEntity = eventMapper.mapToEntity(event);
                        client.setAccount(client.getAccount() - eventEntity.getFitnessRoom().getEventCost());
                        clientService.save(clientMapper.mapToDTO(client));
                    }
                });
            }
        });
        return RepeatStatus.FINISHED;
    }
}
