package com.example.demo.mapper;

import com.example.demo.dto.EventDTO;
import com.example.demo.entitie.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper extends BaseMapper<Event, EventDTO> {

    @Override
    @Mapping(source = "coach.id", target = "coachId")
    @Mapping(source = "fitnessRoom.id", target = "fitnessRoomId")

    EventDTO mapToDTO(Event event);

    @Override
    @Mapping(source = "coachId", target = "coach.id")
    @Mapping(source = "fitnessRoomId", target = "fitnessRoom.id")

    Event mapToEntity(EventDTO eventDTO);
}
