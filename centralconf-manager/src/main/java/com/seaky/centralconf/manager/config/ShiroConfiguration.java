package com.seaky.centralconf.manager.config;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.seaky.centralconf.manager.entry.po.Permission;
import com.seaky.centralconf.manager.service.MyShiroRealm;
import com.seaky.centralconf.manager.shiro.MyAuthenticationFilter;
import com.seaky.centralconf.manager.shiro.RedisManager;
import com.seaky.centralconf.manager.shiro.RedisManagerStandOlone;
import com.seaky.centralconf.manager.shiro.RedisSessionDao;
import com.seaky.centralconf.manager.shiro.ShiroRedisCacheManager;

/**
 * Shiro配置
 * 
 * @author huangfan
 *
 * @date 2016年9月28日
 */
@Configuration
public class ShiroConfiguration {

	private static Map<String, String> filterChainDefinitionMap = Collections.synchronizedMap(new LinkedHashMap<String, String>());

	@Bean
	public MyShiroRealm myShiroRealm() {
		MyShiroRealm myShiroRealm = new MyShiroRealm();
		myShiroRealm.setCredentialsMatcher(credentialsMatcher());
		// myShiroRealm.setCachingEnabled(true);
		// myShiroRealm.setAuthenticationCachingEnabled(true);
		// myShiroRealm.setAuthenticationCacheName("authenticationCache");
		// myShiroRealm.setAuthorizationCachingEnabled(false);
		myShiroRealm.setName("myShiroRealmName");
		myShiroRealm.setAuthorizationCacheName("authorizationCache");
		return myShiroRealm;
	}

	@Bean
	public CredentialsMatcher credentialsMatcher() {
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		hashedCredentialsMatcher.setHashAlgorithmName("md5");
		hashedCredentialsMatcher.setHashIterations(2);
		hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
		return hashedCredentialsMatcher;
	}

	@Bean
	public RedisManager getRedisManager() {
		RedisManagerStandOlone redisManager = new RedisManagerStandOlone();
		redisManager.setPort(6379);
		redisManager.setHost("192.168.88.128");
		return redisManager;
	}

	@Bean
	public ShiroRedisCacheManager getRedisCacheManager() {
		ShiroRedisCacheManager redisCacheManager = new ShiroRedisCacheManager();
		redisCacheManager.setCache(getRedisManager());
		return redisCacheManager;
	}

	// @Bean(name = "shiroEhcacheManager")
	// public EhCacheManager getEhCacheManager() {
	// EhCacheManager em = new EhCacheManager();
	// em.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
	// return em;
	// }

	// @Bean
	// public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
	// return new LifecycleBeanPostProcessor();
	// }
	//
	// @Bean
	// public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
	// DefaultAdvisorAutoProxyCreator daap = new
	// DefaultAdvisorAutoProxyCreator();
	// daap.setProxyTargetClass(true);
	// return daap;
	// }

	@Bean
	public RedisSessionDao redisSessionDao() {
		RedisSessionDao redisSessionDao = new RedisSessionDao();
		redisSessionDao.setRedisManager(getRedisManager());
		return redisSessionDao;
	}

	@Bean
	public DefaultWebSessionManager sessionManager() throws Exception {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setGlobalSessionTimeout(1800000);// 1800000
		sessionManager.setSessionValidationInterval(1800000);
		// sessionManager.setDeleteInvalidSessions(true);
		// sessionManager.setSessionValidationSchedulerEnabled(true);
		// sessionManager.setSessionValidationScheduler(sessionValidationScheduler());
		// sessionManager.setSessionDAO(redisSessionDao());
		sessionManager.setSessionIdCookieEnabled(true);
		return sessionManager;
	}

	@Bean
	public DefaultWebSecurityManager defaultWebSecurityManager() throws Exception {
		DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
		dwsm.setRealm(myShiroRealm());
		dwsm.setSessionManager(sessionManager());
		// 采用redis缓存
		dwsm.setCacheManager(getRedisCacheManager());
		// dwsm.setCacheManager(getEhCacheManager());
		return dwsm;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() throws Exception {
		AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
		aasa.setSecurityManager(defaultWebSecurityManager());
		return aasa;
	}

	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean() throws Exception {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager());
		shiroFilterFactoryBean.setLoginUrl("/tologin");
		shiroFilterFactoryBean.setSuccessUrl("/index");
		shiroFilterFactoryBean.setUnauthorizedUrl("/nauthorizedUrl");
		filterChainDefinitionMap.put("/js/**", "anon");
		filterChainDefinitionMap.put("/ajax/**", "anon");
		filterChainDefinitionMap.put("/authority/**", "anon");
		filterChainDefinitionMap.put("/css/**", "anon");
		filterChainDefinitionMap.put("/fonts/**", "anon");
		filterChainDefinitionMap.put("/img/**", "anon");
		filterChainDefinitionMap.put("/login", "anon");
		filterChainDefinitionMap.put("/getUserByPage", "perms[getUserByPage]");
		// List<Permission> permList = null;
		// for (Permission perm : permList) {
		// filterChainDefinitionMap.put(perm.getUrl(), perm.getPerms());
		// }
		List<Permission> list = null;
		// if (list != null && list.size() > 0) {
		// for (Permission p : list) {
		// filterChainDefinitionMap.put(p.getUrl(), p.getPerms());
		// }
		// }
		filterChainDefinitionMap.put("/**", "authc");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

}
