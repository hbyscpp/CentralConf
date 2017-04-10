package com.seaky.centralconf.configService.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.seaky.centralconf.configService.entry.po.AppEnv;

import tk.mybatis.mapper.common.BaseMapper;

public interface AppEnvMapper extends BaseMapper<AppEnv> {

	public List<AppEnv> geEnv(@Param("appId") Long appId, @Param("UserId") Long UserId, @Param("type") Integer type, @Param("common") String common);

}
