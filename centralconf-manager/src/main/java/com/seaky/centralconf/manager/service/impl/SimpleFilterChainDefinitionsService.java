package com.seaky.centralconf.manager.service.impl;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seaky.centralconf.manager.entry.vo.PermissionVo;
import com.seaky.centralconf.manager.service.AbstractFilterChainDefinitionsService;
import com.seaky.centralconf.manager.service.PermService;

@Service
public class SimpleFilterChainDefinitionsService extends AbstractFilterChainDefinitionsService {
	@Autowired
	PermService permService;

	@Override
	public Map<String, String> initOtherPermission() throws Exception {
		Map<String, String> map = Collections.synchronizedMap(new LinkedHashMap<String, String>());
		map.put("/js/**", "anon");
		map.put("/ajax/**", "anon");
		map.put("/authority/**", "anon");
		map.put("/css/**", "anon");
		map.put("/fonts/**", "anon");
		map.put("/img/**", "anon");
		map.put("/login", "anon");
		List<PermissionVo> list = permService.getAllPerm();
		if (list.size() > 0) {
			for (PermissionVo p : list) {
				map.put(p.getUrl(), p.getPerms());
			}
		}
		map.put("/**", "authc");
		return map;
	}
}
