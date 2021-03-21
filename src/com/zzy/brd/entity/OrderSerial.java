package com.zzy.brd.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

import com.zzy.brd.entity.interfaces.IdEntity;

/**
 * 订单-编号
 * @author lzh 2016/10/27
 *
 */
@Entity
@Table(name = "order_serial")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(value = true)
public class OrderSerial extends IdEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1022219015762490872L;
	/** 编号数*/
	private int serial;
	/** 名称*/
	private String  name;
	public int getSerial() {
		return serial;
	}
	public void setSerial(int serial) {
		this.serial = serial;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
