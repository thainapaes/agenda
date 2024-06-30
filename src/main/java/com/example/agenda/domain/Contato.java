package com.example.agenda.domain;

import com.example.agenda.domain.dto.ContatoRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Table(name = "contato",schema = "desafio")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Contato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contato_id")
    private Long id;
    @Column(name = "contato_nome")
    private String nome;
    @Column(name = "contato_email")
    private String email;
    @Column(name = "contato_celular")
    private String celular;
    @Column(name = "contato_telefone")
    private String telefone;
    @Column(name = "contato_sn_favorito")
    private String snFavorito;
    @Column(name = "contato_sn_ativo")
    private String snAtivo;
    @Column(name = "contato_dh_cad")
    private Date dhCad;

    public Contato(ContatoRequestDTO data) {
        this.nome = data.nome();
        this.email = data.email();
        this.celular = data.celular();
        this.telefone = data.telefone();

        this.snAtivo = "S";
        this.snFavorito = "N";
        this.dhCad = new Date();
    }

}
