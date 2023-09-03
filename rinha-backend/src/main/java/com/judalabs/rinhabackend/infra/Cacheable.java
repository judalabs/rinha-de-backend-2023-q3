package com.judalabs.rinhabackend.infra;

import java.util.UUID;

import com.judalabs.rinhabackend.domain.PessoaDTO;

import reactor.core.publisher.Mono;

public interface Cacheable {

    Mono<Boolean> existePorApelido(String apelido);

    Mono<PessoaDTO> existePorId(UUID id);

}
