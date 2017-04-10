package com.seaky.centralconf.manager.entry.po;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author huangfan
 *
 * @date 2016年9月29日
 */

@Table(name = "conf_perm")
public class Permission implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private Long id;
	@Column
	private String permissionName;
	@Column
	private String permissionDescribe;
	@Column
	private String url;
	@Column
	private int type;
	@Column
	private String perms;
	@Column
	private String userPerm;
	@Column
	private int sort;
	@Column
	private Long parentId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getPermissionDescribe() {
		return permissionDescribe;
	}

	public void setPermissionDescribe(String permissionDescribe) {
		this.permissionDescribe = permissionDescribe;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getPerms() {
		return perms;
	}

	public void setPerms(String perms) {
		this.perms = perms;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getUserPerm() {
		return userPerm;
	}

	public void setUserPerm(String userPerm) {
		this.userPerm = userPerm;
	}
	
}
