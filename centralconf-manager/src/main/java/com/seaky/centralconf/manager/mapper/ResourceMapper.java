package com.seaky.centralconf.manager.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.seaky.centralconf.manager.entry.po.Resource;

import tk.mybatis.mapper.common.BaseMapper;

public interface ResourceMapper extends BaseMapper<Resource> {

	List<Resource> selectAllByUserId(@Param("userId") Long userId, @Param("type") Integer type);

}
