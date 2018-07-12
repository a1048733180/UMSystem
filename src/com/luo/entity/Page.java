package com.luo.entity;

/**
 * 分页参数
 * 
 * @author L99
 *
 */
public class Page {

	private int pageSize; // 页面大小
	private int totalPage; // 总页面
	private int pageStart; // 页面开始
	private int pageCode; // 当前页码
	private int totalNumber; // 总条数

	public Page() {

	}

	public Page(int pageCode, int pageSize) {
		this.pageCode = pageCode;
		this.pageSize = pageSize;
		this.pageStart = (pageCode - 1) * pageSize;
	}

	// 获取页面第一条
	public int getStart() {
		return this.pageStart = (pageCode - 1) * pageSize;
	}

	public void setStart(int start) {
		this.pageStart = start;
	}

	// 获取当前页码
	public int getPageCode() {
		return this.pageCode;
	}

	public void setPageCode(int pageCode) {
		this.pageCode = pageCode;
	}

	// 获取当前页面数量
	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	// 获取总条数
	public int getTotalNumber() {
		return this.totalNumber;
	}

	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
		this.totalPage = (totalNumber % pageSize == 0 ? (totalNumber / pageSize) : ((totalNumber / pageSize) + 1));
	}

	// 获取总页数
	public int getTotalPage() {
		return this.totalPage ;
	}

	public void setTotalPage() {
		this.totalPage =  (totalNumber % pageSize == 0 ? (totalNumber / pageSize) : ((totalNumber / pageSize) + 1));
		
	}
}
