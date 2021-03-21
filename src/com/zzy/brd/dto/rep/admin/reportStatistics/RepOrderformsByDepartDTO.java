package com.zzy.brd.dto.rep.admin.reportStatistics;

import java.math.BigDecimal;

import com.zzy.brd.entity.Department;



/**
 * 报表统计（部门）
 * 
 * @author csy 2016年10月19日
 */
public class RepOrderformsByDepartDTO {
	/** 部门id */
	private long departid;
	/** 部门名称 */
	private String departname;
	/** 部门人数 */
	private long departUserCount;
	/** 总订单数量 */
	private long totalTime = 0;
	/** 昨日订单数量 */
	private long yestodayTime = 0;
	/** 总订单金额 */
	private BigDecimal totalMoney = BigDecimal.ZERO;
	/** 昨日订单金额 */
	private BigDecimal yestodayMoney = BigDecimal.ZERO;
	/** 累计发放佣金 */
	private BigDecimal addBrokerage = BigDecimal.ZERO;
	private long managerCount;//融资经理数目
	private long sellerCount;//商家s数目
	private long firstDepartmentId;
	private long secondDepartmentId;
	private long thirdDepartmentId;
	
	public RepOrderformsByDepartDTO(Department depart, StatisticsOrderByDepartDTO dto) {
		this.departid = depart.getId();
		this.departname = depart.getName();
		this.addBrokerage = depart.getAddBrokerage();
		this.departUserCount = depart.getDepartmentNum();
		if(depart.getLevel()==1){
			this.firstDepartmentId = depart.getId();
			this.secondDepartmentId = -1;
			this.thirdDepartmentId = -1;
		} else if (depart.getLevel()==2){
			this.firstDepartmentId = depart.getParent().getId();
			this.secondDepartmentId = depart.getId();
			this.thirdDepartmentId = -1;			
		} else if (depart.getLevel()==3){
			this.firstDepartmentId = depart.getParent().getParent().getId();
			this.secondDepartmentId = depart.getParent().getId();
			this.thirdDepartmentId = depart.getId();
			
		}		
		if(dto != null){
			this.totalTime = dto.getTotalTime();
			this.totalMoney = dto.getTotalMoney();
			this.yestodayTime = dto.getYestodayTime();
			this.yestodayMoney = dto.getYestodayMoney();
			this.sellerCount = dto.getSellerCount();
			this.managerCount = dto.getManagerCount();
		}
	}
	
	public long getManagerCount() {
		return managerCount;
	}

	public void setManagerCount(long managerCount) {
		this.managerCount = managerCount;
	}

	public long getSellerCount() {
		return sellerCount;
	}

	public void setSellerCount(long sellerCount) {
		this.sellerCount = sellerCount;
	}
	/**
	 * @return the totalTime
	 */
	public long getTotalTime() {
		return totalTime;
	}

	/**
	 * @param totalTime
	 *            the totalTime to set
	 */
	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}



	/**
	 * @return the totalMoney
	 */
	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	/**
	 * @param totalMoney
	 *            the totalMoney to set
	 */
	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}


	/**
	 * @return the departname
	 */
	public String getDepartname() {
		return departname;
	}

	/**
	 * @param departname
	 *            the departname to set
	 */
	public void setDepartname(String departname) {
		this.departname = departname;
	}

	/**
	 * @return the departid
	 */
	public long getDepartid() {
		return departid;
	}

	/**
	 * @param departid
	 *            the departid to set
	 */
	public void setDepartid(long departid) {
		this.departid = departid;
	}
	public BigDecimal getAddBrokerage() {
		return addBrokerage;
	}
	public void setAddBrokerage(BigDecimal addBrokerage) {
		this.addBrokerage = addBrokerage;
	}
	public long getDepartUserCount() {
		return departUserCount;
	}
	public void setDepartUserCount(long departUserCount) {
		this.departUserCount = departUserCount;
	}

	public long getYestodayTime() {
		return yestodayTime;
	}

	public void setYestodayTime(long yestodayTime) {
		this.yestodayTime = yestodayTime;
	}

	public BigDecimal getYestodayMoney() {
		return yestodayMoney;
	}

	public void setYestodayMoney(BigDecimal yestodayMoney) {
		this.yestodayMoney = yestodayMoney;
	}

	public long getFirstDepartmentId() {
		return firstDepartmentId;
	}

	public void setFirstDepartmentId(long firstDepartmentId) {
		this.firstDepartmentId = firstDepartmentId;
	}

	public long getSecondDepartmentId() {
		return secondDepartmentId;
	}

	public void setSecondDepartmentId(long secondDepartmentId) {
		this.secondDepartmentId = secondDepartmentId;
	}

	public long getThirdDepartmentId() {
		return thirdDepartmentId;
	}

	public void setThirdDepartmentId(long thirdDepartmentId) {
		this.thirdDepartmentId = thirdDepartmentId;
	}	
}
