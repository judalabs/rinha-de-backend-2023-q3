package com.judalabs.rinhabackend.domain;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Table(name = "pessoa")
public class Pessoa implements Persistable<UUID> {

    @Id
    @Column(value = "id")
    private UUID id;

    @Column(value = "nome")
    private String nome;

    @Column(value = "apelido")
    private String apelido;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(value = "nascimento")
    private LocalDate nascimento;

    @Column(value = "stack")
    private String stack;

    public Pessoa() {
    }

    public Pessoa(String nome, String apelido, LocalDate nascimento, String stack) {
        this.nome = nome;
        this.apelido = apelido;
        this.nascimento = nascimento;
        this.stack = stack;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return id == null;
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

    public String getStack() {
        return stack;
    }

    public void setStack(String stack) {
        this.stack = stack;
    }
}
