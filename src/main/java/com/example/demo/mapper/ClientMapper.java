package com.example.demo.mapper;

import com.example.demo.dto.ClientDTO;
import com.example.demo.entitie.Client;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientMapper extends BaseMapper<Client, ClientDTO> {

}
