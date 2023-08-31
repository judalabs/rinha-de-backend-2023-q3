package com.judalabs.rinhabackend.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.judalabs.rinhabackend.exception.UnprocessableEntityException;
import com.judalabs.rinhabackend.infra.PessoaRepository;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public PessoaDTO criar(PessoaDTO pessoa) {
        try {
            return toDto(pessoaRepository.save(toEntity(pessoa)));
        } catch (DataIntegrityViolationException e) {
            throw new UnprocessableEntityException();
        }
    }

    public Optional<PessoaDTO> buscarPorId(UUID id) {
        return pessoaRepository.findById(id).map(this::toDto);
    }


    public List<PessoaDTO> buscarPorTermo(String termo) {
        return pessoaRepository.buscarPorTermo(termo)
                .stream().map(this::toDto)
                .toList();
    }

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
