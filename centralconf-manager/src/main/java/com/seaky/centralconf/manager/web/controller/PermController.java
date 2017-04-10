package com.seaky.centralconf.manager.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.seaky.centralconf.manager.common.WebResponse;
import com.seaky.centralconf.manager.entry.po.Permission;
import com.seaky.centralconf.manager.entry.vo.MyPage;
import com.seaky.centralconf.manager.entry.vo.PermissionVo;
import com.seaky.centralconf.manager.service.FilterChainDefinitionsService;
import com.seaky.centralconf.manager.service.PermService;
import com.seaky.centralconf.manager.util.base.Const;

@Controller
public class PermController {
  @Autowired
  private PermService permService;
  @Autowired
  FilterChainDefinitionsService filterChainDefinitionsService;

  @RequestMapping("/toPermList")
  public ModelAndView toPermList() {
    ModelAndView mv = new ModelAndView("/authority/permList");
    return mv;
  }

  @ResponseBody
  @RequestMapping("/getAllPermByPage")
  public WebResponse getAllPermByPage(MyPage myPage) {
    WebResponse wrs = new WebResponse(0, Const.DATA_SUCCEED, permService.getAllPermByPage(myPage));
    return wrs;
  }

  @ResponseBody
  @RequestMapping("/getAllPerm")
  public WebResponse getAllPerm() {
    WebResponse wrs = new WebResponse(0, Const.DATA_SUCCEED, permService.getAllPerm());
    return wrs;
  }


  @RequestMapping("/createPerm")
  @ResponseBody
  public WebResponse createPerm(PermissionVo permissionVo) throws Exception {
    WebResponse wrs = new WebResponse(0, Const.SAVE_SUCCEED, null);
    permService.createPerm(permissionVo);
    filterChainDefinitionsService.updatePermission();
    return wrs;
  }

  @RequestMapping("/getPermById")
  @ResponseBody
  public Permission getPermById(Long id) throws Exception {
    return permService.getPermById(id);
  }

  @RequestMapping("/delPerm")
  @ResponseBody
  public WebResponse delPerm(Long id) throws Exception {
    WebResponse wrs = new WebResponse(0, Const.DEL_SUCCEED, null);
    permService.delPerm(id);
    filterChainDefinitionsService.updatePermission();
    return wrs;
  }

  // @RequestMapping("/delAllPerm")
  // @ResponseBody
  // public String delAllPerm() {
  // permService.delAllPerm();
  // return "success";
  // }

  @RequestMapping("/updatePerm")
  @ResponseBody
  public WebResponse updatePerm(PermissionVo permissionVo) throws Exception {
    WebResponse wrs = new WebResponse(0, Const.UPDATE_SUCCEED, null);
    permService.updatePerm(permissionVo);
    filterChainDefinitionsService.updatePermission();
    return wrs;
  }

  @RequestMapping("/findFoldPermList")
  @ResponseBody
  public WebResponse findFoldPermList() throws Exception {
    return new WebResponse(0, Const.DATA_SUCCEED, permService.findFoldPermList());
  }
}
