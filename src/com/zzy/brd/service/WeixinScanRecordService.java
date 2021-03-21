package com.zzy.brd.service;

import com.zzy.brd.dao.WeixinScanRecordDao;
import com.zzy.brd.dao.WeixinUserDao;
import com.zzy.brd.entity.WeixinScanRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * Created by yonixee on 2017/1/21.
 */
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class WeixinScanRecordService  extends BaseService {

    @Autowired
    private WeixinScanRecordDao weixinScanRecordDao;

    public WeixinScanRecord findRecordByDate(long sceneId, String date) {
        return weixinScanRecordDao.findRecordByDate(sceneId, date);
    }

    public List<WeixinScanRecord> findRecordByDate(long sceneId, String startDate, String endDate) {
        return weixinScanRecordDao.findRecordByDate(sceneId, startDate, endDate);
    }

    public void editRecord(WeixinScanRecord record) {
        weixinScanRecordDao.save(record);
    }
}
