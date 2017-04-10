package com.seaky.centralconf.manager.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.seaky.centralconf.manager.entry.po.BindItem;

import tk.mybatis.mapper.common.BaseMapper;

public interface BindItemMapper extends BaseMapper<BindItem> {

	List<BindItem> getBindItem(@Param("appId") Long appId,@Param("envId") Long envId);

}
