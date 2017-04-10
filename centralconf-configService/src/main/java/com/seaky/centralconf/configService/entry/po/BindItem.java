package com.seaky.centralconf.configService.entry.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "conf_bind_item")
public class BindItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	@Column
	private Long appenvResId;
	@Column
	private Long itemId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAppenvResId() {
		return appenvResId;
	}

	public void setAppenvResId(Long appenvResId) {
		this.appenvResId = appenvResId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

}
