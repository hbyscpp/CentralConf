package test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.seaky.centralconf.manager.shiro.SerializeUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisManagerStandOlone{

	private int expire = 1800;

	private int timeout = 0;

	private String password = "";

	private static JedisPool jedisPool = null;

	private int port = 6379;

	private String host = "192.168.88.128";

	public RedisManagerStandOlone() {
		
	}

	/**
	 * 初始化方法
	 */
	public void init() {
		if (jedisPool == null) {
			if (password != null && !"".equals(password)) {
				jedisPool = new JedisPool(new JedisPoolConfig(), host, port, timeout, password);
			} else if (timeout != 0) {
				jedisPool = new JedisPool(new JedisPoolConfig(), host, port, timeout);
			} else {
				jedisPool = new JedisPool(new JedisPoolConfig(), host, port);
			}
		}
	}

	/**
	 * get value from redis
	 * 
	 * @param key
	 * @return
	 */
	public byte[] get(final byte[] key, final byte[] mapkey) {
		byte[] value = null;
		Jedis jedis = jedisPool.getResource();
		try {
			value = jedis.hget(key, mapkey);
		} finally {
			jedisPool.returnResource(jedis);
		}
		return value;
	}

	/**
	 * set
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Long put(final byte[] key, final byte[] mapkey, final byte[] value, Long expire) {
		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.hset(key, mapkey, value);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * set
	 * 
	 * @param key
	 * @param value
	 * @param expire
	 * @return
	 */
	public byte[] set(byte[] key, byte[] value, int expire) {
		Jedis jedis = jedisPool.getResource();
		try {
			String set = jedis.set(key, value);
		} finally {
			jedisPool.returnResource(jedis);
		}
		return null;
	}

	/**
	 * del
	 * 
	 * @param key
	 */
	public void del(final byte[] key, final byte[] mapkey) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.hdel(key, mapkey);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	public Long deleteCached(final byte[] sessionId) throws Exception {
		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.del(sessionId);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * flush
	 */
	public void flushDB(String pattern) {
		// Jedis jedis = jedisPool.getResource();
		// try {
		// jedis.flushDB();
		// } finally {
		// jedisPool.returnResource(jedis);
		// }
		// Set<byte[]> keys = keys(pattern);
		// for(byte[] key:keys){
		// this.del(key);
		// }
	}

	/**
	 * size
	 */
	public Long dbSize(byte[] name) {
		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.hlen(name);
		} finally {
			jedisPool.returnResource(jedis);
		}

	}

	/**
	 * keys
	 * 
	 * @param regex
	 * @return
	 */
	public Set<byte[]> keys(byte[] name) {
		Jedis jedis = jedisPool.getResource();
		Set<byte[]> hkeys = null;
		try {
			hkeys = jedis.hkeys(name);
		} finally {
			jedisPool.returnResource(jedis);
		}
		return hkeys;
	}

	public List getHashValues(final byte[] key) throws Exception {
		Jedis jedis = jedisPool.getResource();
		List<byte[]> hVals = null;
		List list = new ArrayList();
		try {
			hVals = (List<byte[]>) jedis.hvals(key);
			for (byte[] bs : hVals) {
				list.add(SerializeUtil.unserialize(bs));
			}
		} finally {
			jedisPool.returnResource(jedis);
		}
		return list;
	}

	public int getExpire() {
		return expire;
	}

	public void setExpire(int expire) {
		this.expire = expire;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	
}
