package com.seaky.centralconf.manager.entry.vo;

import java.util.List;

/**
 * 
 * @author huangfan
 *
 * @date 2016年9月29日
 */
public class PermissionVo {

	private Long id;
	private String permissionName;
	private String permissionDescribe;
	private String url;
	private int type;
	private String perms;
	private String userPerm;
	private int sort;
	private Long parentId;
	private List<PermissionVo> sunPerm;

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

	public List<PermissionVo> getSunPerm() {
		return sunPerm;
	}

	public void setSunPerm(List<PermissionVo> sunPerm) {
		this.sunPerm = sunPerm;
	}

}
