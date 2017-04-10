package com.seaky.centralconf.manager.service;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.seaky.centralconf.manager.entry.po.Permission;
import com.seaky.centralconf.manager.entry.vo.JsonPage;
import com.seaky.centralconf.manager.entry.vo.MyPage;
import com.seaky.centralconf.manager.entry.vo.PermissionVo;

public interface PermService {

	public JsonPage<PermissionVo> getAllPermByPage(MyPage myPage);

	public List<PermissionVo> getAllPerm();

	public void createPerm(PermissionVo permissionVo);

	public Permission getPermById(Long id);

	public void delPerm(Long id);

	public void updatePerm(PermissionVo permissionVo);

	// public void delAllPerm();

	// public Boolean isPermExist(Long userName);

	public List<PermissionVo> findFoldPermList();
}
