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
@Table(name = "tb_evaluate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(value = true)
public class TbEvaluate extends IdEntity implements java.io.Serializable{
    private static final long serialVersionUID = 1820717433366616051L;

    /**
     * 用户id，对应表字段为：tb_evaluate.user_id
     */
    @Column(name="user_id")
    private long userId;

    /**
     * 司机id，对应表字段为：tb_evaluate.driver_id
     */
    @Column(name="driver_id")
    private long driverId;

    /**
     * 订单id，对应表字段为：tb_evaluate.order_id
     */
    @Column(name="order_id")
    private long orderId;

    /**
     * 订单表id，对应表字段为：tb_evaluate.order_no
     */
    @Column(name="order_no")
    private String orderNo;

    /**
     * 订单评价星级：几颗星最高5个星，对应表字段为：tb_evaluate.order_star
     */
    @Column(name="order_star")
    private String orderStar;

    /**
     * 订单服务评价(111111-车内整洁，认路准，驾驶平稳，态度好，有礼貌，服务周到)，对应表字段为：tb_evaluate.order_service
     */
    @Column(name="order_service")
    private String orderService;

    /**
     * 订单评价，对应表字段为：tb_evaluate.order_evaluate
     */
    @Column(name="order_evaluate")
    private String orderEvaluate;

    /**
     * 创建时间，对应表字段为：tb_evaluate.create_time
     */
    @Column(name="create_time")
    private Date createTime;

    /**
     * 更新时间，对应表字段为：tb_evaluate.update_time
     */
    @Column(name="update_time")
    private Date updateTime;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getDriverId() {
        return driverId;
    }

    public void setDriverId(long driverId) {
        this.driverId = driverId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderStar() {
        return orderStar;
    }

    public void setOrderStar(String orderStar) {
        this.orderStar = orderStar;
    }

    public String getOrderService() {
        return orderService;
    }

    public void setOrderService(String orderService) {
        this.orderService = orderService;
    }

    public String getOrderEvaluate() {
        return orderEvaluate;
    }

    public void setOrderEvaluate(String orderEvaluate) {
        this.orderEvaluate = orderEvaluate;
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
