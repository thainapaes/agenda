package com.example.agenda.repository;

import com.example.agenda.domain.Contato;
import com.example.agenda.domain.dto.ContatoRequestDTO;
import com.example.agenda.domain.dto.ContatoResponseDTO;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class IContatoRepositoryTest {

    @Autowired
    IContatoRepository contatoRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get Contato successfully from DB")
    void findByCelularSuccess() {
        String celular = "81985214785";
        ContatoRequestDTO data = new ContatoRequestDTO("Thaina", "thaina@test.br", celular, "");
        this.createContato(data);

        Optional<Contato> result = this.contatoRepository.findByCelular(celular);
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not get Contato from DB when contato not exists")
    void findByCelularFailed() {
        String celular = "81985214785";

        Optional<Contato> result = this.contatoRepository.findByCelular(celular);
        assertThat(result.isEmpty()).isTrue();
    }

    private Contato createContato(ContatoRequestDTO data){
        Contato newContato = new Contato(data);
        this.entityManager.persist(newContato);
        return newContato;
    }
}