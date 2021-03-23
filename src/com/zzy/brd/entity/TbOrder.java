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
@Table(name = "tb_order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(value = true)
public class TbOrder extends IdEntity implements java.io.Serializable{
    private static final long serialVersionUID = 1820717433366616046L;

    public enum OrderStatus {
        /** 已取消 */
        yqx,
        /** 派单中*/
        pdz,
        /** 司机已接单*/
        sjyjd,
        /** 行程开始*/
        xcks,
        /** 行程结束*/
        xcjs,
        /** 超时订单*/
        csdd;
        public String getDes() {
            if (this == yqx) {
                return "已取消";
            }
            if (this == pdz) {
                return "派单中";
            }
            if (this == sjyjd) {
                return "司机已接单";
            }
            if (this == xcks) {
                return "行程开始";
            }
            if (this == xcjs) {
                return "行程结束";
            }
            if (this == csdd) {
                return "超时订单";
            }
            return "";
        }
    }

    /**
     * 下单时间，对应表字段为：tb_order.order_time
     */
    @Column(name="order_time")
    private Date orderTime;

    /**
     * 订单号，对应表字段为：tb_order.order_no
     */
    @Column(name="order_no")
    private String orderNo;

    /**
     * 订单起点，对应表字段为：tb_order.order_start_address
     */
    @Column(name="order_start_address")
    private String orderStartAddress;

    /**
     * 订单起点经度，对应表字段为：tb_order.order_start_lon
     */
    @Column(name="order_start_lon")
    private String orderStartLon;

    /**
     * 订单起点纬度，对应表字段为：tb_order.order_start_lat
     */
    @Column(name="order_start_lat")
    private String orderStartLat;

    /**
     * 订单结束点，对应表字段为：tb_order.order_end_address
     */
    @Column(name="order_end_address")
    private String orderEndAddress;

    /**
     * 订单结束点经度，对应表字段为：tb_order.order_end_lon
     */
    @Column(name="order_end_lon")
    private String orderEndLon;

    /**
     * 订单起点纬度，对应表字段为：tb_order.order_end_lat
     */
    @Column(name="order_end_lat")
    private String orderEndLat;

    /**
     * 司机id，对应表字段为：tb_order.drive_id
     */
    /*@Column(name="drive_id")
    private long driveId;*/

    /**
     * 订单类型:0-包车，1-拼车，对应表字段为：tb_order.order_type
     */
    @Column(name="order_type")
    private String orderType;

    /**
     * 订单种类：0-实时单，1-预约单，对应表字段为：tb_order.order_real_type
     */
    @Column(name="order_real_type")
    private String orderRealType;

    /**
     * 预约时间，对应表字段为：tb_order.order_bespeak_time
     */
    @Column(name="order_bespeak_time")
    private Date orderBespeakTime;

    /**
     * 订单状态:0-已取消，1-派单中，2-司机已接单，3-行程开始，4-行程已完成，5-超时订单，对应表字段为：tb_order.order_status
     */
    @Column(name="order_status")
    private OrderStatus orderStatus;

    /**
     * 订单派单时间，对应表字段为：tb_order.order_receive_time
     */
    @Column(name="order_receive_time")
    private Date orderReceiveTime;

    /**
     * 关联用户id，对应表字段为：tb_order.user_id
     */
    /*@Column(name="user_id")
    private long userId;*/

    /**
     * 乘客人数，对应表字段为：tb_order.order_user_num
     */
    @Column(name="order_user_num")
    private String orderUserNum;

    /**
     * ，对应表字段为：tb_order.line_id
     */
    @Column(name="line_id")
    private long lineId;

    /**
     * ，对应表字段为：tb_order.remark
     */
    private String remark;

    /**
     * 取消原因，对应表字段为：tb_order.cancel_reason
     */
    @Column(name="cancel_reason")
    private String cancelReason;

    /**
     * 创建时间，对应表字段为：tb_order.create_time
     */
    @Column(name="create_time")
    private Date createTime;

    /**
     * 更新时间，对应表字段为：tb_order.update_time
     */
    @Column(name="update_time")
    private Date updateTime;

    /** 司机 */
    @ManyToOne(targetEntity = TbDriver.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "drive_id", referencedColumnName = "id")
    private TbDriver tbDriver;

    /** 司机 */
    @ManyToOne(targetEntity = TbPassenger.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private TbPassenger tbPassenger;


    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderStartAddress() {
        return orderStartAddress;
    }

    public void setOrderStartAddress(String orderStartAddress) {
        this.orderStartAddress = orderStartAddress;
    }

    public String getOrderStartLon() {
        return orderStartLon;
    }

    public void setOrderStartLon(String orderStartLon) {
        this.orderStartLon = orderStartLon;
    }

    public String getOrderStartLat() {
        return orderStartLat;
    }

    public void setOrderStartLat(String orderStartLat) {
        this.orderStartLat = orderStartLat;
    }

    public String getOrderEndAddress() {
        return orderEndAddress;
    }

    public void setOrderEndAddress(String orderEndAddress) {
        this.orderEndAddress = orderEndAddress;
    }

    public String getOrderEndLon() {
        return orderEndLon;
    }

    public void setOrderEndLon(String orderEndLon) {
        this.orderEndLon = orderEndLon;
    }

    public String getOrderEndLat() {
        return orderEndLat;
    }

    public void setOrderEndLat(String orderEndLat) {
        this.orderEndLat = orderEndLat;
    }



    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderRealType() {
        return orderRealType;
    }

    public void setOrderRealType(String orderRealType) {
        this.orderRealType = orderRealType;
    }

    public Date getOrderBespeakTime() {
        return orderBespeakTime;
    }

    public void setOrderBespeakTime(Date orderBespeakTime) {
        this.orderBespeakTime = orderBespeakTime;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getOrderReceiveTime() {
        return orderReceiveTime;
    }

    public void setOrderReceiveTime(Date orderReceiveTime) {
        this.orderReceiveTime = orderReceiveTime;
    }


    public String getOrderUserNum() {
        return orderUserNum;
    }

    public void setOrderUserNum(String orderUserNum) {
        this.orderUserNum = orderUserNum;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
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

    public TbDriver getTbDriver() {
        return tbDriver;
    }

    public void setTbDriver(TbDriver tbDriver) {
        this.tbDriver = tbDriver;
    }

    public TbPassenger getTbPassenger() {
        return tbPassenger;
    }

    public void setTbPassenger(TbPassenger tbPassenger) {
        this.tbPassenger = tbPassenger;
    }

    /*public long getDriveId() {
        return driveId;
    }

    public void setDriveId(long driveId) {
        this.driveId = driveId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }*/

    public long getLineId() {
        return lineId;
    }

    public void setLineId(long lineId) {
        this.lineId = lineId;
    }
}
