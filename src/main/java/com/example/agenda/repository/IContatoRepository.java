package com.example.agenda.repository;

import com.example.agenda.domain.Contato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IContatoRepository extends JpaRepository<Contato, Long> {
    public Optional<Contato> findByCelular(String celular);
}
