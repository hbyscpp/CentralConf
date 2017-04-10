package com.seaky.centralconf.manager.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.seaky.centralconf.manager.common.YtException;
import com.seaky.centralconf.manager.entry.po.App;
import com.seaky.centralconf.manager.entry.vo.AppVo;
import com.seaky.centralconf.manager.entry.vo.JsonPage;
import com.seaky.centralconf.manager.entry.vo.MyPage;
import com.seaky.centralconf.manager.exception.YtfmUserErrors;
import com.seaky.centralconf.manager.mapper.AppEnvMapper;
import com.seaky.centralconf.manager.mapper.AppMapper;
import com.seaky.centralconf.manager.service.AppService;
import com.seaky.centralconf.manager.service.EnvService;
import com.seaky.centralconf.manager.util.IdGen;

@Service
public class AppServiceImp implements AppService {
  @Autowired
  AppMapper appMapper;
  @Autowired
  AppEnvMapper appEnvMapper;
  @Autowired
  Environment env;
  @Autowired
  EnvService envService;

  public JsonPage<AppVo> getAllAppByPage(MyPage myPage, Long userId, Integer type) {
    Page<AppVo> page = PageHelper.startPage(myPage.getPageNo(), myPage.getLimit(), true);
    List<App> permList = appMapper.selectAllByUserId(userId, type);
    List<AppVo> result = new ArrayList<AppVo>();
    JsonPage<AppVo> resultList = new JsonPage<AppVo>(page);
    for (App App : permList) {
      AppVo AppVo = new AppVo();
      try {
        BeanUtils.copyProperties(AppVo, App);
      } catch (Exception e) {
        throw new YtException(YtfmUserErrors.COPY_PROPRER);
      }
      result.add(AppVo);
    }
    resultList.setRows(result);
    return resultList;
  }

  // public List<App> getAllApp() {
  // return appMapper.selectAll();
  // }

  public void createApp(AppVo appVo) {
    App app = new App();
    try {
      BeanUtils.copyProperties(app, appVo);
    } catch (Exception e) {
      throw new YtException(YtfmUserErrors.COPY_PROPRER);
    }
    final IdGen idGen = IdGen.get();
    app.setId(idGen.nextId());
    app.setCreateTime(System.currentTimeMillis());
    appMapper.insert(app);
    envService.createCommonEnv(app.getId());
  }

  public AppVo getAppById(Long id) {
    App app = appMapper.selectByPrimaryKey(id);
    AppVo appVo = new AppVo();
    try {
      BeanUtils.copyProperties(appVo, app);
    } catch (Exception e) {
      throw new YtException(YtfmUserErrors.COPY_PROPRER);
    }
    return appVo;
  }

  public void delApp(Long id) {
    appMapper.deleteByPrimaryKey(id);
  }

  public void updateApp(AppVo AppVo) {
    App App = new App();
    try {
      BeanUtils.copyProperties(App, AppVo);
    } catch (Exception e) {
      throw new YtException(YtfmUserErrors.COPY_PROPRER);
    }
    appMapper.updateByPrimaryKeySelective(App);
  }

  public AppVo getAppByName(String appName) {
    App condition = new App();
    condition.setAppName(appName);
    App app = appMapper.selectOne(condition);
    if (app == null) {
      return null;
    }
    AppVo appVo = new AppVo();
    try {
      BeanUtils.copyProperties(appVo, app);
    } catch (Exception e) {
      e.printStackTrace();
      throw new YtException(YtfmUserErrors.COPY_PROPRER);
    }
    return appVo;
  }

}
