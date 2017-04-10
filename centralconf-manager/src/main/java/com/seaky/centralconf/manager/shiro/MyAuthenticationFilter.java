package com.seaky.centralconf.manager.shiro;

import java.util.Collection;
import java.util.LinkedHashMap;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

public class MyAuthenticationFilter extends FormAuthenticationFilter {
	public MyAuthenticationFilter() {
		setLoginUrl(DEFAULT_LOGIN_URL);
	}

	protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
		AuthenticationToken token = createToken(request, response);
		if (token == null) {
			String msg = "createToken method implementation returned null. A validnon-null AuthenticationToken " + "must be created in order to execute alogin attempt.";
			throw new IllegalStateException(msg);
		}
		try {
			Subject subject = getSubject(request, response);
			// 获取session数据
			Session session = subject.getSession();
			final LinkedHashMap<Object, Object> attributes = new LinkedHashMap<Object, Object>();
			final Collection<Object> keys = session.getAttributeKeys();
			for (Object key : keys) {
				final Object value = session.getAttribute(key);
				if (value != null) {
					attributes.put(key, value);
				}
			}
			session.stop();
			subject.login(token);
			// 登录成功后复制session数据
			session = subject.getSession();
			for (final Object key : attributes.keySet()) {
				session.setAttribute(key, attributes.get(key));
			}
			return onLoginSuccess(token, subject, request, response);
		} catch (AuthenticationException e) {
			return onLoginFailure(token, e, request, response);
		}
	}

	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
		issueSuccessRedirect(request, response);
		// we handled the success redirect directly, prevent the chain from
		System.out.println(token);
		// continuing:
		return false;
	}
}
