package com.example.demo.entitie;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class FitnessRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;
    private String name;
    private String address;
    private Integer size;
    private Double eventCost;
}
