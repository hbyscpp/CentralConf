package com.seaky.centralconf.manager.service;

import java.util.List;

import com.seaky.centralconf.manager.entry.po.Item;
import com.seaky.centralconf.manager.entry.vo.AppEnvVo;
import com.seaky.centralconf.manager.entry.vo.ItemVo;
import com.seaky.centralconf.manager.entry.vo.JsonPage;
import com.seaky.centralconf.manager.entry.vo.MyPage;

public interface ItemService {

	public ItemVo getItemByName(String itemName);

	public void addItem(ItemVo itemVo);

	public JsonPage<ItemVo> getEnvItem(Long id, Long envId, MyPage myPage);

	public void updateItem(ItemVo itemVo);

	public void delItem(Long id);

	public JsonPage<ItemVo> getCommonItem(Long appId, MyPage myPage);

	public void addCommonItem(Long appId, ItemVo itemVo);

	public ItemVo getItemByName(ItemVo itemVo);

	public AppEnvVo getCommonEnv(Long appId);

	public void delCommonItem(Long id);

	public void updateCommonItem(Long appId, ItemVo itemVo);

	public JsonPage<ItemVo> getRelationResourceItem(Long appId, Long envId, MyPage myPage);

	public List<Item> findDefItemByEnvId(Long envId);

	public List<Item> getBindItem(Long appId, Long envId, Long resId, Long resEnvId);

}
