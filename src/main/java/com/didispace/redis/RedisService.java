package com.didispace.redis;

public interface RedisService {
    void set(String key, String value);

    String get(String key);

    void set(RedisKeys.BusinessKeys business,
             String key,
             String value);

    String get(RedisKeys.BusinessKeys business,
               String key);

    void set(RedisKeys.ModuleKeys module,
             RedisKeys.BusinessKeys business,
             String key,
             String value);

    String get(RedisKeys.ModuleKeys module,
               RedisKeys.BusinessKeys business,
               String key);

    void set(RedisKeys.ProjectKeys project,
             RedisKeys.ModuleKeys module,
             RedisKeys.BusinessKeys business,
             String key,
             String value);

    String get(RedisKeys.ProjectKeys project,
               RedisKeys.ModuleKeys module,
               RedisKeys.BusinessKeys business,
               String key);

    String getPrefix();

    /**
     * 删除指定KEY以及其对应的value值
     * @param key 指定的KEY值
     */
    void delete(String key);

    /**
     * <h1>请务必谨慎使用</h1>
     * <strong>删除Redis所有数据库的数据</strong>
     */
    void deleteAll();

    /**
     * <h1>请务必谨慎使用</h1>
     * <strong>删除当前连接的Redis数据库的数据</strong>
     */
    void flushDB();

    /**
     * 设置指定KEY的过期时间(单位:秒)
     * @param key 指定的KEY值
     * @param timeout 时长(秒)
     * @return 设置成功返回true,否则返回false
     */
    Boolean setKeyExpire(String key, Long timeout);

    /**
     * 获取指定KEY的过期时间
     * @param key 指定的KEY值
     * @return 过期时间(秒)
     */
    Long getKeyExpire(String key);

    /**
     * 获取默认KEY过期时间(单位:秒)
     * @return 默认过期秒数
     */
    Long getDefaultExpireTime();
}
