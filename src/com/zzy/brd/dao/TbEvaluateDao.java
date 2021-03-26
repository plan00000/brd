package com.zzy.brd.dao;

import com.zzy.brd.entity.TbEvaluate;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by wpr on 2021/3/23 0023.
 */
public interface TbEvaluateDao extends BaseDao<TbEvaluate> {
    @Query("select u from TbEvaluate u where u.driverId =?1")
    List<TbEvaluate> findTbEvaluateByDriverId(Long driverId);
}
