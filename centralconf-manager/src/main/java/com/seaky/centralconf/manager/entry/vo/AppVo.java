package com.seaky.centralconf.manager.entry.vo;

public class AppVo {
	/**
	 * 
	 */

	private Long id;
	private String appName;
	private String appDesc;
	private Long appIdentifyId;
	private Long createTime;
	private Long createUserId;
	private Long liableUserId;
	private Long updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppDesc() {
		return appDesc;
	}

	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
	}

	public Long getAppIdentifyId() {
		return appIdentifyId;
	}

	public void setAppIdentifyId(Long appIdentifyId) {
		this.appIdentifyId = appIdentifyId;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public Long getLiableUserId() {
		return liableUserId;
	}

	public void setLiableUserId(Long liableUserId) {
		this.liableUserId = liableUserId;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

}
