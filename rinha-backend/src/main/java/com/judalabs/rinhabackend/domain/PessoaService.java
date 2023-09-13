package com.judalabs.rinhabackend.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.judalabs.rinhabackend.exception.UnprocessableEntityException;
import com.judalabs.rinhabackend.infra.BatchInsertService;
import com.judalabs.rinhabackend.infra.Cacheable;
import com.judalabs.rinhabackend.infra.PessoaRepository;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final Cacheable cacheService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final BatchInsertService batchInsertService;

    public PessoaService(PessoaRepository pessoaRepository,
                         Cacheable cacheService,
                         ApplicationEventPublisher applicationEventPublisher,
                         BatchInsertService batchInsertService) {
        this.pessoaRepository = pessoaRepository;
        this.cacheService = cacheService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.batchInsertService = batchInsertService;
    }

    public PessoaDTO criar(PessoaDTO pessoaDTO) {
        if(cacheService.existePorApelido(pessoaDTO.apelido())) {
            throw new UnprocessableEntityException();
        }
        final PessoaDTO pessoa = cacheService.lazySave(pessoaDTO);

        batchInsertService.salvaSeEstaCheio(cacheService.getBatch());
        return pessoa;
    }

    public Optional<PessoaDTO> buscarPorId(UUID id) {
        final PessoaDTO pessoaCache = cacheService.existePorId(id);

        if(pessoaCache != null) {
            return Optional.of(pessoaCache);
        }

        return pessoaRepository.findById(id).map(this::toDto);
    }


    public List<PessoaDTO> buscarPorTermo(String termo) {
        return pessoaRepository.buscarPorTermo(termo.toLowerCase())
                .stream().map(this::toDto)
                .toList();
    }

    public long contar() {
        return pessoaRepository.count();
    }

    private Pessoa toEntity(PessoaDTO pessoa) {
        var stack = pessoa.stack() != null ? String.join(",", pessoa.stack()) : null;
        return new Pessoa(pessoa.nome(), pessoa.apelido(), pessoa.nascimento(), stack);
    }

    private PessoaDTO toDto(Pessoa entity) {
        List<String> stacks = entity.getStack() != null ? Arrays.stream(entity.getStack().split(",")).toList() : null;
        return new PessoaDTO(entity.getId(), entity.getNome(), entity.getApelido(), entity.getNascimento(), stacks);
    }
}
