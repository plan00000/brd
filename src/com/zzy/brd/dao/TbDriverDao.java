package com.zzy.brd.dao;

import com.zzy.brd.entity.TbDriver;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by wpr on 2021/3/23 0023.
 */
public interface TbDriverDao extends BaseDao<TbDriver>{

    @Query("select u from TbDriver u where u.mobileno =?1")
    List<TbDriver> findTbDriverByMobileno(String mobileno);

    @Query("select count(u) from TbDriver u where u.driverStatus =?1")
    int countDriverState( TbDriver.DriverStatus driverStatus);
}
