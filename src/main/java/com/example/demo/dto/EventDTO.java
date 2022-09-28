package com.example.demo.dto;

import lombok.Data;

import java.time.LocalTime;
import java.time.OffsetDateTime;

@Data
public class EventDTO {

    private Integer id;
    private OffsetDateTime dateTime;
    private LocalTime duration;
    private Integer fitnessRoomId;
    private Integer coachId;
}
