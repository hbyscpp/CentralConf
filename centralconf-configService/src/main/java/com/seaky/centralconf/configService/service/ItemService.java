package com.seaky.centralconf.configService.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seaky.centralconf.configService.entry.po.App;
import com.seaky.centralconf.configService.entry.po.AppEnv;
import com.seaky.centralconf.configService.entry.po.Item;
import com.seaky.centralconf.configService.exception.YtfmUserErrors;
import com.seaky.centralconf.configService.mapper.ItemMapper;
import com.seaky.centralconf.core.common.YtException;

@Service
@Transactional
public class ItemService {
	@Autowired
	ItemMapper itemMapper;
	@Autowired
	Environment env;
	@Autowired
	AppService appService;
	@Autowired
	EnvService envService;

	public List<Item> getAllItem(String appName, String envName) {
		List<Item> resultList = new LinkedList<Item>();
		App app = appService.getAppByName(appName);
		AppEnv env = envService.getEnvByName(app.getId(), envName);
		List<Item> relationItem = getRelationItem(app.getId(), env.getId());
		List<Item> commonItem = getCommonItem(app.getId());
		List<Item> envItem = getEnvItem(env.getId());
		resultList.addAll(relationItem);
		resultList.addAll(commonItem);
		// resultList.addAll(envItem);
		for (Item item : envItem) {
			resultList.add(item);
		}
		return resultList;
	}

	public List<Item> getCommonItem(Long appId) {
		AppEnv env = envService.getEnvByName(appId, envService.getCommonEnvName());
		List<Item> commonEnvItem = getEnvItem(env.getId());
		return commonEnvItem;
	}

	public List<Item> getEnvItem(Long envId) {
		Item itemWhere = new Item();
		itemWhere.setEnvId(envId);
		List<Item> envItem = itemMapper.select(itemWhere);
		return envItem;
	}

	public List<Item> getRelationItem(Long appId, Long envId) {
		List<Item> relationItem = itemMapper.getRelationResourceItem(appId, envId);
		List<Item> relItemResult = new LinkedList<Item>();
		for (Item item : relationItem) {
			if (relItemResult.contains(item)) {
				throw new YtException(YtfmUserErrors.RES_REPEAT);
			}
			relItemResult.add(item);
		}
		return relItemResult;
	}

	public static void main(String[] args) {
		List<String> resultList = new LinkedList<String>();
		resultList.add("a");
		resultList.add("b");
		resultList.add("c");
		resultList.add("a");
		for (String item : resultList) {
			System.out.println(item);
		}
	}

}
