package com.zzy.brd.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zzy.brd.dao.QrcodeActivityDao;
import com.zzy.brd.entity.Product;
import com.zzy.brd.entity.QrcodeActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 二维码活动
 * @author lzh 2016/12/20
 *
 */
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class QrcodeActivityService extends BaseService{
	private static final Logger logger = LoggerFactory.getLogger(QrcodeActivityService.class);
	@Autowired
	private QrcodeActivityDao qrcodeActivityDao;
	
	/** 获取二维码活动列表*/
	public Page<QrcodeActivity> getQrCodeActivityList(int pageNumber,int pageSize){
		PageRequest pageRequest = createPageRequest(pageNumber, pageSize,"createTime:desc", false);
		return qrcodeActivityDao.findAll(pageRequest);
	}


	public List<QrcodeActivity> findByIds(Long[] ids) {
		return qrcodeActivityDao.findByIds(ids);
	}
	/**
	 * 添加和修改
	 * @param qrcodeActivity
	 * @return
	 */
	public boolean addAndEdit(QrcodeActivity qrcodeActivity){
		return qrcodeActivityDao.save(qrcodeActivity)==null? false:true;
	}
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public QrcodeActivity findById(long id){
		return qrcodeActivityDao.findOne(id);
	}
	
}
