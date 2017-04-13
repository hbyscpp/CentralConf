package com.seaky.centralconf.manager.shiro;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.session.Session;

import com.seaky.centralconf.core.common.RedisCluster;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisManagerCluster {

  // 0 - never expire
  private int expire = 1800;

  // timeout for jedis try to connect to redis server, not expire time! In
  // milliseconds

  private int timeout = 0;

  private String password = "";

  private static RedisCluster redisCluster = null;

  private String hostsAndPorts;

  public RedisManagerCluster() {}

  /**
   * 初始化方法
   */
  public void init() {
    if (redisCluster == null) {
      try {
        redisCluster = new RedisCluster(hostsAndPorts);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }


  public byte[] hget(final byte[] key, final byte[] mapkey) {
    byte[] value = null;
    value = redisCluster.hget(key, mapkey);
    return value;
  }


  public Long hset(final byte[] key, final byte[] mapkey, final byte[] value, Long expire) {
    return redisCluster.hset(key, mapkey, value);
  }



  public void hdel(final byte[] key, final byte[] mapkey) {
    redisCluster.hdel(key, mapkey);
  }



  // /**
  // * flush
  // */
  // public void flushDB(String pattern) {
  // // Jedis jedis = jedisPool.getResource();
  // // try {
  // // jedis.flushDB();
  // // } finally {
  // // jedisPool.returnResource(jedis);
  // // }
  // // Set<byte[]> keys = keys(pattern);
  // // for(byte[] key:keys){
  // // this.del(key);
  // // }
  // }

  /**
   * size
   */
  public Long hlen(byte[] name) {
    return redisCluster.hlen(name);

  }

  /**
   * keys
   * 
   * @param regex
   * @return
   */
  public Set<byte[]> hkeys(byte[] name) {
    Set<byte[]> keys = new HashSet<byte[]>();
    Map<String, JedisPool> clusterNodes = redisCluster.getClusterNodes();
    for (String k : clusterNodes.keySet()) {
      JedisPool jp = clusterNodes.get(k);
      Jedis connection = jp.getResource();
      try {
        // keys.addAll(connection.keys(name));
        keys.addAll(connection.hkeys(name));
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        connection.close();// 用完一定要close这个链接！！！
      }
    }
    return keys;
  }

  public List hvals(final byte[] key) throws Exception {
    List<byte[]> hVals = (List<byte[]>) redisCluster.hvals(key);
    if (hVals == null || hVals.size() < 1) {
      return null;
    }
    List list = new ArrayList();
    for (byte[] bs : hVals) {
      list.add(SerializeUtil.unserialize(bs));
    }
    return list;
  }

  /**
   * ------------------------------------------------------------------------------
   */

  public byte[] set(byte[] key, byte[] value, int expire) {
    redisCluster.set(key, value);
    if (expire != 0) {
      redisCluster.expire(key, expire);
    }
    return value;
  }

  public Long del(final byte[] sessionId) throws Exception {
    return redisCluster.del(sessionId);
  }



  public String getHostsAndPorts() {
    return hostsAndPorts;
  }

  public void setHostsAndPorts(String hostsAndPorts) {
    this.hostsAndPorts = hostsAndPorts;
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

  public String updateSession(final byte[] key, final byte[] session, final Long expire) {

    return null;
  }

  public void deleteSession(byte[] bytes) {

  }

  public List<Session> getKeys(byte[] bytes) {
    return null;
  }

}
