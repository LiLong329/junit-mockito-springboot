package com.didispace;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.didispace.redis.RedisKeys;
import com.didispace.redis.RedisService;
import com.didispace.redis.RedisServiceImpl;

import lombok.extern.slf4j.Slf4j;


@Configuration
@Slf4j
public class RedisConfiguration {

    @Bean
    public RedisService sessionRedisService(StringRedisTemplate template) {
        RedisServiceImpl sessionRedis = new RedisServiceImpl(template);
        sessionRedis.setKeyExpireSeconds(1800L);
        sessionRedis.setProjectKeys(RedisKeys.ProjectKeys.CLOUD_WAREHOUSE);
        sessionRedis.setModuleKeys(RedisKeys.ModuleKeys.WAREHOUSE);
        sessionRedis.setBusinessKeys(RedisKeys.BusinessKeys.SESSION);
        LOGGER.debug("session缓存服务创建完成,默认过期时间为{}秒,默认Key前缀为: {}",
                1800L,
                RedisKeys.ProjectKeys.CLOUD_WAREHOUSE.name() + ":" + RedisKeys.ModuleKeys.WAREHOUSE.name() + ":" + RedisKeys.BusinessKeys.SESSION.name());
        return sessionRedis;
    }

    @Bean
    public RedisService userRedisService(StringRedisTemplate template) {
        RedisServiceImpl sessionRedis = new RedisServiceImpl(template);
        sessionRedis.setKeyExpireSeconds(300L);
        sessionRedis.setProjectKeys(RedisKeys.ProjectKeys.CLOUD_WAREHOUSE);
        sessionRedis.setModuleKeys(RedisKeys.ModuleKeys.WAREHOUSE);
        sessionRedis.setBusinessKeys(RedisKeys.BusinessKeys.USER);
        LOGGER.debug("货主缓存服务创建完成,默认过期时间为{}秒,默认Key前缀为: {}",
                300L,
                RedisKeys.ProjectKeys.CLOUD_WAREHOUSE.name() + ":" + RedisKeys.ModuleKeys.WAREHOUSE.name() + ":" + RedisKeys.BusinessKeys.USER.name());
        return sessionRedis;
    }

    @Bean
    public RedisService siteRedisService(StringRedisTemplate template) {
        RedisServiceImpl sessionRedis = new RedisServiceImpl(template);
        sessionRedis.setKeyExpireSeconds(300L);
        sessionRedis.setProjectKeys(RedisKeys.ProjectKeys.CLOUD_WAREHOUSE);
        sessionRedis.setModuleKeys(RedisKeys.ModuleKeys.WAREHOUSE);
        sessionRedis.setBusinessKeys(RedisKeys.BusinessKeys.SITE);
        LOGGER.debug("仓库缓存服务创建完成,默认过期时间为{}秒,默认Key前缀为: {}",
                300L,
                RedisKeys.ProjectKeys.CLOUD_WAREHOUSE.name() + ":" + RedisKeys.ModuleKeys.WAREHOUSE.name() + ":" + RedisKeys.BusinessKeys.SITE.name());
        return sessionRedis;
    }
}
