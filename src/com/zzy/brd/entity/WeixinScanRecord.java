package com.zzy.brd.entity;

import com.zzy.brd.entity.interfaces.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * 微信用户信息表
 * @author lzh 2016-9-25
 *
 */
@Entity
@Table(name = "weixin_scan_record")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(value = true)
public class WeixinScanRecord extends IdEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8057567895213711918L;

	private String date;

	private long sceneId;

	private int scanNum;

	private int concernNum;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public long getSceneId() {
		return sceneId;
	}

	public void setSceneId(long sceneId) {
		this.sceneId = sceneId;
	}

	public int getScanNum() {
		return scanNum;
	}

	public void setScanNum(int scanNum) {
		this.scanNum = scanNum;
	}

	public int getConcernNum() {
		return concernNum;
	}

	public void setConcernNum(int concernNum) {
		this.concernNum = concernNum;
	}
}
