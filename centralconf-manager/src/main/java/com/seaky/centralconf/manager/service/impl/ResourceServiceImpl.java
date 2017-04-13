package com.seaky.centralconf.manager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.seaky.centralconf.core.common.YtException;
import com.seaky.centralconf.manager.entry.po.AppEnvResEnv;
import com.seaky.centralconf.manager.entry.po.BindItem;
import com.seaky.centralconf.manager.entry.po.Item;
import com.seaky.centralconf.manager.entry.po.Resource;
import com.seaky.centralconf.manager.entry.vo.JsonPage;
import com.seaky.centralconf.manager.entry.vo.MyPage;
import com.seaky.centralconf.manager.entry.vo.ResEnvVo;
import com.seaky.centralconf.manager.entry.vo.ResourceVo;
import com.seaky.centralconf.manager.exception.YtfmUserErrors;
import com.seaky.centralconf.manager.mapper.AppEnvResEnvMapper;
import com.seaky.centralconf.manager.mapper.BindItemMapper;
import com.seaky.centralconf.manager.mapper.ResourceMapper;
import com.seaky.centralconf.manager.service.ItemService;
import com.seaky.centralconf.manager.service.ResourceEnvService;
import com.seaky.centralconf.manager.service.ResourceService;
import com.seaky.centralconf.manager.util.ConvertUtil;
import com.seaky.centralconf.manager.util.IdGen;

@Service
@Transactional
public class ResourceServiceImpl implements ResourceService {
  @Autowired
  ResourceMapper resourceMapper;
  @Autowired
  ResourceEnvService resourceEnvService;
  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  AppEnvResEnvMapper appEnvResEnvMapper;
  @Autowired
  BindItemMapper bindItemMapper;
  @Autowired
  ItemService itemService;

  public void addRes(ResourceVo resourceVo) {
    Resource resource = new Resource();
    try {
      BeanUtils.copyProperties(resource, resourceVo);
    } catch (Exception e) {
      throw new YtException(YtfmUserErrors.COPY_PROPRER);
    }
    resource.setId(IdGen.get().nextId());
    resource.setCreateTime(System.currentTimeMillis());
    resourceMapper.insert(resource);
  }

  public JsonPage<ResourceVo> getAllRes(MyPage myPage, Long userId, Integer type) {
    Page<ResourceVo> page = PageHelper.startPage(myPage.getPageNo(), myPage.getLimit(), true);
    List<Resource> permList = resourceMapper.selectAllByUserId(userId, type);
    List<ResourceVo> result = new ArrayList<ResourceVo>();
    JsonPage<ResourceVo> resultList = new JsonPage<ResourceVo>(page);
    for (Resource resource : permList) {
      ResourceVo resourceVo = new ResourceVo();
      try {
        BeanUtils.copyProperties(resourceVo, resource);
      } catch (Exception e) {
        throw new YtException(YtfmUserErrors.COPY_PROPRER);
      }
      result.add(resourceVo);
    }
    resultList.setRows(result);
    return resultList;
  }

  public void delRes(Long id) {
    resourceMapper.deleteByPrimaryKey(id);
  }

  public ResourceVo getResourceByName(String resName) {
    Resource condition = new Resource();
    condition.setResName(resName);
    Resource resource = resourceMapper.selectOne(condition);
    if (resource == null) {
      return null;
    }
    ResourceVo resourceVo = new ResourceVo();
    try {
      BeanUtils.copyProperties(resourceVo, resource);
    } catch (Exception e) {
      e.printStackTrace();
      throw new YtException(YtfmUserErrors.COPY_PROPRER);
    }
    return resourceVo;
  }

  public ResourceVo getResourceById(Long resId) {
    Resource resource = resourceMapper.selectByPrimaryKey(resId);
    ResourceVo resourceVo = new ResourceVo();
    try {
      BeanUtils.copyProperties(resourceVo, resource);
    } catch (Exception e) {
      throw new YtException(YtfmUserErrors.COPY_PROPRER);
    }
    return resourceVo;
  }

  public List<ResourceVo> getAllRes() {
    List<Resource> selectAll = resourceMapper.selectAll();
    List<ResourceVo> copyTo = ConvertUtil.copyTo(selectAll, ResourceVo.class);
    return copyTo;
  }

  public JsonPage<ResourceVo> getAllResourceAndEnv() {
    // Map<String, Object> result = new HashMap<String, Object>();
    JsonPage<ResourceVo> result = new JsonPage<ResourceVo>();
    List<ResourceVo> allRes = this.getAllRes();
    for (ResourceVo resourceVo : allRes) {
      List<ResEnvVo> envs = resourceEnvService.getAllEnvByResId(resourceVo.getId());
      resourceVo.setEnvs(envs);
    }
    result.setRows(allRes);
    result.setPage(1);
    result.setTotal(new Long(allRes.size()));
    return result;
  }

