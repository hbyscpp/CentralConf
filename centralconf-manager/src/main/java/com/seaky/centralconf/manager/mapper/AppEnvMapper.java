package com.seaky.centralconf.manager.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.seaky.centralconf.manager.entry.po.AppEnv;
import com.seaky.centralconf.manager.entry.vo.AppEnvVo;

import tk.mybatis.mapper.common.BaseMapper;

public interface AppEnvMapper extends BaseMapper<AppEnv> {

	public List<AppEnv> geEnv(@Param("appId") Long appId, @Param("UserId") Long UserId, @Param("type") Integer type, @Param("common") String common);

}
