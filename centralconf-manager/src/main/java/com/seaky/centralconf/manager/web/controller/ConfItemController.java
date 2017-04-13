package com.seaky.centralconf.manager.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.seaky.centralconf.core.common.WebResponse;
import com.seaky.centralconf.manager.entry.vo.AppEnvVo;
import com.seaky.centralconf.manager.entry.vo.AppVo;
import com.seaky.centralconf.manager.entry.vo.ItemVo;
import com.seaky.centralconf.manager.entry.vo.MyPage;
import com.seaky.centralconf.manager.service.AppService;
import com.seaky.centralconf.manager.service.EnvService;
import com.seaky.centralconf.manager.service.ItemService;
import com.seaky.centralconf.manager.util.base.Const;

@Controller
public class ConfItemController {
  @Autowired
  ItemService itemService;
  @Autowired
  EnvService envService;
  @Autowired
  AppService appService;

  // @RequestMapping("/toConfItem")
  // public ModelAndView toConfItem(String app, String env) {
  // ModelAndView mv = new ModelAndView();
  // mv.addObject("app", app);
  // mv.addObject("env", env);
  // mv.setViewName("centralconf/ajax/jqgrid_confItem");
  // return mv;
  // }
  //
  // @RequestMapping("/toConfItemDetail")
  // public ModelAndView toConfItemDetail(String app, String env) {
  // ModelAndView mv = new ModelAndView();
  // mv.addObject("app", app);
  // mv.addObject("env", env);
  // mv.setViewName("centralconf/ajax/jqgrid_confItem_detail");
  // return mv;
  // }
  //
  // @ResponseBody
  // @RequestMapping(value = "/getConfItem")
  // public Result getAppByPage(String app, String env) {
  // List<ConfigItem> confItems = manager.getOriAllItem(app, env);
  // Result r = JqgridDataUtil.getJqgridDataByConfItem(confItems);
  // return r;
  // }
  //
  // @ResponseBody
  // @RequestMapping(value = "/getCommonConfItem")
  // public Result getCommonConfItem(String app) {
  // List<ConfigItem> confItems = manager.getCommonItem(app);
  // Result r = JqgridDataUtil.getJqgridDataByConfItem(confItems);
  // return r;
  // }
  //
  // @ResponseBody
  // @RequestMapping(value = "/getRelativeResourceItem")
  // public Result getRelativeResourceItem(String app, String env) {
  // List<ResourceItem> resourceItems = manager.getRelativeResourceItem(app,
  // env);
  // Result jsondata =
  // JqgridDataUtil.getJqgridDataByResourceItem(resourceItems, true);
  // return jsondata;
  // }
  //
  // @ResponseBody
  // @RequestMapping(value = "/updConfItem", method = { RequestMethod.POST })
  // public String updEnv(String app, String env, String key, String value,
  // String desc, String oper, String id) {
  //
  // if (oper.equals("add") || oper.equals("edit")) {
  // ConfigItem cfItem = new ConfigItem();
  // cfItem.setApp(app);
  // cfItem.setDesc(desc);
  // cfItem.setEnv(env);
  // cfItem.setKey(key);
  // cfItem.setValue(value);
  // // 防止修改key之后，新增一条，而不是修改
  // if (!id.equals(key)) {
  // manager.deleteConfig(app, env, id);
  // }
  // manager.updateConfig(cfItem);
  // } else if (oper.equals("del")) {
  // manager.deleteConfig(app, env, id);
  // }
  //
  // return "true";
  // }
  //
  // /**
  // * @throws IOException
  // * @throws JsonMappingException
  // * @throws JsonParseException
  // *
  // * @Description: 导入json配置 @param TODO @return String @throws
  // */
  // @ResponseBody
  // @RequestMapping(value = "/importConfItem")
  // public String importConfItem(@RequestParam(value = "file", required =
  // false) MultipartFile file, String app, String env) {
  // try {
  // InputStream in = file.getInputStream();
  // StringBuffer out = new StringBuffer();
  // byte[] buf = new byte[4096];
  // int len = -1;
  // while ((len = in.read(buf)) != -1) {
  // out.append(new String(buf, 0, len));
  // }
  // JavaType javaType = getCollectionType(ArrayList.class, ConfigItem.class);
  // List<ConfigItem> list = mapper.readValue(out.toString(), javaType);
  // // 为每个配置项增加app和env,并导入配置
  // for (ConfigItem item : list) {
  // item.setApp(app);
  // item.setEnv(env);
  // manager.updateConfig(item);
  // }
  // } catch (IOException e) {
  // e.printStackTrace();
  // return null;
  // }
  // return "true";
  // }
  //
  // /**
  // *
  // * @Description: 导出json配置 @param TODO @return String @throws
  // */
  // @RequestMapping(value = "/exportConfItem")
  // public void exportConfItem(String app, String env, HttpServletRequest
  // request, HttpServletResponse response) {
  // List<ConfigItem> confItems = manager.getExportItem(app, env);
  // String content;
  // try {
  // String path = request.getSession().getServletContext().getRealPath("/");
  // content = mapper.writeValueAsString(confItems);
  // String fileName = path + DateUtil.DateToString(new Date(),
  // DateStyle.YYYY_MM_DD_HH_MM_SS_WW) + "E.json";
  // // 1.step 创建文件
  // File file = new File(fileName);
  // if (!file.exists())
  // file.createNewFile();
  // FileOutputStream out = new FileOutputStream(file, true);
  // out.write(content.getBytes("utf-8"));
  // out.close();
  // // 2 step 输入文件流给浏览器
  //
  // // 设置文件MIME类型
  // response.setContentType(request.getSession().getServletContext().getMimeType(fileName));
  // // 设置Content-Disposition
  // response.setHeader("Content-Disposition", "attachment;filename=" +
  // fileName);
  // // 读取目标文件，通过response将目标文件写到客户端
  // // 读取文件
  // InputStream in = new FileInputStream(fileName);
  // OutputStream outRsp = response.getOutputStream();
  //
  // // 写文件
  // int b;
  // while ((b = in.read()) != -1) {
  // outRsp.write(b);
  // }
  //
  // in.close();
  // outRsp.close();
  //
  // } catch (JsonProcessingException e) {
  // e.printStackTrace();
  // } catch (IOException e1) {
  // e1.printStackTrace();
  // }
  // }
  //
  // private JavaType getCollectionType(Class<ArrayList> collectionClass,
  // Class<ConfigItem> elementClasses) {
  // return mapper.getTypeFactory().constructParametricType(collectionClass,
  // elementClasses);
  // }

