package com.learn.springbootcrud.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration.LettuceClientConfigurationBuilder;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import io.lettuce.core.ClientOptions;

/**
 * 手动配置Lettuce连接，避免自动配置的localhost缓存问题
 */
@Configuration // 一定要注意别写成了Configurable了
@EnableCaching  // 启用SpringCache注解
public class RedisConfig {

    // 直接从配置读取，优先级最高
    @Value("${spring.redis.host}")
    private String redisHost;
    @Value("${spring.redis.port}")
    private Integer redisPort;
    @Value("${spring.redis.password}")
    private String redisPassword;
    @Value("${spring.redis.database}")
    private Integer redisDatabase;
    @Value("${spring.redis.username:default}")
    private String redisUsername;

    // 手动创建Lettuce连接工厂（核心：强制指定IP/端口）
    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        // 1. 单机Redis配置
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(redisHost);
        redisConfig.setPort(redisPort);
        // redisConfig.setPassword(redisPassword);
        // redisConfig.setUsername(redisUsername); // Redis 6+ 需指定用户名（默认default）+ 密码
        // redisConfig.setDatabase(redisDatabase);

        // 2. Lettuce客户端配置（关键修复）
        LettuceClientConfigurationBuilder builder = LettuceClientConfiguration.builder();

        // 关闭自动重连（先确保首次连接成功，再开启）
        builder.clientOptions(ClientOptions.builder()
                .autoReconnect(false)
                .pingBeforeActivateConnection(true) // 连接前先ping，确保Redis可用
                .build());

        // 构建Lettuce配置
        LettuceClientConfiguration config = builder.build();

        // 3. 创建连接工厂
        LettuceConnectionFactory factory = new LettuceConnectionFactory(redisConfig, config);
        factory.afterPropertiesSet(); // 强制初始化
        return factory;
    }

    // 配置RedisTemplate
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory());
        
        // JSON序列化器
        GenericJackson2JsonRedisSerializer jsonSerializer = new GenericJackson2JsonRedisSerializer();

        // Key序列化
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        // Value序列化
        redisTemplate.setValueSerializer(jsonSerializer);
        redisTemplate.setHashValueSerializer(jsonSerializer);

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
