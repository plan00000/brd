package com.zzy.brd.service;

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
import com.zzy.brd.dao.InformationDao;
import com.zzy.brd.entity.ActivityStarOrder;
import com.zzy.brd.entity.Information;
import com.zzy.brd.entity.Information.InformationType;

/***
 * 产品service
 * 
 * @author lzh
 *
 */
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class InformationService extends BaseService {
	private static final Logger logger = LoggerFactory
			.getLogger(InformationService.class);
	@Autowired
	private InformationDao informationDao;

	/**
	 * 根据id查找content信息
	 * 
	 * @param id
	 * @return
	 */
	public Information findById(long id) {
		return informationDao.findOne(id);
	}

	/***
	 * 活动资讯列表
	 * 
	 * @return
	 */
	public List<Information> findInfomationList() {
		return informationDao.findInfomationList(Information.Status.YES,
				Information.InformationType.ACTIVITY);
	}

	/***
	 * 帮助中心列表
	 * 
	 * @return
	 */
	public List<Information> helpCenterList() {
		return informationDao.findHelpList(Information.Status.YES,
				Information.InformationType.HELP);
	}

	/***
	 * 首页帮助中心列表
	 * 
	 * @return
	 */
	public List<Information> indexHelpList() {
		return informationDao.indexHelpList(Information.Status.YES,
				Information.InformationType.HELP);
	}

	public Information findInformationByType(InformationType type) {
		return informationDao.findInformationByType(type);
	}

	/**
	 * 编辑信息
	 * 
	 * @param SysInf
	 * @return
	 */
	public boolean editInformation(Information information) {
		return informationDao.save(information) == null ? false : true;
	}

	/***
	 * @param sortid
	 * @param type
	 */
	public Information findInformationBySortIdAndType(
			Information.InformationType type, int sortid) {
		return informationDao.findInformationBySortIdAndType(type, sortid);
	}

	/***
	 * 查找最大的sortId
	 */
	public int findMaxSortId(Information.InformationType type) {
		return informationDao.findMaxSortId(type);
	}

	/**
	 * 删除咨询
	 **/
	public void delInformation(Information information) {
		information.setStatus(Information.Status.DEL);
		information.setSortid(0);
		this.editInformation(information);
	}

	/**
	 * 获取收徒指南
	 **/
	public Information findApprentice() {
		return informationDao
				.findInformationByType(Information.InformationType.APPRENTIC);
	}

	/**
	 * 获取关于我们
	 **/
	public Information findAbout() {
		return informationDao
				.findInformationByType(Information.InformationType.ABOUT);
	}

	/**
	 * *微信首页列表获取
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param sortType
	 * @param conut
	 * @return
	 * @author 26132
	 */
	@SuppressWarnings("unchecked")
	public Page<Information> listInformation(Map<String, Object> searchParams,
			int pageNumber, String sortType, int conut) {
		searchParams.put("EQ_type", Information.InformationType.HELP);
		searchParams.put("EQ_status", Information.Status.YES);
		PageRequest pageRequest = this.createPageRequest(pageNumber, conut,
				sortType, false);
		Specification<Information> spec = (Specification<Information>) this
				.createSpecification(searchParams, Information.class);

		return informationDao.findAll(spec, pageRequest);
	}

	/***
	 * 列表获取
	 *
	 * @param searchParams
	 *
	 */
	@SuppressWarnings("unchecked")
	public Page<Information> listInformation(Map<String, Object> searchParams,
			int pageNumber, String sortType) {

		if (sortType != null && sortType.trim().length() > 0) {
			sortType = "addDate:" + sortType;
		} else {
			sortType = "sortid:asc";
		}
		PageRequest pageRequest = this.createPageRequest(pageNumber,
				Constant.PAGE_SIZE, sortType, false);
		Specification<Information> spec = (Specification<Information>) this
				.createSpecification(searchParams, Information.class);

		return informationDao.findAll(spec, pageRequest);
	}
}
