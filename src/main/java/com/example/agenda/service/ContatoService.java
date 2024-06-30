package com.example.agenda.service;

import com.example.agenda.domain.Contato;
import com.example.agenda.domain.dto.ContatoAtivoPatchDTO;
import com.example.agenda.domain.dto.ContatoFavPatchDTO;
import com.example.agenda.domain.dto.ContatoRequestDTO;
import com.example.agenda.domain.dto.ContatoResponseDTO;
import com.example.agenda.mapper.ContatoMapper;
import com.example.agenda.repository.IContatoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContatoService {

    @Autowired
    private IContatoRepository repository;

    @Autowired
    private ContatoMapper contatoMapper;

    public List<Contato> allContacts() {
        return repository.findAll();
    }

    public ContatoResponseDTO getContact(String celular) {
        Contato contato = repository.findByCelular(celular)
                .orElseThrow(() -> new IllegalArgumentException("Contato com esse celular já existe"));
        return contatoMapper.toDto(repository.save(contato));
    }

    public Contato saveContact(ContatoRequestDTO request) {
        Contato response = null;
        if (!request.celular().isEmpty()) {
            Optional<Contato> jaExiste = repository.findByCelular(request.celular());
            if(jaExiste.isPresent()) {
                throw new IllegalArgumentException("Contato com esse celular já existe");
            } else {
                Contato contatoAux = new Contato();
                if (request.nome().isEmpty()) {
                    throw new IllegalArgumentException("O campo nome deve ser preenchido");
                } else if (request.email().isEmpty()) {
                    throw new IllegalArgumentException("O campo email deve ser preenchido");
                } else if (request.telefone().isEmpty()) {
                    throw new IllegalArgumentException("O campo telefone deve ser preenchido");
                }

                contatoAux.setNome(request.nome());
                contatoAux.setEmail(request.email());
                contatoAux.setCelular(request.celular());
                contatoAux.setTelefone(request.telefone());

                contatoAux.setSnAtivo("S");
                contatoAux.setSnFavorito("N");
                contatoAux.setDhCad(new Date());

                response = repository.save(contatoAux);

            }
        }
        return response;
    }

    public ContatoResponseDTO updateContact(String celular, ContatoRequestDTO request){
        Optional<Contato> existed = repository.findByCelular(celular);
        Contato c = null;
        ContatoResponseDTO responseDTO = null;
        if (existed.isPresent()) {
            c = existed.get();

            if(!request.nome().isEmpty() && !c.getNome().equals(request.nome())) {
                c.setNome(request.nome());
            }

            if(!request.email().isEmpty() && !c.getEmail().equals(request.email())) {
                c.setEmail(request.email());
            }

            if(!request.celular().isEmpty() && !c.getCelular().equals(request.celular())) {
                Optional<Contato> celularExisted = repository.findByCelular(request.celular());
                if(celularExisted.isEmpty()) {
                    c.setCelular(request.celular());
                }
            }

            if(!request.telefone().isEmpty() && !c.getTelefone().equals(request.telefone())) {
                c.setTelefone(request.telefone());
            }
            responseDTO = contatoMapper.toDto(repository.save(c));
        }

        return responseDTO;
    }

    public ContatoResponseDTO favStatusContact(ContatoFavPatchDTO favDto){
        Optional<Contato> contato = repository.findByCelular(favDto.celular());
        if(contato.isPresent()) {
            Contato c = contato.get();
            if(c.getSnFavorito().equals("N")) {
                c.setSnFavorito("S");
            } else {
                c.setSnFavorito("N");
            }
            return contatoMapper.toDto(repository.save(c));
        }
        return null;
    }

    public ContatoResponseDTO activeStatusContact(ContatoAtivoPatchDTO ativoDto) {
        Optional<Contato> contato = repository.findByCelular(ativoDto.celular());
        if(contato.isPresent()) {
            Contato c = contato.get();
            if(c.getSnAtivo().equals("S")) {
                c.setSnAtivo("N");
            } else {
                c.setSnAtivo("S");
            }
            return contatoMapper.toDto(repository.save(c));
        }
        return null;
    }
}
