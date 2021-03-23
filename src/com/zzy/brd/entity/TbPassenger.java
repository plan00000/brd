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
@Table(name = "tb_passenger")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(value = true)
public class TbPassenger extends IdEntity implements java.io.Serializable{
    private static final long serialVersionUID = 1820717433366616047L;

    /**
     * 性别sex：1-男生，2-女生，对应表字段为：tb_passenger.sex
     */
    private String sex;

    /**
     * 用户名称，对应表字段为：tb_passenger.user_name
     */
    @Column(name="user_name")
    private String userName;

    /**
     * 手机号码，对应表字段为：tb_passenger.mobileno
     */
    private String mobileno;

    /**
     * 用户状态state： 0-禁用,1-启用,2-删除，对应表字段为：tb_passenger.state
     */
    private String state;

    /**
     * 用户目前位置，对应表字段为：tb_passenger.location
     */
    private String location;

    /**
     * 用户所在位置经纬度--纬度，对应表字段为：tb_passenger.lat
     */
    private String lat;

    /**
     * 用户所在位置经纬度--经度，对应表字段为：tb_passenger.lon
     */
    private String lon;

    /**
     * 身份证号，对应表字段为：tb_passenger.id_card
     */
    @Column(name="id_card")
    private String idCard;

    /**
     * 创建时间，对应表字段为：tb_passenger.create_time
     */
    @Column(name="create_time")
    private Date createTime;

    /**
     * 更新时间，对应表字段为：tb_passenger.update_time
     */
    @Column(name="update_time")
    private Date updateTime;

    /**
     * 备注，对应表字段为：tb_passenger.remark
     */
    private String remark;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
