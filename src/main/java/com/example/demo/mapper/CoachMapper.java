package com.example.demo.mapper;

import com.example.demo.dto.CoachDTO;
import com.example.demo.entitie.Coach;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CoachMapper extends BaseMapper<Coach, CoachDTO> {

    @Override
    CoachDTO mapToDTO(Coach coach);

    @Override
    Coach mapToEntity(CoachDTO coachDTO);
}
