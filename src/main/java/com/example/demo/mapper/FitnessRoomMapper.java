package com.example.demo.mapper;

import com.example.demo.dto.FitnessRoomDTO;
import com.example.demo.entitie.FitnessRoom;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FitnessRoomMapper extends BaseMapper<FitnessRoom, FitnessRoomDTO> {
}
