package com.zzy.brd.service;

import com.zzy.brd.dao.TbPassengerDao;
import com.zzy.brd.entity.TbPassenger;
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
 * Created by wpr on 2021/3/24 0024.
 */
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class PassengerService extends BaseService{

    @Autowired
    private TbPassengerDao tbPassengerDao;

    public Page<TbPassenger> adminPassengerformList(Map<String,Object> searchParams, String sortName, String sortType, int pageNum, int pageSize){
        PageRequest pageRequest ;
        if(!StringUtils.isBlank(sortName) && !StringUtils.isBlank(sortType)){
            String sort = sortName+":"+sortType;
            pageRequest = createPageRequest(pageNum, pageSize, sort, false);
        }else{
            pageRequest = createPageRequest(pageNum,pageSize,"createTime:desc",false);
        }
        @SuppressWarnings("unchecked")
        Specification<TbPassenger> spec = (Specification<TbPassenger>) createSpecification(
                searchParams, TbPassenger.class);
        Page<TbPassenger> result = tbPassengerDao.findAll(spec, pageRequest);
        return result;
    }
    public int countTbPassengerState(TbPassenger.State state){
        return  tbPassengerDao.countTbPassengerByState(state);
    }
}
