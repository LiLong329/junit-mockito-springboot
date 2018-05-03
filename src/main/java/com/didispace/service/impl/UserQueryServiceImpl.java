package com.didispace.service.impl;

import com.didispace.redis.RedisService;
import com.didispace.service.SysoptuserService;
import com.didispace.service.UserQueryService;
import com.didispace.service.UserSiteInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 类名称：UserQueryServiceImpl<br>
 * 类描述：<br>
 * 创建时间：2018年05月03日<br>
 *
 * @author lichao
 * @version 1.0.0
 */
@Slf4j
@Service("userQueryService")
public class UserQueryServiceImpl implements UserQueryService {
    private final SysoptuserService sysOptUserService;
    private final RedisService redisService;
    private final UserSiteInfoService userSiteInfoService;

    public UserQueryServiceImpl(SysoptuserService sysOptUserService, UserSiteInfoService userSiteInfoService,
                                @Qualifier("userRedisService") RedisService redisService) {
        this.sysOptUserService = sysOptUserService;
        this.redisService = redisService;
        this.userSiteInfoService = userSiteInfoService;
    }
}
