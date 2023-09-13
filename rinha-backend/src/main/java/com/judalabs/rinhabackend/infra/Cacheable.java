package com.judalabs.rinhabackend.infra;

import java.util.List;
import java.util.UUID;

import org.springframework.data.redis.core.ValueOperations;

import com.judalabs.rinhabackend.domain.PessoaDTO;

public interface Cacheable {
    boolean existePorApelido(String apelido);

    PessoaDTO existePorId(UUID id);

    void atualizacaoDeCacheListener(PessoaDTO dto);


    PessoaDTO lazySave(PessoaDTO pessoaDTO);

    ValueOperations<Boolean, List<PessoaDTO>> getBatch();
}
