package com.zzy.brd.dao;

import com.zzy.brd.entity.WeixinScanRecord;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface WeixinScanRecordDao extends BaseDao<WeixinScanRecord>{

    @Query("select w from WeixinScanRecord w where w.date=?2 and w.sceneId =?1 ")
    public WeixinScanRecord findRecordByDate(long sceneId, String date);

    @Query("select w from WeixinScanRecord w where w.date>=?2 and w.date<=?3 and w.sceneId =?1 ")
    public List<WeixinScanRecord> findRecordByDate(long sceneId, String startDate, String endDate);
}
