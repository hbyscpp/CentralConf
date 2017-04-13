//package com.seaky.centralconf.manager.common;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
//
//import redis.clients.jedis.HostAndPort;
//import redis.clients.jedis.JedisCluster;
//
//public class RedisCluster extends JedisCluster {
//
//  private static final String HOST_SEPARATE = ",";
//  private static final String PORT_SEPARATE = ":";
//
//  private static final int DEFAULT_TIMEOUT = 2000;
//  private static final int DEFAULT_MAX_REDIRECTIONS = 3;
//
//  public RedisCluster(String hostsAndPorts) throws Exception {
//    this(hostsAndPorts, DEFAULT_TIMEOUT);
//  }
//
//  public RedisCluster(String hostsAndPorts, int timeout) throws Exception {
//    this(hostsAndPorts, timeout, DEFAULT_MAX_REDIRECTIONS, null);
//  }
//
//  public RedisCluster(String hostsAndPorts, GenericObjectPoolConfig config) throws Exception {
//    this(hostsAndPorts, DEFAULT_TIMEOUT, DEFAULT_MAX_REDIRECTIONS, config);
//  }
//
//  private static Set<HostAndPort> toHostAndPortPair(String hostsAndPorts) {
//    if (StringUtils.isBlank(hostsAndPorts))
//      throw new IllegalArgumentException("hosts and ports is null");
//    Set<HostAndPort> hostPortSet = new HashSet<HostAndPort>();
//    String[] redisClusterHosts = hostsAndPorts.split(HOST_SEPARATE);
//    for (String redisClusterHost : redisClusterHosts) {
//      String[] hostAndPorts = redisClusterHost.split(PORT_SEPARATE);
//      String host = hostAndPorts[0];
//      int port = Integer.parseInt(hostAndPorts[1]);
//      HostAndPort info = new HostAndPort(host, port);
//      hostPortSet.add(info);
//    }
//    return hostPortSet;
//  }
//
//  public RedisCluster(String hostsAndPorts, int timeout, int retries,
//      GenericObjectPoolConfig config) throws Exception {
//    super(toHostAndPortPair(hostsAndPorts), timeout, retries,
//        config == null ? new GenericObjectPoolConfig() : config);
//  }
//
//}
