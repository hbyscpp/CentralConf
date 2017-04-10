package com.seaky.centralconf.manager.service;

import java.util.List;

import com.seaky.centralconf.manager.entry.vo.JsonPage;
import com.seaky.centralconf.manager.entry.vo.MyPage;
import com.seaky.centralconf.manager.entry.vo.ResEnvVo;

public interface ResourceEnvService {

	public JsonPage<ResEnvVo> getEnv(Long resId, Long id, Integer type, MyPage myPage);

	public ResEnvVo getEnvByName(Long resId, String envName);

	public void addenv(ResEnvVo resEnvVo);

	public void delEnv(Long resId, Long envId);
	
	public ResEnvVo getEnvById(Long envId);

	public List<ResEnvVo> getAllEnvByResId(Long resId);

}
