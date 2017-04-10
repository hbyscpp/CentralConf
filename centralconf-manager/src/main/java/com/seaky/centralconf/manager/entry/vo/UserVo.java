package com.seaky.centralconf.manager.entry.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserVo {
	/**
	 * 
	 * @author huangfan
	 *
	 * @date 2016年9月28日
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String userName;
	private String realName;
	private String department;
	private Integer type;
	private Long mobile;
	private Integer status;
	private String remark;
	private Long createTime;
	private Long updateTime;
	private List<String> perms = new ArrayList<String>();;
	private Map<String, Map<String, Object>> app = new HashMap<String, Map<String, Object>>();
	private List<String> resourse;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}


	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getMobile() {
		return mobile;
	}

	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public List<String> getPerms() {
		return perms;
	}

	public void setPerms(List<String> perms) {
		this.perms = perms;
	}

	public Map<String, Map<String, Object>> getApp() {
		return app;
	}

	public void setApp(Map<String, Map<String, Object>> app) {
		this.app = app;
	}

	public List<String> getResourse() {
		return resourse;
	}

	public void setResourse(List<String> resourse) {
		this.resourse = resourse;
	}

}
