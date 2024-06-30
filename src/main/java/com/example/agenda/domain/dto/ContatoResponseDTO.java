package com.example.agenda.domain.dto;

import com.example.agenda.domain.Contato;

public record ContatoResponseDTO(Long id, String nome, String email, String celular, String telefone, String snAtivo, String snFavorito) {
    public ContatoResponseDTO(Contato contato) {
        this(contato.getId(), contato.getNome(), contato.getEmail(), contato.getCelular(), contato.getTelefone(), contato.getSnAtivo(), contato.getSnFavorito());
    }
}
