package com.judalabs.rinhabackend.config;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.judalabs.rinhabackend.domain.PessoaDTO;

@Configuration
public class RedisConfig {

    @Bean
    @Qualifier("redisFindOne")
    public RedisTemplate<UUID, PessoaDTO> redisFindOne(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<UUID, PessoaDTO> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    @Qualifier("redisExistsApelido")
    public RedisTemplate<String, Boolean> redisExistsApelido(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Boolean> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}

