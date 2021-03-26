package com.zzy.brd.service;

import com.zzy.brd.dao.TbDriverDao;
import com.zzy.brd.dao.TbEvaluateDao;
import com.zzy.brd.dao.TbOrderDao;
import com.zzy.brd.dto.rep.RepSimpleMessageDTO;
import com.zzy.brd.entity.TbDriver;
import com.zzy.brd.entity.TbEvaluate;
import com.zzy.brd.entity.TbOrder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by wpr on 2021/3/23 0023.
 */
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class DriverService extends BaseService{

    @Autowired
    private TbDriverDao tbDriverDao;
    @Autowired
    private TbEvaluateDao tbEvaluateDao;

    /**
     * 列表
     * @param searchParams
     * @param sortName
     * @param sortType
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Page<TbDriver> adminDriverformList(Map<String,Object> searchParams, String sortName, String sortType, int pageNum, int pageSize){
        PageRequest pageRequest ;
        if(!StringUtils.isBlank(sortName) && !StringUtils.isBlank(sortType)){
            String sort = sortName+":"+sortType;
            pageRequest = createPageRequest(pageNum, pageSize, sort, false);
        }else{
            pageRequest = createPageRequest(pageNum,pageSize,"createTime:desc",false);
        }
        @SuppressWarnings("unchecked")
        Specification<TbDriver> spec = (Specification<TbDriver>) createSpecification(
                searchParams, TbDriver.class);
        Page<TbDriver> result = tbDriverDao.findAll(spec, pageRequest);
        return result;
    }

    /**
     * 添加订单
     * @param tbDriver
     * @return
     */
    public RepSimpleMessageDTO addDriver(TbDriver tbDriver){
        RepSimpleMessageDTO repSimpleMessageDTO = new RepSimpleMessageDTO();
        List<TbDriver> checkTbDriverList =tbDriverDao.findTbDriverByMobileno(tbDriver.getMobileno());
        if(checkTbDriverList !=null || checkTbDriverList.size()!=0){
            repSimpleMessageDTO.setCode(0);
            repSimpleMessageDTO.setMes("该手机号已注册过");
        }
        if(tbDriverDao.save(tbDriver)==null?false:true){
            repSimpleMessageDTO.setCode(1);
            repSimpleMessageDTO.setMes("新增成功");
        }else{
            repSimpleMessageDTO.setCode(0);
            repSimpleMessageDTO.setMes("新增失败");
        }
        return repSimpleMessageDTO;
    }

    /**
     * 修改
     * @param tbDriver
     * @return
     */
    public RepSimpleMessageDTO editDriver(TbDriver tbDriver){
        TbDriver editTbDriver = tbDriverDao.findOne(tbDriver.getId());
        editTbDriver.setMobileno(tbDriver.getMobileno());
        editTbDriver.setPassword(tbDriver.getPassword());
        editTbDriver.setUserName(tbDriver.getUserName());
        editTbDriver.setCarNo(tbDriver.getCarNo());
        editTbDriver.setIdCard(tbDriver.getIdCard());
        editTbDriver.setDriverNo(tbDriver.getDriverNo());
        editTbDriver.setCarMark(tbDriver.getCarMark());
        editTbDriver.setCarColor(tbDriver.getCarColor());
        RepSimpleMessageDTO repSimpleMessageDTO = new RepSimpleMessageDTO();

        if(tbDriverDao.save(editTbDriver)==null?false:true){
            repSimpleMessageDTO.setCode(1);
            repSimpleMessageDTO.setMes("编辑成功");
        }else{
            repSimpleMessageDTO.setCode(0);
            repSimpleMessageDTO.setMes("编辑失败");
        }
        return repSimpleMessageDTO;
    }

    /**
     *
     * @param driverId
     * @return
     */
    public TbDriver findById(long driverId){
        return tbDriverDao.findOne(driverId);
    }

    public Page<TbEvaluate> tbEvaluateformList(Map<String,Object> searchParams, String sortName, String sortType, int pageNum, int pageSize){
        PageRequest pageRequest ;
        if(!StringUtils.isBlank(sortName) && !StringUtils.isBlank(sortType)){
            String sort = sortName+":"+sortType;
            pageRequest = createPageRequest(pageNum, pageSize, sort, false);
        }else{
            pageRequest = createPageRequest(pageNum,pageSize,"createTime:desc",false);
        }
        @SuppressWarnings("unchecked")
        Specification<TbEvaluate> spec = (Specification<TbEvaluate>) createSpecification(
                searchParams, TbEvaluate.class);
        Page<TbEvaluate> result = tbEvaluateDao.findAll(spec, pageRequest);
        return result;
    }

    public int countDriverState(TbDriver.DriverStatus driverStatus){
        return  tbDriverDao.countDriverState(driverStatus);
    }
}
