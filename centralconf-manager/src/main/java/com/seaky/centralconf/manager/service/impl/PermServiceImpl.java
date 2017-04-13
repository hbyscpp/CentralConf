package com.seaky.centralconf.manager.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.seaky.centralconf.core.common.YtException;
import com.seaky.centralconf.manager.entry.po.Permission;
import com.seaky.centralconf.manager.entry.vo.JsonPage;
import com.seaky.centralconf.manager.entry.vo.MyPage;
import com.seaky.centralconf.manager.entry.vo.PermissionVo;
import com.seaky.centralconf.manager.exception.YtfmUserErrors;
import com.seaky.centralconf.manager.mapper.PermMapper;
import com.seaky.centralconf.manager.service.PermService;
import com.seaky.centralconf.manager.util.IdGen;

@Service
@Transactional
public class PermServiceImpl implements PermService {
  @Autowired
  PermMapper permManager;

  public JsonPage<PermissionVo> getAllPermByPage(MyPage myPage) {
    Page<PermissionVo> page = PageHelper.startPage(myPage.getPageNo(), myPage.getLimit(), true);
    List<Permission> permList = permManager.selectAll();
    List<PermissionVo> result = new ArrayList<PermissionVo>();
    JsonPage<PermissionVo> resultList = new JsonPage<PermissionVo>(page);
    for (Permission permission : permList) {
      PermissionVo permissionVo = new PermissionVo();
      try {
        BeanUtils.copyProperties(permissionVo, permission);
      } catch (Exception e) {
        throw new YtException(YtfmUserErrors.COPY_PROPRER);
      }
      result.add(permissionVo);
    }
    resultList.setRows(result);
    return resultList;
  }

  public List<PermissionVo> getAllPerm() {
    List<Permission> permList = permManager.selectAll();
    List<PermissionVo> result = new ArrayList<PermissionVo>();
    for (Permission permission : permList) {
      PermissionVo permissionVo = new PermissionVo();
      try {
        BeanUtils.copyProperties(permissionVo, permission);
      } catch (Exception e) {
        throw new YtException(YtfmUserErrors.COPY_PROPRER);
      }
      result.add(permissionVo);
    }
    return result;
  }

  public void createPerm(PermissionVo permissionVo) {
    Permission permission = new Permission();
    try {
      BeanUtils.copyProperties(permission, permissionVo);
    } catch (Exception e) {
      throw new YtException(YtfmUserErrors.COPY_PROPRER);
    }
    final IdGen idGen = IdGen.get();
    permission.setId(idGen.nextId());
    permManager.insert(permission);
  }

  public Permission getPermById(Long id) {
    return permManager.selectByPrimaryKey(id);
  }

  public void delPerm(Long id) {
    permManager.deleteByPrimaryKey(id);
  }

  public void updatePerm(PermissionVo permissionVo) {
    Permission permission = new Permission();
    try {
      BeanUtils.copyProperties(permission, permissionVo);
    } catch (Exception e) {
      throw new YtException(YtfmUserErrors.COPY_PROPRER);
    }
    permManager.updateByPrimaryKeySelective(permission);
  }


  public List<PermissionVo> findFoldPermList() {
    List<Permission> selectAll = permManager.selectAll();
    List<PermissionVo> result = new ArrayList<PermissionVo>();
    for (Permission permission : selectAll) {
      PermissionVo permissionVo = new PermissionVo();
      try {
        BeanUtils.copyProperties(permissionVo, permission);
      } catch (Exception e) {
        e.printStackTrace();
        throw new YtException(YtfmUserErrors.COPY_PROPRER);
      }
      result.add(permissionVo);
    }
    return findMenuList(result);
  }

  public List<PermissionVo> findMenuList(List<PermissionVo> permList) {
    List<PermissionVo> firstLevelMenu = new ArrayList<PermissionVo>();
    List<PermissionVo> sunPerm = new ArrayList<PermissionVo>();
    for (PermissionVo perm : permList) {
      if (perm.getParentId() == -1) {
        firstLevelMenu.add(perm);
      } else {
        sunPerm.add(perm);
      }
    }
    return findSunMenu(firstLevelMenu, sunPerm);
  }

  public List<PermissionVo> findSunMenu(List<PermissionVo> firstLevelMenuList,
      List<PermissionVo> sunPermList) {
    for (PermissionVo firstLevelMenu : firstLevelMenuList) {
      List<PermissionVo> permList = new ArrayList<PermissionVo>();
      for (int i = 0; i < sunPermList.size(); i++) {
        PermissionVo sunPerm = sunPermList.get(i);
        if (sunPerm.getParentId().longValue() == firstLevelMenu.getId().longValue()) {
          permList.add(sunPerm);
          sunPermList.remove(i);
          i--;
        }
      }
      firstLevelMenu.setSunPerm(permList);
      findSunMenu(permList, sunPermList);
    }
    return firstLevelMenuList;
  }

}
