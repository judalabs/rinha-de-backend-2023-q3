package com.judalabs.rinhabackend.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.judalabs.rinhabackend.validation.Pessoa;

@Pessoa
public record PessoaDTO(
        UUID id,
        String nome,
        String apelido,
        LocalDate nascimento,
        List<String> stack) implements Serializable {
}
