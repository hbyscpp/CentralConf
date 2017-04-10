package com.seaky.centralconf.configService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seaky.centralconf.configService.entry.po.App;
import com.seaky.centralconf.configService.mapper.AppMapper;

@Service
public class AppService {
	@Autowired
	AppMapper appMapper;

	public App getAppByName(String appName) {
		App condition = new App();
		condition.setAppName(appName);
		App app = appMapper.selectOne(condition);
		if (app == null) {
			return null;
		}
		return app;
	}
}
