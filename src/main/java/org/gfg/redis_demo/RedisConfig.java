package org.gfg.redis_demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public LettuceConnectionFactory lettuceRedisConnectionFactory(){
        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration("redis-17574.c16.us-east-1-3.ec2.redns.redis-cloud.com", 17574);
        redisStandaloneConfiguration.setPassword("fDSacTmgQv55dkEI8GxftdfNO3pNoWR1");
        LettuceConnectionFactory lettuceRedisConnectionFactory= new LettuceConnectionFactory(redisStandaloneConfiguration);
        return lettuceRedisConnectionFactory;
    }


    @Bean
    public RedisTemplate<String, Object> getRedisTemplate(){
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setHashKeySerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setConnectionFactory(lettuceRedisConnectionFactory());
        return redisTemplate;
    }

}
