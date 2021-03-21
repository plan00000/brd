package com.zzy.brd.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zzy.brd.constant.Constant;
import com.zzy.brd.dao.AdvertisementDao;
import com.zzy.brd.entity.Advertisement;
import com.zzy.brd.entity.Advertisement.AdPositionType;
import com.zzy.brd.entity.Advertisement.AdSourceType;


/***
 * 产品service
 * 
 * @author csy 2016-09-26
 *
 */
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class AdvertisementService extends BaseService {
	private static final Logger logger = LoggerFactory.getLogger(AdvertisementService.class);
	@Autowired
	private AdvertisementDao advertisementDao;
	/**
	 * 得到banner列表
	 * @return
	 */
	public List<Advertisement> listAdvertisements(AdSourceType source) {
		return advertisementDao.listAdvertisements(AdPositionType.BANNER,source);
	}
	
	public Advertisement getAdvertisementByPosition(AdPositionType adPositionType){
		return advertisementDao.getAdvertisementByPosition(adPositionType);
	}
	/** Pc根据位置获取图片*/
	public Advertisement getPCAdvertisementByPosition(AdPositionType adPositionType){
		return advertisementDao.getPCAdvertisementByPosition(adPositionType);
	}
	
	@SuppressWarnings("unchecked")
	public Page<Advertisement> listAdvertisement(Map<String,Object> searchParams,int pageNumber){
		PageRequest pageRequest = this.createPageRequest(pageNumber, Constant.PAGE_SIZE, "position:asc", false);
		Specification<Advertisement> spec = (Specification<Advertisement>) this.createSpecification(searchParams, Advertisement.class);
		
		return advertisementDao.findAll(spec, pageRequest);
	}
	
	/**
	 *根据位置和类型获取是否存在广告 
	 **/
	public Advertisement getAdvertisementByPositionAndType(Advertisement.AdPositionType position, Advertisement.AdSourceType source){
		return advertisementDao.getAdvertisementByPositionAndType(position,source);
	}
	
	/**获取滚播图列表*/
	public List<Advertisement> getAdvertisementByType(Advertisement.AdSourceType source){
		return advertisementDao.getAdvertisementByType(source);
	}
	
}
