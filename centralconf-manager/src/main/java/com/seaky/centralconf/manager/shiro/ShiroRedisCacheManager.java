package com.seaky.centralconf.manager.shiro;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
/**
 * redis的缓存管理器
 * @author caomei
 *
 */
public class ShiroRedisCacheManager extends AbstractCacheManager {
	private RedisManager cache;

	@Override
	protected Cache<String, Object> createCache(String name) throws CacheException {
		cache.init();
		return new ShiroRedisCache<String,Object>(name,cache);
	}

	public RedisManager getCache() {
		return cache;
	}

	public void setCache(RedisManager cache) {
		this.cache = cache;
	}
	
	
	
	

}
