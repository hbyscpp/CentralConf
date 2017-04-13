	package com.seaky.centralconf.manager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.seaky.centralconf.core.common.YtException;
import com.seaky.centralconf.manager.entry.po.Item;
import com.seaky.centralconf.manager.entry.vo.AppEnvVo;
import com.seaky.centralconf.manager.entry.vo.ItemVo;
import com.seaky.centralconf.manager.entry.vo.JsonPage;
import com.seaky.centralconf.manager.entry.vo.MyPage;
import com.seaky.centralconf.manager.entry.vo.ResEnvVo;
import com.seaky.centralconf.manager.entry.vo.ResourceVo;
import com.seaky.centralconf.manager.exception.YtfmUserErrors;
import com.seaky.centralconf.manager.mapper.ItemMapper;
import com.seaky.centralconf.manager.service.EnvService;
import com.seaky.centralconf.manager.service.ItemService;
import com.seaky.centralconf.manager.service.ResourceEnvService;
import com.seaky.centralconf.manager.service.ResourceService;
import com.seaky.centralconf.manager.util.ConvertUtil;
import com.seaky.centralconf.manager.util.IdGen;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {
  @Autowired
  ItemMapper itemMapper;
  @Autowired
  Environment env;
  @Autowired
  EnvService envService;
  @Autowired
  ResourceEnvService resourceEnvService;
  @Autowired
  ResourceService resourceService;

  public ItemVo getItemByName(String itemName) {
    Item itemWhere = new Item();
    itemWhere.setItemName(itemName);
    Item item = itemMapper.selectOne(itemWhere);
    if (item == null) {
      return null;
    }
    ItemVo itemVo = new ItemVo();
    try {
      BeanUtils.copyProperties(itemVo, item);
    } catch (Exception e) {
      throw new YtException(YtfmUserErrors.COPY_PROPRER);
    }
    return itemVo;
  }

  public void addItem(ItemVo itemVo) {
    Item item = new Item();
    try {
      BeanUtils.copyProperties(item, itemVo);
    } catch (Exception e) {
      throw new YtException(YtfmUserErrors.COPY_PROPRER);
    }
    item.setId(IdGen.get().nextId());
    item.setCreateTime(System.currentTimeMillis());
    itemMapper.insert(item);
  }

  public JsonPage<ItemVo> getEnvItem(Long id, Long envId, MyPage myPage) {
    @SuppressWarnings("unchecked")
    Page<ItemVo> page = PageHelper.startPage(myPage.getPageNo(), myPage.getLimit(), true);
    Item itemWhere = new Item();
    itemWhere.setEnvId(envId);
    List<Item> selectAll = itemMapper.select(itemWhere);
    JsonPage<ItemVo> resultList = new JsonPage<ItemVo>(page);
    List<ItemVo> copyTo = ConvertUtil.copyTo(selectAll, ItemVo.class);
    resultList.setRows(copyTo);
    return resultList;
  }

  public void updateItem(ItemVo itemVo) {
    Item item = new Item();
    try {
      BeanUtils.copyProperties(item, itemVo);
      item.setUpdateTime(System.currentTimeMillis());
      item.setType(null);
      item.setEnvId(null);
      item.setCreateTime(null);
      item.setCreateUserId(null);
    } catch (Exception e) {
      throw new YtException(YtfmUserErrors.COPY_PROPRER);
    }
    itemMapper.updateByPrimaryKeySelective(item);
  }

  public void delItem(Long id) {
    itemMapper.deleteByPrimaryKey(id);
  }

  public JsonPage<ItemVo> getCommonItem(Long appId, MyPage myPage) {
    AppEnvVo appEnvVo = this.getCommonEnv(appId);
    return getEnvItem(appId, appEnvVo.getId(), myPage);
  }

  public void addCommonItem(Long appId, ItemVo itemVo) {
    AppEnvVo appEnvVo = this.getCommonEnv(appId);
    itemVo.setEnvId(appEnvVo.getId());
    this.addItem(itemVo);
  }

  public ItemVo getItemByName(ItemVo itemVo) {
    Item itemWhere = new Item();
    itemWhere.setItemName(itemVo.getItemName());
    itemWhere.setEnvId(itemVo.getEnvId());
    Item item = itemMapper.selectOne(itemWhere);
    if (item == null) {
      return null;
    }
    ItemVo itemVoResult = new ItemVo();
    try {
      BeanUtils.copyProperties(itemVoResult, item);
    } catch (Exception e) {
      throw new YtException(YtfmUserErrors.COPY_PROPRER);
    }
    return itemVoResult;
  }

  public AppEnvVo getCommonEnv(Long appId) {
    AppEnvVo appEnvVo = envService.getEnvByName(appId, envService.getCommonEnvName());
    if (appEnvVo == null) {
      throw new YtException(YtfmUserErrors.NO_CATALOG);
    }
    return appEnvVo;
  }

  public void delCommonItem(Long id) {
    itemMapper.deleteByPrimaryKey(id);
  }

  public void updateCommonItem(Long appId, ItemVo itemVo) {
    AppEnvVo appEnvVo = this.getCommonEnv(appId);
    itemVo.setEnvId(appEnvVo.getId());
    this.updateItem(itemVo);
  }

  public JsonPage<ItemVo> getRelationResourceItem(Long appId, Long envId, MyPage myPage) {
    @SuppressWarnings("unchecked")
    Page<ItemVo> page = PageHelper.startPage(myPage.getPageNo(), myPage.getLimit(), true);
    List<Item> selectAll = itemMapper.getRelationResourceItem(appId, envId);
    JsonPage<ItemVo> resultList = new JsonPage<ItemVo>(page);
    List<ItemVo> result = new ArrayList<ItemVo>();
    for (Item item : selectAll) {
      ItemVo itemVo = new ItemVo();
      try {
        BeanUtils.copyProperties(itemVo, item);
      } catch (Exception e) {
        throw new YtException(YtfmUserErrors.COPY_PROPRER);
      }
      ResEnvVo resEnvVo = resourceEnvService.getEnvById(item.getEnvId());
      ResourceVo resourceVo = resourceService.getResourceById(resEnvVo.getResId());
      itemVo.setResEnvName(resEnvVo.getEnvName());
      itemVo.setResName(resourceVo.getResName());
      result.add(itemVo);
    }
    resultList.setRows(result);
    return resultList;
  }

  public List<Item> findDefItemByEnvId(Long envId) {
    Item item = new Item();
    item.setEnvId(envId);
    item.setIsDefault(0);
    return itemMapper.select(item);
  }

  public List<Item> getBindItem(Long appId, Long envId, Long resId, Long resEnvId) {
    Map<String, Map<String, Object>> mapResult = new HashMap<String, Map<String, Object>>();
    List<Item> list = itemMapper.getBindItem(appId, envId, resId, resEnvId);
    return list;
  }
}
