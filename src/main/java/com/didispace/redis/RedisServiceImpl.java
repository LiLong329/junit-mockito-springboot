package com.didispace.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

import lombok.Setter;

public class RedisServiceImpl implements RedisService {
    private final StringRedisTemplate template;
    @Setter
    private long keyExpireSeconds;
    @Setter
    private RedisKeys.ProjectKeys projectKeys;
    @Setter
    private RedisKeys.ModuleKeys moduleKeys;
    @Setter
    private RedisKeys.BusinessKeys businessKeys;

    public RedisServiceImpl(StringRedisTemplate template) {
        this.template = template;
    }

    @Override
    public void set(String key, String value) {
        set(projectKeys, moduleKeys, businessKeys, key, value);
    }

    @Override
    public String get(String key) {
        return get(projectKeys, moduleKeys, businessKeys, key);
    }

    @Override
    public void set(RedisKeys.BusinessKeys business, String key, String value) {
        set(projectKeys, moduleKeys, business, key, value);
    }

    @Override
    public String get(RedisKeys.BusinessKeys business, String key) {
        return get(projectKeys, moduleKeys, business, key);
    }

    @Override
    public void set(RedisKeys.ModuleKeys module, RedisKeys.BusinessKeys business, String key, String value) {
        set(projectKeys, module, business, key, value);
    }

    @Override
    public String get(RedisKeys.ModuleKeys module, RedisKeys.BusinessKeys business, String key) {
        return get(projectKeys, module, business, key);
    }

    @Override
    public void set(RedisKeys.ProjectKeys project, RedisKeys.ModuleKeys module, RedisKeys.BusinessKeys business, String key, String value) {
        String storeKey = project.name() + ":" + module.name() + ":" + business.name() + ":" + key;
        template.boundValueOps(storeKey).set(value, keyExpireSeconds, TimeUnit.SECONDS);
    }

    @Override
    public String get(RedisKeys.ProjectKeys project, RedisKeys.ModuleKeys module, RedisKeys.BusinessKeys business, String key) {
        String storeKey = project.name() + ":" + module.name() + ":" + business.name() + ":" + key;
        return template.boundValueOps(storeKey).get();
    }

    @Override
    public String getPrefix() {
        return this.projectKeys.name() + ":" + this.moduleKeys.name() + ":" + this.businessKeys.name();
    }

    @Override
    public void delete(String key) {
        template.delete(this.getPrefix() + ":" + key);
    }

    @Override
    public void deleteAll() {
        template.execute((RedisCallback<Void>) connection -> {
            connection.flushAll();
            return null;
        });
    }

    @Override
    public void flushDB() {
        template.execute((RedisCallback<Void>)connection -> {
            connection.flushDb();
            return null;
        });

    }

    @Override
    public Boolean setKeyExpire(String key, Long timeout) {
        return template.boundValueOps(getPrefix() + ":" + key).expire(timeout, TimeUnit.SECONDS);
    }

    @Override
    public Long getKeyExpire(String key) {
        return template.boundValueOps(getPrefix() + ":" + key).getExpire();
    }

    @Override
    public Long getDefaultExpireTime() {
        return this.keyExpireSeconds;
    }
}
