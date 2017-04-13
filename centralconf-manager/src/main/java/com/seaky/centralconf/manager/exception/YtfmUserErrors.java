package com.seaky.centralconf.manager.exception;

import com.seaky.centralconf.core.common.Errorcode;

public class YtfmUserErrors {


  /* 新增开始 */
  public static final Errorcode PARAMETER_VALUE_EMPTY =
      new Errorcode(Errorcode.BUSINESS_ERROR, 99, 10011, "参数值为空");
  public static final Errorcode USER_DISABLE =
      new Errorcode(Errorcode.BUSINESS_ERROR, 99, 10012, "账户已停用");
  public static final Errorcode USER_EMPTY =
      new Errorcode(Errorcode.BUSINESS_ERROR, 99, 10013, "获取当前用户失败");
  public static final Errorcode ASSIGNED_ORG_EMPTY =
      new Errorcode(Errorcode.BUSINESS_ERROR, 99, 10014, "未分配组织机构权限");
  public static final Errorcode ORGID_EMPTY =
      new Errorcode(Errorcode.BUSINESS_ERROR, 99, 10015, "未传入组织机构ID");
  public static final Errorcode ORG_MATCHING =
      new Errorcode(Errorcode.BUSINESS_ERROR, 99, 10016, "组织机构权限不匹配");
  public static final Errorcode USER_EXIST =
      new Errorcode(Errorcode.BUSINESS_ERROR, 99, 10017, "用户已经存在");
  public static final Errorcode MAPPER_VALUE =
      new Errorcode(Errorcode.BUSINESS_ERROR, 99, 10018, "转换错误");
  public static final Errorcode COPY_PROPRER =
      new Errorcode(Errorcode.BUSINESS_ERROR, 99, 10019, "复制属性错误");
  public static final Errorcode NO_CATALOG =
      new Errorcode(Errorcode.BUSINESS_ERROR, 99, 10020, "目录不存在");

  /* 新增结束 */



}
