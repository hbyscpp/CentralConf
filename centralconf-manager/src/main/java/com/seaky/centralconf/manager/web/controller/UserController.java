package com.seaky.centralconf.manager.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.seaky.centralconf.manager.common.WebResponse;
import com.seaky.centralconf.manager.entry.po.User;
import com.seaky.centralconf.manager.entry.vo.MyPage;
import com.seaky.centralconf.manager.entry.vo.UserVo;
import com.seaky.centralconf.manager.service.UserService;
import com.seaky.centralconf.manager.util.base.Const;

@Controller
public class UserController {
  @Autowired
  private UserService userService;

  @RequestMapping("/toUserList")
  public ModelAndView toUserList() {
    ModelAndView mv = new ModelAndView(Const.AUTHORITY_USER);
    return mv;
  }

  @ResponseBody
  @RequestMapping("/getUserByPage")
  public WebResponse getUserByPage(MyPage myPage) {
    WebResponse wrs = new WebResponse(0, Const.DATA_SUCCEED, userService.getUserByPage(myPage));
    return wrs;
  }

  @RequestMapping("/createUser")
  @ResponseBody
  public WebResponse createUser(UserVo userVo) throws JsonProcessingException {
    WebResponse wrs = new WebResponse(0, Const.SAVE_SUCCEED, null);
    userService.createUser(userVo);
    return wrs;
  }

  @RequestMapping("/getUser")
  @ResponseBody
  public User getUser(String userName) throws Exception {
    return userService.getUserByName(userName);
  }

  @RequestMapping("/delUser")
  @ResponseBody
  public WebResponse delUser(Long id) {
    WebResponse wrs = new WebResponse(0, Const.DEL_SUCCEED, null);
    userService.delUser(id);
    return wrs;
  }

  // @RequestMapping("/delAllUser")
  // @ResponseBody
  // public String delAllUser() {
  // userService.delAllUser();
  // return "success";
  // }

  @RequestMapping("/updateUser")
  @ResponseBody
  public WebResponse updateUser(UserVo userVo) throws Exception {
    WebResponse wrs = new WebResponse(0, Const.UPDATE_SUCCEED, null);
    userService.updateUser(userVo);
    return wrs;
  }

  // @RequestMapping("/getAllIteam")
  // @ResponseBody
  // public WebResponse getAllIteam() {
  // List<String> allApp = centralConfigManager.getAllApp();
  // List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
  // Map<String, Object> resultMap = new HashMap<String, Object>();
  // for (String s : allApp) {
  // Map<String, Object> map = new HashMap<String, Object>();
  // List<String> envs = centralConfigManager.getAllEnv(s);
  // map.put("app", s);
  // map.put("env", envs);
  // list.add(map);
  // }
  // resultMap.put("rows", list);
  // resultMap.put("total", list.size());
  // WebResponse wrs = new WebResponse(0, Const.DATA_SUCCEED, resultMap);
  // return wrs;
  // }

  /**
   * 保存用户应用权限
   */
  @RequestMapping("/savePermApp")
  @ResponseBody
  public WebResponse savePermApp(String value, String userName) throws Exception {
    userService.savePermApp(value, userName);
    WebResponse wrs = new WebResponse(0, Const.SAVE_SUCCEED, null);
    return wrs;
  }

  /**
   * 获取用户应用权限
   */
  @RequestMapping("/getPermApp")
  @ResponseBody
  public WebResponse getPermApp(String userName) throws Exception {
    WebResponse wrs = new WebResponse(0, Const.DATA_SUCCEED, userService.getPermApp(userName));
    return wrs;
  }

  /**
   * 获取用户访问权限
   * 
   * @param userName
   * @return
   * @throws Exception
   */
  @RequestMapping("/getUserPerm")
  @ResponseBody
  public WebResponse getUserPerm(Long userId) throws Exception {
    WebResponse wrs = new WebResponse(0, Const.DATA_SUCCEED, userService.getPerm(userId));
    return wrs;
  }

  /**
   * 保存用户访问权限
   */
  @RequestMapping("/savePerm")
  @ResponseBody
  public WebResponse savePerm(@RequestParam String value, @RequestParam Long userId)
      throws Exception {
    userService.savePerm(value, userId);
    WebResponse wrs = new WebResponse(0, Const.SAVE_SUCCEED, null);
    return wrs;
  }

  public static void main(String[] args) {

  }

}
