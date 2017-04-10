package com.seaky.centralconf.configService.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.seaky.centralconf.configService.entry.po.Item;

import tk.mybatis.mapper.common.BaseMapper;

public interface ItemMapper extends BaseMapper<Item> {

	List<Item> getRelationResourceItem(@Param("appId") Long appId, @Param("envId") Long envId);

	List<Item> getBindItem(@Param("appId") Long appId,@Param("envId") Long envId,@Param("resId") Long resId,@Param("resEnvId") Long resEnvId);

}
