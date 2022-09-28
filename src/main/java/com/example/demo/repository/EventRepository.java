package com.example.demo.repository;

import com.example.demo.entitie.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

    @Query(value = "SELECT * FROM event WHERE date_time > :today", nativeQuery = true)
    List<Event> getAllFutureEvents(@Param("today") OffsetDateTime today);

    List<Event> findByFitnessRoomId(Integer fitnessRoomId);

    @Query(value = "SELECT * FROM event WHERE date_time > :today AND coach_id = :coachId", nativeQuery = true)
    List<Event> getAllFutureEventsByCoach(@Param("today") OffsetDateTime today, @Param("coachId") Integer coachId);

    @Query(value = "SELECT * FROM event JOIN client_event ON id = event_id WHERE client_id = :clientId", nativeQuery = true)
    List<Event> findByClient(@Param("clientId") Integer clientId);
}
