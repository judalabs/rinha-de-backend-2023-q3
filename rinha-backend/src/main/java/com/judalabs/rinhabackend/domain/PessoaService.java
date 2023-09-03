package com.judalabs.rinhabackend.domain;

import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.judalabs.rinhabackend.exception.UnprocessableEntityException;
import com.judalabs.rinhabackend.infra.Cacheable;
import com.judalabs.rinhabackend.infra.PessoaRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final Cacheable cacheService;
    private final ApplicationEventPublisher applicationEventPublisher;

    public PessoaService(PessoaRepository pessoaRepository,
                         Cacheable cacheService,
                         ApplicationEventPublisher applicationEventPublisher) {
        this.pessoaRepository = pessoaRepository;
        this.cacheService = cacheService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Transactional
    public Mono<PessoaDTO> criar(PessoaDTO pessoa) {
        return cacheService.existePorApelido(pessoa.getApelido())
                .flatMap(e -> Mono.<PessoaDTO>error(new UnprocessableEntityException()))
                .switchIfEmpty(buscarSalvandoNoBanco(pessoa));
    }

    private Mono<PessoaDTO> buscarSalvandoNoBanco(PessoaDTO pessoa) {
        final Pessoa entity = toEntity(pessoa);
        return pessoaRepository.save(entity).map(e -> {
            final PessoaDTO dto = PessoaDTO.toDto(e);
            applicationEventPublisher.publishEvent(new PessoaSalvaListener(dto));
            return dto;
        });

    }

    public Mono<PessoaDTO> buscarPorId(UUID id) {
        return cacheService.existePorId(id)
                .switchIfEmpty(pessoaRepository.findById(id).map(PessoaDTO::toDto));
    }


    public Flux<PessoaDTO> buscarPorTermo(String termo) {
        return pessoaRepository.buscarPorTermo(termo.toLowerCase())
                .map(PessoaDTO::toDto);
    }

    public Mono<Long> contar() {
        return pessoaRepository.count();
    }

    private Pessoa toEntity(PessoaDTO pessoa) {
        var stack = pessoa.getStack() != null ? String.join(",", pessoa.getStack()) : null;
        return new Pessoa(pessoa.getNome(), pessoa.getApelido(), pessoa.getNascimento(), stack);
    }


}
