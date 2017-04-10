package com.seaky.centralconf.manager.shiro;

import java.util.List;
import java.util.Set;

public interface RedisManager {
	
	public void init();

	public byte[] hget(final byte[] key, final byte[] mapkey);

	public Long hset(final byte[] key, final byte[] mapkey, final byte[] value, Long expire);

	public void hdel(final byte[] key, final byte[] mapkey);

	public Long hlen(byte[] name);

	public Set<byte[]> hkeys(byte[] name);

	public List hvals(final byte[] key);

	/**
	 *------------------------------------------------------------------------------
	 */
	
	

	public Long del(final byte[] sessionId) throws Exception;


	public String updateSession(final byte[] key, final byte[] session,final Long expire);

	public void deleteSession(byte[] bytes);

	public List getKeys(byte[] bytes);

	public Object getSession(byte[] bytes);
	

}
