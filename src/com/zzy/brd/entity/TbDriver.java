package com.zzy.brd.entity;
import com.zzy.brd.entity.interfaces.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wpr on 2021/3/23 0023.
 */
@Entity
@Table(name = "tb_driver")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(value = true)
public class TbDriver extends IdEntity implements java.io.Serializable {
    private static final long serialVersionUID = 1820717433366616044L;

    /**
     * 性别：1-男生，2-女生，对应表字段为：tb_driver.sex
     */
    private String sex;

    /**
     * 年龄，对应表字段为：tb_driver.age
     */
    private String age;

    /**
     * 工龄，对应表字段为：tb_driver.work_age
     */
    @Column(name="work_age")
    private String workAge;

    /**
     * 密码，对应表字段为：tb_driver.password
     */
    private String password;

    /**
     * 用户名称，对应表字段为：tb_driver.user_name
     */
    @Column(name="user_name")
    private String userName;

    /**
     * 手机号码，对应表字段为：tb_driver.mobileno
     */
    private String mobileno;

    /**
     * 状态 0-禁用,1-启用,2-删除，对应表字段为：tb_driver.state
     */
    private String state;

    /**
     * 司机状态（0-下班，1-上班），对应表字段为：tb_driver.driver_status
     */
    @Column(name="driver_status")
    private String driverStatus;

    /**
     * 用户目前位置，对应表字段为：tb_driver.location
     */
    private String location;

    /**
     * 用户所在位置经纬度--纬度，对应表字段为：tb_driver.lat
     */
    private String lat;

    /**
     * 用户所在位置经纬度--经度，对应表字段为：tb_driver.lon
     */
    private String lon;

    /**
     * 身份证号，对应表字段为：tb_driver.id_card
     */
    @Column(name="id_card")
    private String idCard;

    /**
     * 车牌号，对应表字段为：tb_driver.car_no
     */
    @Column(name="car_no")
    private String carNo;

    /**
     * 车辆颜色，对应表字段为：tb_driver.car_color
     */
    @Column(name="car_color")
    private String carColor;

    /**
     * 车辆品牌，对应表字段为：tb_driver.car_mark
     */
    @Column(name="car_mark")
    private String carMark;

    /**
     * 司机评分级：几颗星最高5个星1代表一个星，对应表字段为：tb_driver.driver_star
     */
    @Column(name="driver_star")
    private String driverStar;

    /**
     * 驾驶证号，对应表字段为：tb_driver.driver_no
     */
    @Column(name="driver_no")
    private String driverNo;

    /**
     * 创建时间，对应表字段为：tb_driver.create_time
     */
    @Column(name="create_time")
    private Date createTime;

    /**
     * 更新时间，对应表字段为：tb_driver.update_time
     */
    @Column(name="update_time")
    private Date updateTime;

    /** 评价 */
    /*@OneToMany(targetEntity = TbEvaluate.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", referencedColumnName = "id")
    private TbDriver tbDriver;*/

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getWorkAge() {
        return workAge;
    }

    public void setWorkAge(String workAge) {
        this.workAge = workAge;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(String driverStatus) {
        this.driverStatus = driverStatus;
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

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getCarMark() {
        return carMark;
    }

    public void setCarMark(String carMark) {
        this.carMark = carMark;
    }

    public String getDriverStar() {
        return driverStar;
    }

    public void setDriverStar(String driverStar) {
        this.driverStar = driverStar;
    }

    public String getDriverNo() {
        return driverNo;
    }

    public void setDriverNo(String driverNo) {
        this.driverNo = driverNo;
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
