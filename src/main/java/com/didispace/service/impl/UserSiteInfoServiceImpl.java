package com.didispace.service.impl;

import com.didispace.redis.RedisService;
import com.didispace.service.UserSiteInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 类名称：UserSiteInfoServiceImpl<br>
 * 类描述：<br>
 * 创建时间：2018年05月03日<br>
 *
 * @author lichao
 * @version 1.0.0
 */
@Service
@Slf4j
public class UserSiteInfoServiceImpl implements UserSiteInfoService {

    private final RedisService redisService;

    public UserSiteInfoServiceImpl(@Qualifier("siteRedisService") RedisService redisService) {
        this.redisService = redisService;
    }
}
