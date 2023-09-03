package com.judalabs.rinhabackend.config;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.judalabs.rinhabackend.domain.PessoaDTO;

@Configuration
public class RedisConfig {

    @Bean
    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
        return new LettuceConnectionFactory("localhost", 6553);
    }


    @Bean
    @Qualifier("redisFindOne")
    public ReactiveRedisTemplate<UUID, PessoaDTO> redisFindOne(ReactiveRedisConnectionFactory redisConnectionFactory) {
        Jackson2JsonRedisSerializer<PessoaDTO> valueSerializer =
                new Jackson2JsonRedisSerializer<>(PessoaDTO.class);
        RedisSerializationContext.RedisSerializationContextBuilder<UUID, PessoaDTO> builder =
                RedisSerializationContext.newSerializationContext(new UUIDRedisSerializer());
        RedisSerializationContext<UUID, PessoaDTO> context = builder.value(valueSerializer).build();
        return new ReactiveRedisTemplate<>(redisConnectionFactory, context);
    }


    @Bean
    @Qualifier("redisExistsApelido")
    public ReactiveRedisTemplate<String, Boolean> redisExistsApelido(ReactiveRedisConnectionFactory redisConnectionFactory) {
        Jackson2JsonRedisSerializer<Boolean> valueSerializer =
                new Jackson2JsonRedisSerializer<>(Boolean.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, Boolean> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, Boolean> context = builder.value(valueSerializer).build();
        return new ReactiveRedisTemplate<>(redisConnectionFactory, context);
    }
}

