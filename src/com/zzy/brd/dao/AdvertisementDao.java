package com.zzy.brd.dao;


import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.zzy.brd.entity.Advertisement;
import com.zzy.brd.entity.Advertisement.AdPositionType;
import com.zzy.brd.entity.Advertisement.AdSourceType;

/**
 * 
 * @author csy 2016-09-26
 *
 */
public interface AdvertisementDao extends BaseDao<Advertisement> {
	
	/**
	 * 后台获取banners
	 * @return
	 */
	@Query("select a from Advertisement a where a.position = ?1 and a.source = ?2 order by a.id")
	List<Advertisement> listAdvertisements(AdPositionType adPositionType,AdSourceType source);
	
	@Query("select a from Advertisement a where a.position = ?1 ")
	public Advertisement getAdvertisementByPosition(AdPositionType adPositionType);
	
	@Query("select a from Advertisement a where a.position = ?1 and a.source = 0")
	public Advertisement getPCAdvertisementByPosition(AdPositionType adPositionType);
	
	@Query("select a from Advertisement a where a.position= ?1 and a.source = ?2")
	public Advertisement getAdvertisementByPositionAndType(AdPositionType adPositionType,Advertisement.AdSourceType source);
	
	@Query("select a from Advertisement a where a.position= '0' and a.source =?1 ")
	public List<Advertisement> getAdvertisementByType(Advertisement.AdSourceType source);
	
}
