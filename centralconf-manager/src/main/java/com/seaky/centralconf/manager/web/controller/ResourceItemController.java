package com.seaky.centralconf.manager.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.seaky.centralconf.manager.common.WebResponse;
import com.seaky.centralconf.manager.entry.vo.ItemVo;
import com.seaky.centralconf.manager.entry.vo.JsonPage;
import com.seaky.centralconf.manager.entry.vo.MyPage;
import com.seaky.centralconf.manager.entry.vo.ResEnvVo;
import com.seaky.centralconf.manager.entry.vo.ResourceVo;
import com.seaky.centralconf.manager.service.ItemService;
import com.seaky.centralconf.manager.service.ResourceEnvService;
import com.seaky.centralconf.manager.service.ResourceService;
import com.seaky.centralconf.manager.util.base.Const;

@Controller
public class ResourceItemController {
  @Autowired
  ItemService itemService;
  @Autowired
  ResourceEnvService resourceEnvService;
  @Autowired
  ResourceService resourceService;

  @RequestMapping("/toResourceEnvItem")
  public ModelAndView toResourceEnvItem(@RequestParam Long resId, @RequestParam Long envId) {
    ResEnvVo envVo = resourceEnvService.getEnvById(envId);
    ResourceVo resource = resourceService.getResourceById(resId);
    ModelAndView mv = new ModelAndView();
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("resId", resId.toString());
    map.put("envId", envId.toString());
    map.put("resName", resource.getResName());
    map.put("envName", envVo.getEnvName());
    mv.addObject("env", map);
    mv.setViewName("resource/resourceItemList");
    return mv;
  }

  @ResponseBody
  @RequestMapping(value = "/getResourceEnvItem")
  public WebResponse getResourceEnvItem(Long id, Long envId, MyPage myPage) {
    if (envId == null) {
      JsonPage<ItemVo> result = new JsonPage<ItemVo>();
      result.setRows(new ArrayList<ItemVo>());
      return new WebResponse(0, Const.DATA_SUCCEED, result);
    }
    JsonPage<ItemVo> envItem = itemService.getEnvItem(id, envId, myPage);
    WebResponse wb = new WebResponse(0, Const.DATA_SUCCEED, envItem);
    return wb;
  }

  @ResponseBody
  @RequestMapping(value = "/addResourceItem", method = {RequestMethod.POST})
  public WebResponse addResourceItem(ItemVo itemVo) {
    ItemVo itemVoOld = itemService.getItemByName(itemVo);
    if (itemVoOld != null) {
      return new WebResponse(-1, Const.EXIST, null);
    }
    itemService.addItem(itemVo);
    return new WebResponse(0, Const.SAVE_SUCCEED, null);
  }

  @ResponseBody
  @RequestMapping(value = "/delResourceItem", method = {RequestMethod.POST})
  public WebResponse delResourceItem(Long id) {
    itemService.delItem(id);
    return new WebResponse(0, Const.DEL_SUCCEED, null);
  }

  @ResponseBody
  @RequestMapping(value = "/updateResourceItem", method = {RequestMethod.POST})
  public WebResponse updateResourceItem(ItemVo itemVo) {
    itemService.updateItem(itemVo);
    return new WebResponse(0, Const.SAVE_SUCCEED, null);
  }

  @ResponseBody
  @RequestMapping(value = "/getRelationResourceItem")
  public WebResponse getRelationResourceItem(Long appId, Long envId, MyPage myPage) {
    JsonPage<ItemVo> resList = itemService.getRelationResourceItem(appId, envId, myPage);
    return new WebResponse(0, Const.DATA_SUCCEED, resList);
  }

}
