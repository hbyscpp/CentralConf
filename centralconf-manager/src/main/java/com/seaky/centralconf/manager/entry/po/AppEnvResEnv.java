package com.seaky.centralconf.manager.entry.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "conf_appenv_resource")
public class AppEnvResEnv implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private Long id;
	@Column
	private Long appId;
	@Column
	private Long resId;
	@Column
	private Long appEnvId;
	@Column
	private Long resEnvId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public Long getResId() {
		return resId;
	}

	public void setResId(Long resId) {
		this.resId = resId;
	}

	public Long getAppEnvId() {
		return appEnvId;
	}

	public void setAppEnvId(Long appEnvId) {
		this.appEnvId = appEnvId;
	}

	public Long getResEnvId() {
		return resEnvId;
	}

	public void setResEnvId(Long resEnvId) {
		this.resEnvId = resEnvId;
	}

}
