package com.example.demo.service.impl;

import com.example.demo.DVORApplication;
import com.example.demo.dto.FitnessRoomDTO;
import com.example.demo.entitie.FitnessRoom;
import com.example.demo.mapper.FitnessRoomMapper;
import com.example.demo.repository.FitnessRoomRepository;
import com.example.demo.service.FitnessRoomService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.logging.Logger;

@Service
public class FitnessRoomServiceImpl implements FitnessRoomService {
    private static final Logger log = Logger.getLogger(DVORApplication.class.getName());
    private final FitnessRoomMapper fitnessRoomMapper;
    private final FitnessRoomRepository fitnessRoomRepository;

    public FitnessRoomServiceImpl(FitnessRoomMapper fitnessRoomMapper, FitnessRoomRepository fitnessRoomRepository) {
        this.fitnessRoomMapper = fitnessRoomMapper;
        this.fitnessRoomRepository = fitnessRoomRepository;
    }

    @Override
    public FitnessRoom getById(Integer id) {
        return fitnessRoomRepository.findById(id)
                .orElseThrow(() -> {
                    log.warning("Fitness room with id " + id + " not found");
                    return new EntityNotFoundException("Fitness room with id " + id);
                });
    }

    @Override
    public FitnessRoomDTO save(FitnessRoomDTO fitnessRoomDTO) {
        FitnessRoom fitnessRoom = fitnessRoomMapper.mapToEntity(fitnessRoomDTO);
        FitnessRoom savedFitnessRoom = fitnessRoomRepository.save(fitnessRoom);
        log.info("Fitness room is saved");

        return fitnessRoomMapper.mapToDTO(savedFitnessRoom);
    }

    @Override
    public void delete(Integer id) {
        FitnessRoom fitnessRoom = fitnessRoomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fitness room with id " + id));
        fitnessRoomRepository.delete(fitnessRoom);
    }

    @Override
    public List<FitnessRoom> getAll() {
        return fitnessRoomRepository.findAll();
    }
}
