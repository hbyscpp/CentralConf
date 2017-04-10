package com.seaky.centralconf.manager.service;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.seaky.centralconf.manager.common.YtException;
import com.seaky.centralconf.manager.entry.po.User;
import com.seaky.centralconf.manager.entry.vo.PermissionVo;
import com.seaky.centralconf.manager.exception.YtfmUserErrors;
import com.seaky.centralconf.manager.util.base.Const;

/**
 * 
 * @author huangfan
 *
 * @date 2016年9月28日
 */
public class MyShiroRealm extends AuthorizingRealm {
  @Autowired
  UserService userService;
  @Autowired
  PermService permService;

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
    User user = (User) principalCollection.getPrimaryPrincipal();
    // User user = null;
    // String userName = (String)principalCollection.getPrimaryPrincipal();
    SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
    // Boolean b = userService.isUserExist(user.getUserName());
    // if (!b) {
    // return null;
    // }
    try {
      user = userService.getUserByName(user.getUserName());
    } catch (Exception e) {
      throw new YtException(YtfmUserErrors.MAPPER_VALUE);
    }
    List<PermissionVo> list = permService.getAllPerm();
    if (user.getType() == 1) {
      if (list != null && list.size() > 0) {
        for (PermissionVo p : list) {
          simpleAuthorizationInfo.addStringPermission("perms[" + p.getPerms() + "]");
        }
      }
    } else {
      List<String> perm = null;
      if (perm != null && perm.size() > 0 && list != null && list.size() > 0) {
        for (PermissionVo p : list) {
          if (perm.contains(p.getId().toString())) {
            simpleAuthorizationInfo.addStringPermission("perms[" + p.getPerms() + "]");
          }
        }
      }
    }
    simpleAuthorizationInfo.addStringPermission("get");
    return simpleAuthorizationInfo;

  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
      throws AuthenticationException {
    String userName = (String) authenticationToken.getPrincipal();
    User user = userService.getUserByName(userName);
    if (user == null) {
      return null;
    }
    if (user.getStatus() == 1) {
      throw new YtException(YtfmUserErrors.USER_DISABLE);
    }
    SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user,
        user.getPassword(), ByteSource.Util.bytes(user.getSalt()), this.getName());
    this.setSession(Const.SESSION_USER, user);
    // SecurityUtils.getSubject().getSession().setAttribute(Const.SESSION_USER, user);
    return simpleAuthenticationInfo;
  }

  private void setSession(Object key, Object value) {
    Subject currentUser = SecurityUtils.getSubject();
    if (null != currentUser) {
      Session session = currentUser.getSession();
      if (null != session) {
        session.setAttribute(key, value);
      }
    }
  }

  @Override
  public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
    super.clearCachedAuthorizationInfo(principals);
  }

}