  public void addRelativeResource(Long appId, Long envId, Long resId, Long resEnvId,
      String itemValue) {
    // Map<String, Map<String, Object>> map = null;
    // Map<String, Map<String, Object>> itemMap = null;
    Map<String, Object> itemMap = null;
    try {
      // map = objectMapper.readValue(value, Map.class);
      itemMap = objectMapper.readValue(itemValue, Map.class);
    } catch (Exception e) {
      throw new YtException(YtfmUserErrors.MAPPER_VALUE);
    }
    AppEnvResEnv aereSel = new AppEnvResEnv();
    aereSel.setAppId(appId);
    aereSel.setAppEnvId(envId);
    aereSel.setResId(resId);
    // aereSel.setResEnvId(resEnvId);
    List<AppEnvResEnv> selectList = appEnvResEnvMapper.select(aereSel);
    for (AppEnvResEnv aere : selectList) {
      appEnvResEnvMapper.delete(aere);
      BindItem biDel = new BindItem();
      biDel.setAppenvResId(aere.getId());
      bindItemMapper.delete(biDel);
    }
    aereSel.setId(IdGen.get().nextId());
    aereSel.setResEnvId(resEnvId);
    appEnvResEnvMapper.insert(aereSel);
    // 获取默认值并关联
    List<Item> findDefItemByEnvId = itemService.findDefItemByEnvId(resEnvId);
    for (Item item : findDefItemByEnvId) {
      BindItem bi = new BindItem();
      bi.setId(IdGen.get().nextId());
      bi.setAppenvResId(aereSel.getId());
      bi.setItemId(item.getId());
      bindItemMapper.insert(bi);
    }
    // 关联勾选的item
    for (Entry<String, Object> entryItem : itemMap.entrySet()) {
      BindItem bi = new BindItem();
      bi.setId(IdGen.get().nextId());
      bi.setAppenvResId(aereSel.getId());
      bi.setItemId(Long.parseLong(entryItem.getKey()));
      bindItemMapper.insert(bi);
    }

    // List<AppEnvResEnv> selectList = appEnvResEnvMapper.select(aereDel);
    // for (AppEnvResEnv ar : selectList) {
    // BindItem biDel = new BindItem();
    // biDel.setAppenvResId(ar.getId());
    // bindItemMapper.delete(biDel);
    // }
    // appEnvResEnvMapper.delete(aereDel);
    // for (Entry<String, Map<String, Object>> entry : map.entrySet()) {
    // for (Entry<String, Object> entryValue : entry.getValue().entrySet())
    // {
    // AppEnvResEnv aere = new AppEnvResEnv();
    // aere.setId(IdGen.get().nextId());
    // aere.setAppId(appId);
    // aere.setAppEnvId(envId);
    // aere.setResId(Long.parseLong(entry.getKey()));
    // aere.setResEnvId(Long.parseLong(entryValue.getKey()));
    // appEnvResEnvMapper.insert(aere);
    // // 获取默认值并关联
    // List<Item> findDefItemByEnvId =
    // itemService.findDefItemByEnvId(Long.parseLong(entryValue.getKey()));
    // for (Item item : findDefItemByEnvId) {
    // BindItem bi = new BindItem();
    // bi.setId(IdGen.get().nextId());
    // bi.setAppenvResId(aere.getId());
    // bi.setItemId(item.getId());
    // bindItemMapper.insert(bi);
    // }
    // // 关联勾选的item
    // for (Entry<String, Object> entryItem :
    // itemMap.get(entryValue.getKey()).entrySet()) {
    // BindItem bi = new BindItem();
    // bi.setId(IdGen.get().nextId());
    // bi.setAppenvResId(aere.getId());
    // bi.setItemId(Long.parseLong(entryItem.getKey()));
    // bindItemMapper.insert(bi);
    // }
    //
    // }
    // }
  }

  public Map<String, Map<String, Object>> getRelativeResource(Long appId, Long envId) {
    Map<String, Map<String, Object>> mapResult = new HashMap<String, Map<String, Object>>();
    AppEnvResEnv aereSel = new AppEnvResEnv();
    aereSel.setAppId(appId);
    aereSel.setAppEnvId(envId);
    List<AppEnvResEnv> list = appEnvResEnvMapper.select(aereSel);
    for (AppEnvResEnv ar : list) {
      if (mapResult.containsKey((ar.getResId()).toString())) {
        Map<String, Object> map = mapResult.get(ar.getResId().toString());
        map.put(ar.getResEnvId().toString(), true);
      } else {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(ar.getResEnvId().toString(), true);
        mapResult.put(ar.getResId().toString(), map);
      }
    }
    return mapResult;
  }

  public Map<String, Object> getBindItem(Long appId, Long envId, Long resId, Long resEnvId) {
    Map<String, Object> mapResult = new HashMap<String, Object>();
    List<Item> list = itemService.getBindItem(appId, envId, resId, resEnvId);
    for (Item item : list) {
      mapResult.put(item.getId().toString(), true);
    }
    return mapResult;
  }

  public Map<String, Map<String, Object>> getBindItem(Long appId, Long envId) {
    Map<String, Map<String, Object>> mapResult = new HashMap<String, Map<String, Object>>();
    AppEnvResEnv aereSel = new AppEnvResEnv();
    aereSel.setAppId(appId);
    aereSel.setAppEnvId(envId);
    List<AppEnvResEnv> list = appEnvResEnvMapper.select(aereSel);
    for (AppEnvResEnv ar : list) {
      Map<String, Object> map = new HashMap<String, Object>();
      List<Item> itemList = itemService.getBindItem(appId, envId, ar.getResId(), ar.getResEnvId());
      for (Item item : itemList) {
        map.put(item.getId().toString(), true);
      }
      mapResult.put(ar.getResEnvId().toString(), map);
    }
    return mapResult;
  }
}
