package com.judalabs.rinhabackend.infra;

import java.util.UUID;

import com.judalabs.rinhabackend.domain.PessoaDTO;

public interface Cacheable {
    boolean existePorApelido(String apelido);

    PessoaDTO existePorId(UUID id);

}
