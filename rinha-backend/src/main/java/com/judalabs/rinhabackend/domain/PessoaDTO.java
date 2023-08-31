package com.judalabs.rinhabackend.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import com.judalabs.rinhabackend.validation.Stack;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PessoaDTO (
        UUID id,
        @NotEmpty @Length(max = 100) String nome,
        @NotEmpty @Length(max = 32) String apelido,
        @NotNull LocalDate nascimento,
        @Stack List<String> stack) {
}
