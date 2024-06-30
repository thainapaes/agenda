package com.example.agenda.mapper;

import com.example.agenda.domain.Contato;
import com.example.agenda.domain.dto.ContatoResponseDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-30T10:22:18-0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 21.0.3 (Oracle Corporation)"
)
@Component
public class ContatoMapperImpl implements ContatoMapper {

    @Override
    public ContatoResponseDTO toDto(Contato contato) {
        if ( contato == null ) {
            return null;
        }

        Long id = null;
        String nome = null;
        String email = null;
        String celular = null;
        String telefone = null;
        String snAtivo = null;
        String snFavorito = null;

        id = contato.getId();
        nome = contato.getNome();
        email = contato.getEmail();
        celular = contato.getCelular();
        telefone = contato.getTelefone();
        snAtivo = contato.getSnAtivo();
        snFavorito = contato.getSnFavorito();

        ContatoResponseDTO contatoResponseDTO = new ContatoResponseDTO( id, nome, email, celular, telefone, snAtivo, snFavorito );

        return contatoResponseDTO;
    }

    @Override
    public Contato toEntity(ContatoResponseDTO contatoResponseDTO) {
        if ( contatoResponseDTO == null ) {
            return null;
        }

        Contato contato = new Contato();

        contato.setId( contatoResponseDTO.id() );
        contato.setNome( contatoResponseDTO.nome() );
        contato.setEmail( contatoResponseDTO.email() );
        contato.setCelular( contatoResponseDTO.celular() );
        contato.setTelefone( contatoResponseDTO.telefone() );
        contato.setSnFavorito( contatoResponseDTO.snFavorito() );
        contato.setSnAtivo( contatoResponseDTO.snAtivo() );

        return contato;
    }
}
