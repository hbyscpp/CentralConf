package com.seaky.centralconf.manager.entry.vo;

import java.util.List;

import com.github.pagehelper.Page;

public class JsonPage<T> {

	private List<T> rows;

	private int page;

	private Long total;

	public JsonPage() {
	}

	/**
	 * 通过Page生成JsonPage
	 * 
	 * @param page
	 */
	public JsonPage(Page<T> page) {
		this.rows = page.getResult();
		this.page = page.getPageNum();
		// this.total = (int) page.getPages();
		this.total = (long) page.getTotal();
	}

	/**
	 * 通过List生成JsonPage
	 * 
	 * @param rows
	 * @param page
	 * @param total
	 * @param records
	 */
	public JsonPage(List<T> rows, int page, Long total) {
		this.rows = rows;
		this.page = page;
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

}
