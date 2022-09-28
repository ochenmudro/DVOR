package com.example.demo.entitie;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Event {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;
    private OffsetDateTime dateTime;
    private LocalTime duration;

    public void setDateTime(OffsetDateTime dateTime) {
        this.dateTime = dateTime.withOffsetSameLocal(ZoneOffset.of("+03:00"));
    }

    @ManyToMany(mappedBy = "events",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Client> clients;
    @ManyToOne
    private FitnessRoom fitnessRoom;

    @ManyToOne
    @JoinColumn(name = "coach_id")
    private Coach coach;

    public void addClient(Client client){
        this.clients.add(client);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Event event = (Event) o;
        return id != null && Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public List<Integer> getClientsId(){
        List<Integer> clientsId = new ArrayList<>();
        for(Client client:this.clients)
            clientsId.add(client.getId());
        return clientsId;
    }
}
