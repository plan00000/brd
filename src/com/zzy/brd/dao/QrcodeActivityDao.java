package com.zzy.brd.dao;

import com.zzy.brd.entity.QrcodeActivity;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 
 * @author lzh 2016/12/20
 *
 */
public interface QrcodeActivityDao extends BaseDao<QrcodeActivity>{

    @Query("select q from QrcodeActivity q where q.id in (?1)")
    List<QrcodeActivity> findByIds(Long[] ids);
}
