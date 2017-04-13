package com.seaky.centralconf.manager.service.impl;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.seaky.centralconf.core.common.YtException;
import com.seaky.centralconf.manager.entry.po.ResEnv;
import com.seaky.centralconf.manager.entry.vo.JsonPage;
import com.seaky.centralconf.manager.entry.vo.MyPage;
import com.seaky.centralconf.manager.entry.vo.ResEnvVo;
import com.seaky.centralconf.manager.exception.YtfmUserErrors;
import com.seaky.centralconf.manager.mapper.ResEnvMapper;
import com.seaky.centralconf.manager.service.ResourceEnvService;
import com.seaky.centralconf.manager.util.ConvertUtil;
import com.seaky.centralconf.manager.util.IdGen;

@Service
@Transactional
public class ResourceEnvServiceImpl implements ResourceEnvService {
  @Autowired
  ResEnvMapper resEnvMapper;

  public JsonPage<ResEnvVo> getEnv(Long resId, Long id, Integer type, MyPage myPage) {
    Page<ResEnvVo> page = PageHelper.startPage(myPage.getPageNo(), myPage.getLimit(), true);
    JsonPage<ResEnvVo> resultList = new JsonPage<ResEnvVo>(page);
    List<ResEnv> envList = resEnvMapper.geEnv(resId, id, type);
    resultList.setRows(ConvertUtil.copyTo(envList, ResEnvVo.class));
    return resultList;
  }

  public ResEnvVo getEnvByName(Long resId, String envName) {
    ResEnv resEnvWhere = new ResEnv();
    resEnvWhere.setResId(resId);
    resEnvWhere.setEnvName(envName);
    ResEnv resEnv = resEnvMapper.selectOne(resEnvWhere);
    if (resEnv == null) {
      return null;
    }
    ResEnvVo resEnvVo = new ResEnvVo();
    try {
      BeanUtils.copyProperties(resEnvVo, resEnv);
    } catch (Exception e) {
      throw new YtException(YtfmUserErrors.COPY_PROPRER);
    }
    return resEnvVo;
  }

  public void addenv(ResEnvVo resEnvVo) {
    ResEnv resEnv = new ResEnv();
    try {
      BeanUtils.copyProperties(resEnv, resEnvVo);
    } catch (Exception e) {
      throw new YtException(YtfmUserErrors.COPY_PROPRER);
    }
    resEnv.setId(IdGen.get().nextId());
    resEnv.setCreateTime(System.currentTimeMillis());
    resEnvMapper.insert(resEnv);
  }

  public void delEnv(Long resId, Long envId) {
    resEnvMapper.deleteByPrimaryKey(envId);
    // UserAppEnv userAppEnv = new UserAppEnv();
    // userAppEnv.setEnvId(envId);
    // userAppEnvMapper.delete(userAppEnv);
  }

  public ResEnvVo getEnvById(Long envId) {
    ResEnv resEnv = resEnvMapper.selectByPrimaryKey(envId);
    if (resEnv == null) {
      return null;
    }
    ResEnvVo resEnvVo = new ResEnvVo();
    try {
      BeanUtils.copyProperties(resEnvVo, resEnv);
    } catch (Exception e) {
      throw new YtException(YtfmUserErrors.COPY_PROPRER);
    }
    return resEnvVo;
  }

  public List<ResEnvVo> getAllEnvByResId(Long resId) {
    ResEnv resEnvCon = new ResEnv();
    resEnvCon.setResId(resId);
    List<ResEnv> selectAll = resEnvMapper.select(resEnvCon);
    return ConvertUtil.copyTo(selectAll, ResEnvVo.class);
  }

}
