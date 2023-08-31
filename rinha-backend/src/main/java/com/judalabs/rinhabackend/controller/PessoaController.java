package com.judalabs.rinhabackend.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.judalabs.rinhabackend.domain.PessoaDTO;
import com.judalabs.rinhabackend.domain.PessoaService;
import com.judalabs.rinhabackend.exception.NotFoundException;

import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping("/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping
    public ResponseEntity<PessoaDTO> criar(@RequestBody @Valid PessoaDTO pessoa) {
        PessoaDTO salvo = pessoaService.criar(pessoa);

        final URI uri = getUri(salvo);
        return ResponseEntity.created(uri).body(salvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaDTO> buscarPorId(@PathVariable UUID id) {
        return pessoaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(NotFoundException::new);
    }

    @GetMapping
    public ResponseEntity<List<PessoaDTO>> buscarPorTermo(@RequestParam(value = "t") String termo) {
        return ResponseEntity.ok(pessoaService.buscarPorTermo(termo));
    }

    @GetMapping("/contagem-pessoas")
    public ResponseEntity<Long> countPeople() {
        return ResponseEntity.ok(pessoaService.contar());
    }

    private URI getUri(PessoaDTO salvo) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(salvo.id())
                .toUri();
    }
}
