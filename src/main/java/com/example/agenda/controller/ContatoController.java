package com.example.agenda.controller;

import com.example.agenda.domain.Contato;
import com.example.agenda.domain.dto.ContatoAtivoPatchDTO;
import com.example.agenda.domain.dto.ContatoFavPatchDTO;
import com.example.agenda.domain.dto.ContatoRequestDTO;
import com.example.agenda.domain.dto.ContatoResponseDTO;
import com.example.agenda.repository.IContatoRepository;
import com.example.agenda.service.ContatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/agenda", produces = "application/json")
public class ContatoController {

    @Autowired
    private IContatoRepository repository;

    private ContatoService service;
    public ContatoController(ContatoService service) {
        this.service = service;
    }

    @GetMapping
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<ContatoResponseDTO> allContact() {
        return service.allContacts().stream().map(ContatoResponseDTO:: new).toList();
    }

    @GetMapping("/{celular}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ContatoResponseDTO getContact(@PathVariable String celular) {
        return service.getContact(celular);
    }

    @PostMapping
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public void saveContact(@RequestBody ContatoRequestDTO request) {
        Contato c = service.saveContact(request);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PatchMapping("/{celular}")
    public ContatoResponseDTO updateContact(@PathVariable String celular, @RequestBody ContatoRequestDTO request) {
        return service.updateContact(celular, request);

    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PatchMapping("/ativar")
    public ContatoResponseDTO activeContact(@RequestBody ContatoAtivoPatchDTO request) {
        return service.activeStatusContact(request);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PatchMapping("/favorito")
    public ContatoResponseDTO favContact(@RequestBody ContatoFavPatchDTO request) {
        return service.favStatusContact(request);

    }

}
