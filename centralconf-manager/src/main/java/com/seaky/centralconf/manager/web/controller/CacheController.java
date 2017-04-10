package com.seaky.centralconf.manager.web.controller;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seaky.centralconf.manager.entry.po.User;
import com.seaky.centralconf.manager.service.UserService;
import com.seaky.centralconf.manager.util.base.Const;

@Controller
public class CacheController {
	@Autowired
	UserService userService;
	@Autowired
	CacheManager CacheManager;

	// @Autowired
	// MyShiroRealm myShiroRealm;
	/*@Autowired
	RedisSessionDao sessionDAO;*/

	@ResponseBody
	@RequestMapping("/clearAuthorCache")
	public void clearAuthorCache(String userName, HttpSession session) {
		User user1 = (User) session.getAttribute(Const.SESSION_USER);
//		User user2 = (User) SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_USER);
		User user = userService.getUserByName(userName);
		SimplePrincipalCollection principals = new SimplePrincipalCollection(user1, "myShiroRealmName");
		// myShiroRealm.clearCachedAuthorizationInfo(principals);
		Cache<Object, AuthorizationInfo> cache = CacheManager.getCache("authorizationCache");
		Object object = cache.get(principals);
		System.out.println(object);
		cache.remove(principals);
	}

	// @ResponseBody
	// @RequestMapping("/findAllSession")
	// public Map<String, Object> findAllSession() {
	// Collection<Session> sessions = sessionDAO.getActiveSessions();
	// for (Session session : sessions) {
	// System.out.println(session.getId());
	// System.out.println(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY));
	// // if ("qianxun".equals(String.valueOf())) {''
	// //// session.setTimeout(0);// 设置session立即失效，即将其踢出系统
	// // System.out.println(session.getId());
	// // session.setAttribute(BaseConstant.SESSION_FORCE_LOGOUT_KEY,
	// // "aaaa");
	// //// break;
	// // }
	// //
	// System.out.println(session.getAttribute(BaseConstant.SESSION_FORCE_LOGOUT_KEY));
	// }
	// Map<String, Object> map = new HashMap<String, Object>();
	// map.put("1", "1");
	// return map;
	// }
}