  /**
   * 更新开始
   */
  @RequestMapping("/toItemDetail")
	public ModelAndView toItemDetail(Long appId, Long envId) {
		ModelAndView mv = new ModelAndView();
		Map<String, Object> map = new HashMap<String, Object>();
		AppVo app = appService.getAppById(appId);
		AppEnvVo appEnvVo = envService.getEnvById(envId);
		mv.addObject("app", app);
		map.put("appId", appId.toString());
		map.put("envId", envId.toString());
		map.put("appName", app.getAppName());
		map.put("envName", appEnvVo.getEnvName());
		mv.addObject("env", map);
		mv.setViewName("app/itemDetail");
		return mv;
	}

  @RequestMapping("/toCommonItemDetail")
  public ModelAndView toCommonItemDetail(Long appId) {
    AppEnvVo commonEnv = itemService.getCommonEnv(appId);
    AppVo app = appService.getAppById(appId);
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("appId", appId.toString());
    map.put("envId", commonEnv.getId().toString());
    map.put("appName", app.getAppName());
    map.put("envName", commonEnv.getEnvName());
    ModelAndView mv = new ModelAndView();
    mv.addObject("env", map);
    mv.setViewName("app/commonItemDetail");
    return mv;
  }

  @ResponseBody
  @RequestMapping(value = "/getEnvItem")
  public WebResponse getEnvItem(Long id, Long envId, MyPage myPge) {
    return new WebResponse(0, Const.DATA_SUCCEED, itemService.getEnvItem(id, envId, myPge));
  }

  @ResponseBody
  @RequestMapping(value = "/addItem", method = {RequestMethod.POST})
  public WebResponse addItem(ItemVo itemVo) {
    ItemVo itemVoOld = itemService.getItemByName(itemVo);
    if (itemVoOld != null) {
      return new WebResponse(-1, Const.EXIST, null);
    }
    itemService.addItem(itemVo);
    return new WebResponse(0, Const.SAVE_SUCCEED, null);
  }

  @ResponseBody
  @RequestMapping(value = "/updateItem", method = {RequestMethod.POST})
  public WebResponse updateItem(ItemVo itemVo) {
    itemService.updateItem(itemVo);
    return new WebResponse(0, Const.SAVE_SUCCEED, null);
  }

  @ResponseBody
  @RequestMapping(value = "/delItem", method = {RequestMethod.POST})
  public WebResponse delItem(Long id) {
    itemService.delItem(id);
    return new WebResponse(0, Const.DEL_SUCCEED, null);
  }

  @ResponseBody
  @RequestMapping(value = "/getCommonItem")
  public WebResponse getCommonItem(@RequestParam Long appId, MyPage myPage) {
    return new WebResponse(0, Const.DATA_SUCCEED, itemService.getCommonItem(appId, myPage));
  }

  @ResponseBody
  @RequestMapping(value = "/addCommonItem", method = {RequestMethod.POST})
  public WebResponse addCommonItem(Long appId, ItemVo itemVo) {
    ItemVo itemVoOld = itemService.getItemByName(itemVo);
    if (itemVoOld != null) {
      return new WebResponse(-1, Const.EXIST, null);
    }
    itemService.addCommonItem(appId, itemVo);
    return new WebResponse(0, Const.SAVE_SUCCEED, null);
  }

  @ResponseBody
  @RequestMapping(value = "/updateCommonItem", method = {RequestMethod.POST})
  public WebResponse updateCommonItem(Long appId, ItemVo itemVo) {
    itemService.updateCommonItem(appId, itemVo);
    return new WebResponse(0, Const.SAVE_SUCCEED, null);
  }

  @ResponseBody
  @RequestMapping(value = "/delCommonItem", method = {RequestMethod.POST})
  public WebResponse delCommonItem(Long id) {
    itemService.delCommonItem(id);
    return new WebResponse(0, Const.DEL_SUCCEED, null);
  }

}
