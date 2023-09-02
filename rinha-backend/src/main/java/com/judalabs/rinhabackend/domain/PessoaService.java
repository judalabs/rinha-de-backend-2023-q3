package com.judalabs.rinhabackend.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.judalabs.rinhabackend.exception.UnprocessableEntityException;
import com.judalabs.rinhabackend.infra.Cacheable;
import com.judalabs.rinhabackend.infra.PessoaRepository;

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
    public PessoaDTO criar(PessoaDTO pessoa) {
        if(cacheService.existePorApelido(pessoa.apelido())) {
            throw new UnprocessableEntityException();
        }
        final PessoaDTO dto = toDto(pessoaRepository.save(toEntity(pessoa)));
        applicationEventPublisher.publishEvent(dto);
        return dto;
    }

    @Transactional(readOnly = true)
    public Optional<PessoaDTO> buscarPorId(UUID id) {
        final PessoaDTO pessoaCache = cacheService.existePorId(id);

        if(pessoaCache != null) {
            return Optional.of(pessoaCache);
        }

        return pessoaRepository.findById(id).map(this::toDto);
    }


    @Transactional(readOnly = true)
    public List<PessoaDTO> buscarPorTermo(String termo) {
        return pessoaRepository.buscarPorTermo(termo.toLowerCase())
                .stream().map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public long contar() {
        return pessoaRepository.count();
    }

    private Pessoa toEntity(PessoaDTO pessoa) {
        var stack = pessoa.stack() != null ? String.join(",", pessoa.stack()) : null;
        return new Pessoa(pessoa.apelido(), pessoa.nome(), pessoa.nascimento(), stack);
    }

    private PessoaDTO toDto(Pessoa entity) {
        List<String> stacks = entity.getStack() != null ? Arrays.stream(entity.getStack().split(",")).toList() : null;
        return new PessoaDTO(entity.getId(), entity.getApelido(), entity.getNome(), entity.getNascimento(), stacks);
    }
}
