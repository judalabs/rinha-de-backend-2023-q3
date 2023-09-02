package com.judalabs.rinhabackend.infra;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.judalabs.rinhabackend.domain.PessoaDTO;

@Service
public class RedisService implements Cacheable {

//    private final RedisTemplate<UUID, PessoaDTO> redisFindOne;
//    private final RedisTemplate<String, Boolean> redisExistsApelido;

//    public RedisService(
//            @Qualifier("redisFindOne") RedisTemplate<UUID, PessoaDTO> redisFindOne,
//            @Qualifier("redisExistsApelido") RedisTemplate<String, Boolean> redisExistsApelido) {
//        this.redisFindOne = redisFindOne;
//        this.redisExistsApelido = redisExistsApelido;
//    }

    @Override
    public boolean existePorApelido(String apelido) {
        return false;//redisExistsApelido.opsForValue().get(apelido) != null;
    }

    @Override
    public PessoaDTO existePorId(UUID id) {
        return null;//redisFindOne.opsForValue().get(id);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void atualizacaoDeCacheListener(PessoaDTO pessoaDTO) {
//        redisFindOne.opsForValue().set(pessoaDTO.id(), pessoaDTO);
//        redisExistsApelido.opsForValue().set(pessoaDTO.apelido(), true);
    }
}
