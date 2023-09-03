package com.judalabs.rinhabackend.infra;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;

import com.judalabs.rinhabackend.domain.PessoaDTO;

import reactor.core.publisher.Mono;

@Service
public class RedisService implements Cacheable {

    private final ReactiveRedisTemplate<UUID, PessoaDTO> redisFindOne;
    private final ReactiveRedisTemplate<String, Boolean> redisExistsApelido;

    public RedisService(
            @Qualifier("redisFindOne") ReactiveRedisTemplate<UUID, PessoaDTO> redisFindOne,
            @Qualifier("redisExistsApelido") ReactiveRedisTemplate<String, Boolean> redisExistsApelido) {
        this.redisFindOne = redisFindOne;
        this.redisExistsApelido = redisExistsApelido;
    }

    @Override
    public Mono<Boolean> existePorApelido(String apelido) {
        return redisExistsApelido.opsForValue().get(apelido);
    }

    @Override
    public Mono<PessoaDTO> existePorId(UUID id) {
        return redisFindOne.opsForValue().get(id);
    }

    @EventListener
    public void atualizacaoDeCacheListener(PessoaDTO pessoaDTO) {
        redisFindOne.opsForValue().set(pessoaDTO.getId(), pessoaDTO);
        redisExistsApelido.opsForValue().set(pessoaDTO.getApelido(), true);
    }
}
