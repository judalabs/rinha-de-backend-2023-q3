package com.judalabs.rinhabackend.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.judalabs.rinhabackend.validation.PessoaValida;

@PessoaValida
public class PessoaDTO implements Serializable {

    private UUID id;
    private String nome;
    private String apelido;
    private LocalDate nascimento;
    private List<String> stack;

    public PessoaDTO() {
    }

    public PessoaDTO(UUID id, String nome, String apelido, LocalDate nascimento, List<String> stack) {
        this.id = id;
        this.nome = nome;
        this.apelido = apelido;
        this.nascimento = nascimento;
        this.stack = stack;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public List<String> getStack() {
        return stack;
    }

    public void setStack(List<String> stack) {
        this.stack = stack;
    }

    public static PessoaDTO toDto(Pessoa entity) {
        List<String> stacks = entity.getStack() != null ? Arrays.stream(entity.getStack().split(",")).toList() : null;
        return new PessoaDTO(entity.getId(), entity.getNome(), entity.getApelido(), entity.getNascimento(), stacks);
    }

    public static Pessoa toEntity(PessoaDTO pessoa) {
        var stack = pessoa.getStack() != null ? String.join(",", pessoa.getStack()) : null;
        return new Pessoa(pessoa.getNome(), pessoa.getApelido(), pessoa.getNascimento(), stack);
    }
}
