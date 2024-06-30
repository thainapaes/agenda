package com.example.agenda.mapper;

import com.example.agenda.domain.Contato;
import com.example.agenda.domain.dto.ContatoResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContatoMapper {
    ContatoResponseDTO toDto(Contato contato);
    Contato toEntity(ContatoResponseDTO contatoResponseDTO);
}
