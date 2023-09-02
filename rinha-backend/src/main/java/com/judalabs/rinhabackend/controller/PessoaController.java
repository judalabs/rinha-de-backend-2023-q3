package com.judalabs.rinhabackend.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.judalabs.rinhabackend.domain.PessoaDTO;
import com.judalabs.rinhabackend.domain.PessoaService;

import jakarta.validation.Valid;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping("/pessoas")
    public Mono<ResponseEntity<PessoaDTO>> criar(@Valid @RequestBody PessoaDTO pessoaDTO) {
        return pessoaService.criar(pessoaDTO).map(p -> {
            final URI uri = getUri(p);
            return ResponseEntity.created(uri).body(p);
        });
    }

    @GetMapping("/pessoas/{id}")
    public Mono<ResponseEntity<PessoaDTO>> buscarPorId(@PathVariable UUID id) {
        return pessoaService.buscarPorId(id).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/pessoas")
    public Flux<ResponseEntity<PessoaDTO>> buscarPorTermo(@RequestParam("t") String termo) {
        return pessoaService.buscarPorTermo(termo)
                .map(ResponseEntity::ok);
    }

    @GetMapping(value = "/contagem-pessoas", produces = "application/json")
    public Mono<ResponseEntity<Long>> contarPessoas() {
        return pessoaService.contar().map(ResponseEntity::ok);
    }

    private URI getUri(PessoaDTO salvo) {
        return UriComponentsBuilder
                .fromUriString("/pessoas")
                .path("/{id}")
                .buildAndExpand(salvo.id())
                .toUri();
    }
}
