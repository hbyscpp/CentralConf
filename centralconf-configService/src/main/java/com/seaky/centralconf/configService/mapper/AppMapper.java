package com.seaky.centralconf.configService.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.seaky.centralconf.configService.entry.po.App;

import tk.mybatis.mapper.common.BaseMapper;

public interface AppMapper extends BaseMapper<App> {

	public List<App> selectAllByUserId(@Param("userId") Long userId, @Param("type") Integer type);
}
