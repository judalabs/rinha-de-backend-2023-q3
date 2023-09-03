package com.judalabs.rinhabackend.config;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public class UUIDRedisSerializer implements RedisSerializer <UUID> {

    @Override
    public byte[] serialize(UUID uuid) throws SerializationException {
        return uuid.toString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public UUID deserialize(byte[] bytes) throws SerializationException {
        return UUID.fromString(new String(bytes, StandardCharsets.UTF_8));
    }
}
