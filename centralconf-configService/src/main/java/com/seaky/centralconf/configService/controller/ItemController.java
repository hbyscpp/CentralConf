package com.seaky.centralconf.configService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seaky.centralconf.configService.entry.po.Item;
import com.seaky.centralconf.configService.service.ItemService;
import com.yuntu.commons.web.WebResponse;

@Controller
public class ItemController {
	@Autowired
	ItemService itemService;

	@RequestMapping("/getAllItem")
	@ResponseBody
	public WebResponse getAllItem(String appName, String envName) {
		List<Item> allItem = itemService.getAllItem(appName, envName);
		return new WebResponse(0, "susscess", allItem);
	}

}
