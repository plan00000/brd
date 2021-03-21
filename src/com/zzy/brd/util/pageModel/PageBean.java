package com.zzy.brd.util.pageModel;

/**
 * 分页使用
 * 
 * @author pengcq 2015-10-21 15:30:08
 *
 */
public class PageBean {
	
	/** 当前页 */
	private int currentPage;
	/** 总共几页 */
	private int totalPage;
	/** 总共有几条记录 */
	private long total;
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	
	
}
