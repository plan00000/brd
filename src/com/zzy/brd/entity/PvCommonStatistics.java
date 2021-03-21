package com.zzy.brd.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

import com.zzy.brd.entity.interfaces.IdEntity;

/**
 * @author:xpk
 *    2017年1月13日-下午4:20:49
 **/
@Entity
@Table(name = "pv_common_statistics")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(value = true)
public class PvCommonStatistics extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3723556416709056028L;
	
	private int websitePv;
	
	private int websiteTotalPv;
	
	/***统计时间*/
	private Date statisticsDate;

	public int getWebsitePv() {
		return websitePv;
	}

	public void setWebsitePv(int websitePv) {
		this.websitePv = websitePv;
	}

	public int getWebsiteTotalPv() {
		return websiteTotalPv;
	}

	public void setWebsiteTotalPv(int websiteTotalPv) {
		this.websiteTotalPv = websiteTotalPv;
	}

	public Date getStatisticsDate() {
		return statisticsDate;
	}

	public void setStatisticsDate(Date statisticsDate) {
		this.statisticsDate = statisticsDate;
	} 

}
