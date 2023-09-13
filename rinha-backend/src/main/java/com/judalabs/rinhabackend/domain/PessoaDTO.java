package com.judalabs.rinhabackend.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.judalabs.rinhabackend.validation.PessoaValida;

@PessoaValida
public record PessoaDTO(
        UUID id,
        String nome,
        String apelido,
        LocalDate nascimento,
        List<String> stack) implements Serializable {

    public static PessoaDTO comRandomId(PessoaDTO pessoaDTO) {
        return new PessoaDTO(UUID.randomUUID(), pessoaDTO.nome, pessoaDTO.apelido, pessoaDTO.nascimento, pessoaDTO.stack);

    }

    public static String getStackString(PessoaDTO pessoa) {
        return pessoa.stack() != null ? String.join(",", pessoa.stack()) : null;
    }
}
