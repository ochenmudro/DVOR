package com.example.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class ClientDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private Integer age;
    private Double account;
    private List<EventDTO> events;
}
