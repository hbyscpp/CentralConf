package com.seaky.centralconf.manager.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.seaky.centralconf.core.common.WebResponse;
import com.seaky.centralconf.manager.entry.po.User;
import com.seaky.centralconf.manager.entry.vo.AppVo;
import com.seaky.centralconf.manager.entry.vo.JsonPage;
import com.seaky.centralconf.manager.entry.vo.MyPage;
import com.seaky.centralconf.manager.service.AppService;
import com.seaky.centralconf.manager.service.UserService;
import com.seaky.centralconf.manager.util.base.Const;

@Controller
public class AppController {
  @Autowired
  UserService userService;
  @Autowired
  AppService appService;

  @RequestMapping("/toApp")
  public ModelAndView toApp() {
    ModelAndView mv = new ModelAndView();
    // mv.setViewName("ajax/jqgrid_app");
    mv.setViewName("app/appList");
    return mv;
  }

  @RequestMapping("/toApp1")
  public ModelAndView toApp1() {
    ModelAndView mv = new ModelAndView();
    // mv.setViewName("ajax/jqgrid_app");
    mv.setViewName("app/appList2");
    return mv;
  }

  // @ResponseBody
  // @RequestMapping(value = "/getAppOld")
  // public Result getApp(HttpSession session) throws Exception {
  // List<String> apps = manager.getAllApp();
  // User sessionUser = (User) session.getAttribute(Const.SESSION_USER);
  // if (sessionUser.getType() != 1) {
  // User user = userService.getUser(sessionUser.getUserName());
  // Map<String, Map<String, Object>> map = user.getApp();
  // for (int i = 0; i < apps.size(); i++) {
  // if (map.get(apps.get(i)) == null) {
  // apps.remove(i);
  // i--;
  // }
  // }
  // }
  // Result r = JqgridDataUtil.getJqgridData(apps);
  // return r;
  // }

  @ResponseBody
  @RequestMapping(value = "/getApp")
  public WebResponse getAllApp(HttpSession session, MyPage myPage) throws Exception {
    User sessionUser = (User) session.getAttribute(Const.SESSION_USER);
    JsonPage<AppVo> appList =
        appService.getAllAppByPage(myPage, sessionUser.getId(), sessionUser.getType());
    WebResponse wb = new WebResponse(0, Const.DATA_SUCCEED, appList);
    return wb;
  }

  @ResponseBody
  @RequestMapping(value = "/addApp", method = {RequestMethod.POST})
  public WebResponse addApp(AppVo appVo, HttpSession session) {
    AppVo appVoResult = appService.getAppByName(appVo.getAppName());
    if (null != appVoResult) {
      // 已经存在这个应用
      return new WebResponse(0, Const.EXIST, null);
    }
    User sessionUser = (User) session.getAttribute(Const.SESSION_USER);
    appVo.setCreateUserId(sessionUser.getId());
    appService.createApp(appVo);
    return new WebResponse(0, Const.SAVE_SUCCEED, null);
  }

  @ResponseBody
  @RequestMapping(value = "/delApp", method = {RequestMethod.POST})
  public WebResponse delApp(Long id) {
    appService.delApp(id);
    WebResponse wrs = new WebResponse(0, Const.DEL_SUCCEED, null);
    return wrs;
  }
}
