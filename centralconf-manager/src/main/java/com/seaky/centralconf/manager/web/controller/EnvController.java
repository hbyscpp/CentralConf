package com.seaky.centralconf.manager.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.seaky.centralconf.manager.common.WebResponse;
import com.seaky.centralconf.manager.entry.po.User;
import com.seaky.centralconf.manager.entry.vo.AppEnvVo;
import com.seaky.centralconf.manager.entry.vo.MyPage;
import com.seaky.centralconf.manager.service.EnvService;
import com.seaky.centralconf.manager.service.UserService;
import com.seaky.centralconf.manager.util.base.Const;

@Controller
public class EnvController {
  @Autowired
  UserService userService;
  @Autowired
  EnvService envService;

  @RequestMapping("/toEnv")
  public ModelAndView toEnv(String app) {
    ModelAndView mv = new ModelAndView();
    mv.addObject("app", app);
    mv.setViewName("app/evnList");
    return mv;
  }

  @ResponseBody
  @RequestMapping(value = "/getEnv")
  public WebResponse getEnv(Long appId, HttpSession session, MyPage myPage) throws Exception {
    User sessionUser = (User) session.getAttribute(Const.SESSION_USER);
    WebResponse wb = new WebResponse(0, Const.DATA_SUCCEED,
        envService.getEnv(appId, sessionUser.getId(), sessionUser.getType(), myPage));
    return wb;
  }

  @ResponseBody
  @RequestMapping(value = "/copyEnv")
  public WebResponse copyEnv(@RequestParam String app, @RequestParam String srcEnv,
      @RequestParam String dstEnv, @RequestParam boolean isOverrie) {
    // manager.copyEnv(app, srcEnv, dstEnv, isOverrie);
    // return new JsonResult(true, "操作成功", null);
    return new WebResponse(0, Const.OPER_SUCCEED, null);
  }

  @ResponseBody
  @RequestMapping(value = "/delEnv", method = {RequestMethod.POST})
  public WebResponse delEnv(Long appId, Long envId) {
    envService.delEnv(appId, envId);
    WebResponse wrs = new WebResponse(0, Const.DEL_SUCCEED, null);
    return wrs;
  }

  @ResponseBody
  @RequestMapping(value = "/addEnv", method = {RequestMethod.POST})
  public WebResponse addEnv(AppEnvVo appEnvVo) {
    WebResponse wrs = new WebResponse(0, Const.SAVE_SUCCEED, null);
    AppEnvVo envByName = envService.getEnvByName(appEnvVo.getAppId(), appEnvVo.getEnvName());
    if (envByName != null) {
      return new WebResponse(-1, Const.EXIST, null);
    }
    envService.addenv(appEnvVo);
    return wrs;
  }

}
