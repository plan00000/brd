package com.zzy.brd.service;

import com.zzy.brd.dao.TbLineDao;
import com.zzy.brd.entity.TbLine;
import com.zzy.brd.entity.TbOrder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Created by wpr on 2021/3/23 0023.
 */
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class LineService extends BaseService{

    @Autowired
    private TbLineDao tbLineDao;

    public Page<TbLine> adminLineList(Map<String,Object> searchParams, String sortName, String sortType, int pageNum, int pageSize){
        PageRequest pageRequest ;
        if(!StringUtils.isBlank(sortName) && !StringUtils.isBlank(sortType)){
            String sort = sortName+":"+sortType;
            pageRequest = createPageRequest(pageNum, pageSize, sort, false);
        }else{
            pageRequest = createPageRequest(pageNum,pageSize,"createTime:desc",false);
        }
        @SuppressWarnings("unchecked")
        Specification<TbLine> spec = (Specification<TbLine>) createSpecification(
                searchParams, TbOrder.class);
        Page<TbLine> result = tbLineDao.findAll(spec, pageRequest);
        return result;
    }
}
