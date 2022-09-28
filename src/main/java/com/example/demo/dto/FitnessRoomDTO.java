package com.example.demo.dto;

import lombok.Data;

@Data
public class FitnessRoomDTO {
    private Integer id;
    private String name;
    private String address;
    private Integer size;
    private Double eventCost;
}
