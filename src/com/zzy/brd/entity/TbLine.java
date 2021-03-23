package com.zzy.brd.entity;

import com.zzy.brd.entity.interfaces.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by wpr on 2021/3/23 0023.
 */
@Entity
@Table(name = "tb_line")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(value = true)
public class TbLine extends IdEntity implements java.io.Serializable{
    private static final long serialVersionUID = 1820717433366616045L;

    /**
     * 起点，对应表字段为：tb_line.start_address
     */
    @Column(name="")
    private String startAddress;

    /**
     * 终点，对应表字段为：tb_line.end_address
     */
    @Column(name="")
    private String endAddress;

    /**
     * 起点经度，对应表字段为：tb_line.start_lon
     */
    @Column(name="")
    private String startLon;

    /**
     * 起点纬度，对应表字段为：tb_line.start_lat
     */
    @Column(name="")
    private String startLat;

    /**
     * 终点经度，对应表字段为：tb_line.end_lon
     */
    @Column(name="")
    private String endLon;

    /**
     * 终点纬度，对应表字段为：tb_line.end_lat
     */
    @Column(name="")
    private String endLat;

    /**
     * 创建时间，对应表字段为：tb_line.create_time
     */
    @Column(name="")
    private Date createTime;

    /**
     * 更新时间，对应表字段为：tb_line.update_time
     */
    @Column(name="")
    private Date updateTime;


    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public String getStartLon() {
        return startLon;
    }

    public void setStartLon(String startLon) {
        this.startLon = startLon;
    }

    public String getStartLat() {
        return startLat;
    }

    public void setStartLat(String startLat) {
        this.startLat = startLat;
    }

    public String getEndLon() {
        return endLon;
    }

    public void setEndLon(String endLon) {
        this.endLon = endLon;
    }

    public String getEndLat() {
        return endLat;
    }

    public void setEndLat(String endLat) {
        this.endLat = endLat;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
