package com.example.demo.entitie;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Coach {
    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String rewards;

    @OneToMany(mappedBy = "coach", fetch = FetchType.EAGER)
    private List<Event> events;
}
