package com.seaky.centralconf.manager.entry.vo;

import java.util.List;

public class ResourceVo {
	/**
	 * 
	 */
	private Long id;
	private String resName;
	private String resDesc;
	private Long createTime;
	private Long createUserId;
	private Long updateTime;
	private List<ResEnvVo> envs;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public String getResDesc() {
		return resDesc;
	}

	public void setResDesc(String resDesc) {
		this.resDesc = resDesc;
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

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public List<ResEnvVo> getEnvs() {
		return envs;
	}

	public void setEnvs(List<ResEnvVo> envs) {
		this.envs = envs;
	}

}
