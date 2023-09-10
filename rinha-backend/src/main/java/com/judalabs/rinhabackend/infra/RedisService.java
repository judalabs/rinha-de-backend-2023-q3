package com.judalabs.rinhabackend.infra;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.judalabs.rinhabackend.domain.PessoaDTO;

@Service
public class RedisService implements Cacheable {

    private final RedisTemplate<UUID, PessoaDTO> redisFindOne;
    private final RedisTemplate<String, Boolean> redisExistsApelido;

    public RedisService(
            @Qualifier("redisFindOne") RedisTemplate<UUID, PessoaDTO> redisFindOne,
            @Qualifier("redisExistsApelido") RedisTemplate<String, Boolean> redisExistsApelido) {
        this.redisFindOne = redisFindOne;
        this.redisExistsApelido = redisExistsApelido;
    }

    @Override
    public boolean existePorApelido(String apelido) {
        return redisExistsApelido.opsForValue().get(apelido) != null;
    }

    @Override
    public PessoaDTO existePorId(UUID id) {
        return redisFindOne.opsForValue().get(id);
    }

    public void atualizacaoDeCacheListener(PessoaDTO pessoaDTO) {
        redisFindOne.opsForValue().set(pessoaDTO.id(), pessoaDTO);
        redisExistsApelido.opsForValue().set(pessoaDTO.apelido(), true);
    }
}
