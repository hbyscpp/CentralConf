package com.seaky.centralconf.manager.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.seaky.centralconf.core.common.WebResponse;
import com.seaky.centralconf.manager.util.base.Const;

@Controller
public class LoginController {

  @RequestMapping("/login")
  public ModelAndView login(HttpSession session, String name, String password) {
    Object principal = SecurityUtils.getSubject().getPrincipal();
    ModelAndView mv = new ModelAndView(Const.REDIRECT_INDEX);
    if (principal == null) {
      if (StringUtils.isBlank(name) || StringUtils.isBlank(password)) {
        mv.setViewName(Const.FORWORD_LOGIN);
        return mv;
      }
      Subject subject = SecurityUtils.getSubject();
      UsernamePasswordToken token = new UsernamePasswordToken(name, password);
      // SecurityUtils.getSubject().getSession().setTimeout(0);
      subject.login(token);
    }
    return mv;
  }

  @RequestMapping("/index")
  public ModelAndView index() {
    ModelAndView mv = new ModelAndView();
    mv.setViewName(Const.SUCCESS_LOGIN);
    return mv;
  }

  @RequestMapping("/tologin")
  public ModelAndView toLogin(HttpSession session, HttpServletRequest request) {
    String requestType = request.getHeader("X-Requested-With");
    ModelAndView mv = new ModelAndView();
    if (requestType != null) {
      mv.setViewName("/ajaxToLogin");
      return mv;
    }
    Object attribute1 = session.getAttribute(Const.SESSION_USER);
    Object attribute = SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_USER);
    Object principal = SecurityUtils.getSubject().getPrincipal();
    if (principal != null) {
      mv.setViewName(Const.SUCCESS_LOGIN);
    } else {
      mv.setViewName(Const.FORWORD_LOGIN);
    }
    return mv;
  }

  @RequestMapping("/ajaxToLogin")
  @ResponseBody
  public WebResponse ajaxToLogin() {
    return new WebResponse(401, Const.NO_SESSION, null);
  }

  @RequestMapping("/nauthorizedUrl")
  @ResponseBody
  public WebResponse nauthorizedUrl() {
    return new WebResponse(405, Const.NO_AUTHORIZED_MSG, null);
  }

  @RequestMapping("/logout")
  @ResponseBody
  public WebResponse logout() {
    Subject subject = SecurityUtils.getSubject();
    if (subject.isAuthenticated()) {
      subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
    }
    return new WebResponse(0, Const.LOGIN_OUT, null);
  }

  @RequestMapping("/fun1")
  @ResponseBody
  public WebResponse fun1() {
    return new WebResponse(0, Const.LOGIN_OUT, "aaaaa");
  }

}
