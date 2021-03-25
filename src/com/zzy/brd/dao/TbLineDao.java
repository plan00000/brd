package com.zzy.brd.dao;

import com.zzy.brd.entity.TbLine;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by wpr on 2021/3/23 0023.
 */
public interface TbLineDao extends BaseDao<TbLine>{
    @Query("select u from TbLine u where u.startAddress =?1 and u.endAddress =?2")
    List<TbLine> findByStartAddressAndEndAddress(String startAddress,String endAddress);
}
