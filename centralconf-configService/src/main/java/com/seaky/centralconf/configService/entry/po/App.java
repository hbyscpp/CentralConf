package com.seaky.centralconf.configService.entry.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "conf_app")
public class App implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private Long id;
	@Column
	private String appName;
	@Column
	private String appDesc;
	@Column
	private Long appIdentifyId;
	@Column
	private Long createTime;
	@Column
	private Long createUserId;
	@Column
	private Long liableUserId;
	@Column
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
