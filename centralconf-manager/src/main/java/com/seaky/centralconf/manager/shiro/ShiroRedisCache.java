package com.seaky.centralconf.manager.shiro;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShiroRedisCache<K, V> implements Cache<K, V> {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private RedisManager cache;
	private String name;

	public ShiroRedisCache() {
	}

	public ShiroRedisCache(String name, RedisManager cache) {
		this.cache = cache;
		this.name = name;
	}

	private byte[] getByteName() {
		return name.getBytes();

	}

	private byte[] getByteKey(K key) {
		if (key instanceof String) {
			String preKey = key.toString();
			return preKey.getBytes();
		} else {
			return SerializeUtil.serialize(key);
		}
	}

	@SuppressWarnings("unchecked")
	public V get(K key) throws CacheException {
		logger.debug("根据key从Redis中获取对象 key [" + key + "]");
		try {
			if (key == null) {
				return null;
			} else {
				V value = (V) SerializeUtil.unserialize(cache.hget(getByteName(), getByteKey(key)));
				return value;
			}
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}

	public V put(K key, V value) throws CacheException {
		logger.debug("根据key从存储 key [" + key + "]");
		try {
			cache.hset(getByteName(), getByteKey(key), SerializeUtil.serialize(value), null);
			return value;
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}

	public V remove(K key) throws CacheException {
		logger.debug("从redis中删除 key [" + key + "]");
		try {
			V previous = get(key);
			cache.hdel(getByteName(), getByteKey(key));
			return previous;
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}

	public void clear() throws CacheException {
		logger.debug("从redis中删除所有元素");
		try {
			cache.del(getByteName());
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}

	public int size() {
		try {
			Long longSize = new Long(cache.hlen(getByteName()));
			return longSize.intValue();
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}

	public Set<K> keys() {
		try {
			Set<byte[]> hKeys = cache.hkeys(getByteName());
			Set<K> keys = new HashSet<K>();
			for(byte[] bs:hKeys){
				keys.add((K)SerializeUtil.unserialize(bs));
			}
			return keys;
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}

	public Collection<V> values() {
		try {
			Collection<V> values = cache.hvals(getByteName());
			return values;
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}

	/*
	 * public RedisManager getCache() { return cache; }
	 * 
	 * public void setCache(RedisManager cache) { this.cache = cache; }
	 */

}
