package com.seaky.centralconf.manager.shiro;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisManagerStandOlone implements RedisManager {

	/**
	 * 以下为默认值，可以在配置文件中声明进行覆盖
	 */

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

	public byte[] hget(final byte[] key, final byte[] mapkey) {
		byte[] value = null;
		Jedis jedis = jedisPool.getResource();
		try {
			value = jedis.hget(key, mapkey);
			System.out.println(key.toString()+" ---------------------------------------------------------");
			System.out.println(mapkey.toString()+ "-------------------------------------------------------");
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return value;
	}

	public Long hset(final byte[] key, final byte[] mapkey, final byte[] value, Long expire) {
		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.hset(key, mapkey, value);
		} finally {
			// if (jedis != null) {jedis.close();}
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public void hdel(final byte[] key, final byte[] mapkey) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.hdel(key, mapkey);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public Long hlen(byte[] name) {
		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.hlen(name);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}

	}

	public Set<byte[]> hkeys(byte[] name) {
		Jedis jedis = jedisPool.getResource();
		Set<byte[]> hkeys = null;
		try {
			hkeys = jedis.hkeys(name);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return hkeys;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List hvals(final byte[] key) {
		Jedis jedis = jedisPool.getResource();
		List<byte[]> hVals = null;
		List list = new ArrayList();
		try {
			hVals = (List<byte[]>) jedis.hvals(key);
			for (byte[] bs : hVals) {
				list.add(SerializeUtil.unserialize(bs));
			}
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return list;
	}

	/**
	 * -------------------------------------------------------------------------
	 * -----
	 */

	public String updateSession(final byte[] key, final byte[] session, final Long expire) {
		Jedis jedis = jedisPool.getResource();
		try {
			String set = jedis.set(key, session);
			if (expire != null) {
				jedis.expire(key, (int) ((long) expire));
			} else {
				jedis.expire(key, this.expire);
			}
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return null;
	}

	public void deleteSession(byte[] sessionId) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.del(sessionId);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public Long del(byte[] sessionId) throws Exception {
		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.del(sessionId);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public List getKeys(byte[] keys) {
		Jedis jedis = jedisPool.getResource();
		List list = new ArrayList();
		try {
			Set<byte[]> setByte = jedis.keys(keys);
			if (setByte == null || setByte.size() < 1) {
				return null;
			}
			for (byte[] key : setByte) {
				byte[] bs = jedis.get(key);
				list.add(SerializeUtil.unserialize(bs));
			}
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return list;
	}

	public Object getSession(byte[] sessionId) {
		Jedis jedis = jedisPool.getResource();
		try {
			return SerializeUtil.unserialize(jedis.get(sessionId));
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}

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
