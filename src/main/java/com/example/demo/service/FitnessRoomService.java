package com.example.demo.service;

import com.example.demo.dto.FitnessRoomDTO;
import com.example.demo.entitie.FitnessRoom;

import java.util.List;

public interface FitnessRoomService {
    FitnessRoom getById(Integer id);

    FitnessRoomDTO save(FitnessRoomDTO fitnessRoomDTO);

    void delete(Integer id);

    List<FitnessRoom> getAll();
}
