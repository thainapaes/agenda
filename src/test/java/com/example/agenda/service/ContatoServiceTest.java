package com.example.agenda.service;

import com.example.agenda.domain.Contato;
import com.example.agenda.domain.dto.ContatoAtivoPatchDTO;
import com.example.agenda.domain.dto.ContatoFavPatchDTO;
import com.example.agenda.domain.dto.ContatoRequestDTO;
import com.example.agenda.domain.dto.ContatoResponseDTO;
import com.example.agenda.mapper.ContatoMapper;
import com.example.agenda.repository.IContatoRepository;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.h2.store.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class ContatoServiceTest {

    @Mock
    private IContatoRepository repository;

    @InjectMocks
    private ContatoService contatoService;

    @Mock
    private ContatoMapper contatoMapper;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create the Contato successfully when everything is ok")
    void saveContactSuccess() {
        ContatoRequestDTO requestDTO = new ContatoRequestDTO(
                "Thaina", "thaina@test.br", "81985214785", "81999999999");

        Contato contato = new Contato();
        contato.setNome(requestDTO.nome());
        contato.setEmail(requestDTO.email());
        contato.setCelular(requestDTO.celular());
        contato.setTelefone(requestDTO.telefone());
        contato.setSnAtivo("S");
        contato.setSnFavorito("N");
        contato.setDhCad(new Date());

        when(repository.findByCelular(requestDTO.celular())).thenReturn(Optional.empty());
        when(repository.save(any(Contato.class))).thenReturn(contato);

        Contato result = this.contatoService.saveContact(requestDTO);
        assertNotNull(result);
    }
    

    @Test
    @DisplayName("Should throw exception when celular already exists")
    void saveContactFailedDueToExistingCelular() {
        ContatoRequestDTO requestDTO = new ContatoRequestDTO(
                "Thaina", "thaina@test.br", "81985214785", "81999999999");

        Contato contato = new Contato();
        contato.setNome(requestDTO.nome());
        contato.setEmail(requestDTO.email());
        contato.setCelular(requestDTO.celular());
        contato.setTelefone(requestDTO.telefone());
        contato.setSnAtivo("S");
        contato.setSnFavorito("N");
        contato.setDhCad(new Date());

        when(repository.findByCelular(requestDTO.celular())).thenReturn(Optional.of(contato));

        assertThrows(IllegalArgumentException.class, () -> {
            this.contatoService.saveContact(requestDTO);
        });
    }

    @Test
    @DisplayName("Should return null when any required field is empty")
    void saveContactFailedDueToEmptyField() {
        ContatoRequestDTO requestDTOWithEmptyNome = new ContatoRequestDTO(
                "", "thaina@test.br", "81985214785", "81999999999");

        ContatoRequestDTO requestDTOWithEmptyEmail = new ContatoRequestDTO(
                "Thaina", "", "81985214785", "81999999999");

        ContatoRequestDTO requestDTOWithEmptyTelefone = new ContatoRequestDTO(
                "Thaina", "thaina@test.br", "81985214785", "");

        when(repository.findByCelular("81985214785")).thenReturn(Optional.empty());

        Exception thrownNome = assertThrows(IllegalArgumentException.class, () -> {
            this.contatoService.saveContact(requestDTOWithEmptyNome);
        });
        assertEquals("O campo nome deve ser preenchido", thrownNome.getMessage());

        Exception thrownEmail = assertThrows(IllegalArgumentException.class, () -> {
            this.contatoService.saveContact(requestDTOWithEmptyEmail);
        });
        assertEquals("O campo email deve ser preenchido", thrownEmail.getMessage());

        Exception thrownTelefone = assertThrows(IllegalArgumentException.class, () -> {
            this.contatoService.saveContact(requestDTOWithEmptyTelefone);
        });
        assertEquals("O campo telefone deve ser preenchido", thrownTelefone.getMessage());

    }

    @Test
    @DisplayName("Should update the Contato successfully when everything is ok")
    void updateTheContactWithSuccess() {
        Contato contato = new Contato(1L, "Thaina", "thaina@test.br", "81985214785", "", "N", "S", new Date());
        contato.setEmail("thai@test.br");
        contato.setTelefone("8134612658");
        ContatoRequestDTO requestDTO = new ContatoRequestDTO(
                "Thaina", "thai@test.br","81985214785", "8134612658");
        ContatoResponseDTO responseDTO = new ContatoResponseDTO(
                1L, "Thaina", "thai@test.br","81985214785", "8134612658", "S", "N");

        when(repository.findByCelular(contato.getCelular())).thenReturn(Optional.of(contato));
        when(repository.save(contato)).thenReturn(contato);
        when(contatoMapper.toDto(contato)).thenReturn(responseDTO);

        ContatoResponseDTO result = this.contatoService.updateContact(requestDTO.celular(), requestDTO);
        assertEquals(result.email(), "thai@test.br");
        assertEquals(result.telefone(), "8134612658");
    }

    @Test
    @DisplayName("Should favorite the Contato on the list")
    void changeTheFavStatusToFavoriteSuccess() {
        Contato contato = new Contato(1L, "Thaina", "thaina@test.br", "81985214785", "", "N", "S", new Date());
        contato.setSnFavorito("S");

        ContatoFavPatchDTO patchDto = new ContatoFavPatchDTO(contato.getCelular());
        ContatoResponseDTO responseDTO = new ContatoResponseDTO(
                1L, "Thaina", "thai@test.br","81985214785", "8134612658", "S", "S");

        when(repository.findByCelular(contato.getCelular())).thenReturn(Optional.of(contato));
        when(repository.save(contato)).thenReturn(contato);
        when(contatoMapper.toDto(contato)).thenReturn(responseDTO);

        ContatoResponseDTO result = this.contatoService.favStatusContact(patchDto);
        assertNotNull(result);
        assertEquals(result.snFavorito(), "S");

    }

    @Test
    @DisplayName("Should unactivated the Contato")
    void changeTheStatusToUnactivatedContact() {
        Contato contato = new Contato(1L, "Thaina", "thaina@test.br", "81985214785", "", "N", "S", new Date());
        contato.setSnAtivo("N");

        ContatoAtivoPatchDTO patchDto = new ContatoAtivoPatchDTO(contato.getCelular());
        ContatoResponseDTO responseDTO = new ContatoResponseDTO(
                1L, "Thaina", "thai@test.br","81985214785", "8134612658", "N", "N");

        when(repository.findByCelular(contato.getCelular())).thenReturn(Optional.of(contato));
        when(repository.save(contato)).thenReturn(contato);
        when(contatoMapper.toDto(contato)).thenReturn(responseDTO);

        ContatoResponseDTO result = this.contatoService.activeStatusContact(patchDto);
        assertNotNull(result);
        assertEquals(result.snAtivo(), "N");
    }

}