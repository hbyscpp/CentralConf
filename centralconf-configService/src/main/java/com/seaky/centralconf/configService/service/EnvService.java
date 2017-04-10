package com.seaky.centralconf.configService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seaky.centralconf.configService.entry.po.AppEnv;
import com.seaky.centralconf.configService.mapper.AppEnvMapper;

@Service
@Transactional
public class EnvService {
	@Autowired
	AppEnvMapper appEnvMapper;
	@Autowired
	Environment env;
	
	public AppEnv getEnvByName(Long appId, String envName) {
		AppEnv appEnvWhere = new AppEnv();
		appEnvWhere.setEnvName(envName);
		appEnvWhere.setAppId(appId);
		AppEnv appEnv = appEnvMapper.selectOne(appEnvWhere);
		if (appEnv == null) {
			return null;
		}
		return appEnv;
	}
	
	public String getCommonEnvName() {
		String common = "_common";
		if(env.getProperty("env.common")!=null){
			common = env.getProperty("env.common");
		}
		return common;
	}
}
