package com.example.demo.controller;

import com.example.demo.dto.FitnessRoomDTO;
import com.example.demo.entitie.FitnessRoom;
import com.example.demo.service.FitnessRoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/rooms")
public class FitnessRoomController {
    public FitnessRoomController(FitnessRoomService fitnessRoomService) {
        this.fitnessRoomService = fitnessRoomService;
    }

    private final FitnessRoomService fitnessRoomService;

    @GetMapping(value = {"id"})
    public ResponseEntity<FitnessRoom> getFitnessRoom(@PathVariable Integer fitnessRoomId) {
        if (fitnessRoomId == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        FitnessRoom fitnessRoom = this.fitnessRoomService.getById(fitnessRoomId);
        if (fitnessRoom == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(fitnessRoom, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<FitnessRoomDTO> createFitnessRoom(@RequestBody @Valid FitnessRoomDTO fitnessRoomDTO) {
        if (fitnessRoomDTO == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        this.fitnessRoomService.save(fitnessRoomDTO);
        return new ResponseEntity<>(fitnessRoomDTO, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<FitnessRoomDTO> updateFitnessRoom(@RequestBody @Valid FitnessRoomDTO fitnessRoomDTO) {
        if (fitnessRoomDTO == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        this.fitnessRoomService.save(fitnessRoomDTO);
        return new ResponseEntity<>(fitnessRoomDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<FitnessRoom> deleteFitnessRoom(@PathVariable("id") Integer id) {
        FitnessRoom fitnessRoom = this.fitnessRoomService.getById(id);
        if (fitnessRoom == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        this.fitnessRoomService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<FitnessRoom>> getAllFitnessRoom() {
        List<FitnessRoom> fitnessRooms = this.fitnessRoomService.getAll();
        if (fitnessRooms.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(fitnessRooms, HttpStatus.OK);
    }
}
