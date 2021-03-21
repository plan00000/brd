package com.zzy.brd.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.zzy.brd.entity.Information;
import com.zzy.brd.entity.Information.InformationType;

/**
 * 
 * @author csy 2016-09-26
 */
public interface InformationDao extends BaseDao<Information> {

	@Query("select i from Information i where i.status=?1 and i.type=?2 order by i.sortid,i.addDate desc")
	public List<Information> findInfomationList(Information.Status Status, Information.InformationType type);

	@Query("select i from Information i where i.status=?1 and i.type=?2 and i.sortid  BETWEEN 1 AND 5 order by i.sortid ,i.addDate desc" )
	public List<Information> indexHelpList(Information.Status Status, Information.InformationType type);
	
	@Query("select i from Information i where i.status=?1 and i.type=?2 order by i.sortid,i.addDate desc  ")
	public List<Information> findHelpList(Information.Status Status, Information.InformationType type);
	
	@Query("select i from Information i where i.type = ?1")
	public Information findInformationByType(InformationType type);
	
	@Query("select i from Information i where i.type = ?1 and i.sortid=?2")
	public Information findInformationBySortIdAndType(InformationType type,int sortid);
	
	@Query("select max(i.sortid) from Information i where i.type=?1 ")
	public int findMaxSortId(InformationType type);

}
