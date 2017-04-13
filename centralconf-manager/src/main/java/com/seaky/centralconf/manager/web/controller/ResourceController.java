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
import com.seaky.centralconf.manager.entry.vo.JsonPage;
import com.seaky.centralconf.manager.entry.vo.MyPage;
import com.seaky.centralconf.manager.entry.vo.ResourceVo;
import com.seaky.centralconf.manager.service.ResourceService;
import com.seaky.centralconf.manager.util.base.Const;

@Controller
public class ResourceController {
  @Autowired
  ResourceService resourceService;

  @RequestMapping("/toResouce")
  public ModelAndView toResouce() {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("resource/resourceList");
    return mv;
  }

  /**
   * getAllRes
   */
  @ResponseBody
  @RequestMapping(value = "/getAllRes")
  public WebResponse getAllRes(HttpSession session, MyPage myPage) {
    User sessionUser = (User) session.getAttribute(Const.SESSION_USER);
    JsonPage<ResourceVo> resList =
        resourceService.getAllRes(myPage, sessionUser.getId(), sessionUser.getType());
    WebResponse wb = new WebResponse(0, Const.DATA_SUCCEED, resList);
    return wb;
  }

  /**
   * addRes
   */
  @ResponseBody
  @RequestMapping(value = "/addRes", method = {RequestMethod.POST})
  public WebResponse addRes(HttpSession session, ResourceVo resourceVo) {
    ResourceVo resourceResult = resourceService.getResourceByName(resourceVo.getResName());
    if (null != resourceResult) {
      // 已经存在这个资源
      return new WebResponse(0, Const.EXIST, null);
    }
    User sessionUser = (User) session.getAttribute(Const.SESSION_USER);
    resourceVo.setCreateUserId(sessionUser.getId());
    resourceService.addRes(resourceVo);
    WebResponse wrs = new WebResponse(0, Const.SAVE_SUCCEED, null);
    return wrs;
  }

  /**
   * delRes
   */
  @ResponseBody
  @RequestMapping(value = "/delRes", method = {RequestMethod.POST})
  public WebResponse delRes(Long id) {
    resourceService.delRes(id);
    WebResponse wrs = new WebResponse(0, Const.DEL_SUCCEED, null);
    return wrs;
  }

  /**
   * getAllResource
   * 
   * @return
   */
  @RequestMapping("/getAllResource")
  @ResponseBody
  public WebResponse getAllResourceAndEnv() {
    JsonPage<ResourceVo> allResourceAndEnv = resourceService.getAllResourceAndEnv();
    WebResponse wrs = new WebResponse(0, Const.DATA_SUCCEED, allResourceAndEnv);
    return wrs;
  }

  @ResponseBody
  @RequestMapping(value = "/addRelativeResource", method = {RequestMethod.POST})
  public WebResponse addRelativeResource(Long appId, Long envId, Long resId, Long resEnvId,
      String itemValue) {
    resourceService.addRelativeResource(appId, envId, resId, resEnvId, itemValue);
    return new WebResponse(0, Const.SAVE_SUCCEED, null);
  }

  @ResponseBody
  @RequestMapping(value = "/getRelativeResource")
  public WebResponse getRelativeResource(Long appId, Long envId) {
    return new WebResponse(0, Const.DATA_SUCCEED,
        resourceService.getRelativeResource(appId, envId));
  }

  @ResponseBody
  @RequestMapping(value = "/getBindItem")
  public WebResponse getBindItem(Long appId, Long envId) {
    return new WebResponse(0, Const.DATA_SUCCEED, resourceService.getBindItem(appId, envId));
  }

  @ResponseBody
  // @RequestMapping(value = "/getBindItem")
  public WebResponse getBindItem(Long appId, Long envId, Long resId, Long resEnvId) {
    return new WebResponse(0, Const.DATA_SUCCEED,
        resourceService.getBindItem(appId, envId, resId, resEnvId));
  }

}
