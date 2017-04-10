package com.seaky.centralconf.manager.shiro;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisSessionDao extends AbstractSessionDAO {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private RedisManager redisManager;
	private String sessionprefix = "conf-";

	public void update(Session session) throws UnknownSessionException {
		redisManager.updateSession(session.getId().toString().getBytes(), SerializeUtil.serialize(session), session.getTimeout() / 1000);
	}

	public void delete(Session session) {
		redisManager.deleteSession(session.getId().toString().getBytes());
	}

	public Collection<Session> getActiveSessions() {
		String keys = sessionprefix + "*";
		List<Session> list = null;
		try {
			list = (List<Session>) redisManager.getKeys(keys.getBytes());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return list;
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = session.getId();
		try {
			super.assignSessionId(session, sessionprefix + super.generateSessionId(session));
//			update(session);
			sessionId = session.getId();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		Session session = null;
		try {
			session = (Session) redisManager.getSession(sessionId.toString().getBytes());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return session;
	}

	public RedisManager getRedisManager() {
		return redisManager;
	}

	public void setRedisManager(RedisManager redisManager) {
		this.redisManager = redisManager;
	}

	public String getSessionprefix() {
		return sessionprefix;
	}

	public void setSessionprefix(String sessionprefix) {
		this.sessionprefix = sessionprefix;
	}

}
