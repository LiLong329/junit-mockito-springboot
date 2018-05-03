package com.didispace.shiro;


import com.didispace.redis.RedisService;
import com.didispace.service.UserQueryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 类名称：UserRealm<br>
 * 类描述：shiro 用户验证及验权具体实现类<br>
 * 创建时间：2018年4月12日下午9:00:34<br>
 * @author wangfang
 */
@Slf4j
public class UserRealm extends AuthorizingRealm {
	
//	@Value("${user-center.api.appcode}")
	private String appCode;
//	@Value("${yuncang.shiro.active}")
    private String shiroSwitch;
	@Autowired
	private UserQueryService userQueryService;
	@Autowired
	private RedisService sessionRedisService;
	
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		
		String jwtToken = (String)arg0.getPrimaryPrincipal();
		String caToken = JWTUtil.getUsername(jwtToken);
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//查询用户角色信息并添加到shiro权限过滤器 
//		UserRolesResultDTO rolesDTO;
		Set<String> perms = new HashSet<String>();
		//Set<String> roles = new HashSet<String>();
		try {
			boolean activeInterceptor = Boolean.parseBoolean(shiroSwitch);
		    LOGGER.debug("是否开启shiro 认证:{}", activeInterceptor);
		    
		    if(activeInterceptor) {
		    	//判断用户是否是管理员，如果是管理员，则手动增加管理员角色；
				boolean isAdmin = false;
//				isAdmin = userQueryService.checkIsEnterpriseAdmin(appCode, caToken);
//				if(isAdmin) {
//					//管理员有全部权限  ,获取管理员的所有权限
//					String roleCodes = UserRoleConstants.ROLES_COMMANAGER;
//					perms=  userQueryService.retrievePermByRoleCode(appCode, caToken,roleCodes);
//				}else{
//					perms = userQueryService.retrieveShiroUserPerm(appCode, caToken);
//				}
				
		    }else {
		    	//管理员有全部权限 
//		    	String roleCodes = UserRoleConstants.ROLES_COMMANAGER;
//				perms=  userQueryService.retrievePermByRoleCode(appCode, caToken,roleCodes);
		    }
		    
			info.addStringPermissions(perms);
			
		} catch (Exception e) {
			LOGGER.error("授权失败，获取用户角色列表失败",e);
			info.addRoles(new HashSet<String>());
		}
		
		LOGGER.debug("shiro 开始授权,caToken为{}",caToken);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
		String token = (String) auth.getCredentials();
		String caToken = JWTUtil.getUsername(token);
		boolean activeInterceptor = Boolean.parseBoolean(shiroSwitch);
	    LOGGER.debug("是否开启shiro 认证:{}", activeInterceptor);
	    if (activeInterceptor) {
	    	String sessionToken = sessionRedisService.get(caToken);
	        LOGGER.debug("从Session缓存中读取[KEY:{}]的值:{}", caToken, sessionToken);
	        if (Objects.isNull(sessionToken) || sessionToken.isEmpty()) {
	            LOGGER.warn("shiro 认证失败，ca Token：{}", caToken);
	        	throw new UnknownAccountException("shiro认证失败");
	        }
	    }
	    sessionRedisService.setKeyExpire(caToken, sessionRedisService.getDefaultExpireTime());
		//第一个参数为 Principal ，在授权时 作为用户的唯一标识
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(token, token, getName());
		LOGGER.debug("shiro验证通过，caToken为{}",caToken);
		return info; 
	}

}
