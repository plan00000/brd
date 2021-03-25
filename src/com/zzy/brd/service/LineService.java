package com.zzy.brd.service;

import com.zzy.brd.dao.TbLineDao;
import com.zzy.brd.dao.TbOrderDao;
import com.zzy.brd.dto.rep.RepSimpleMessageDTO;
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

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wpr on 2021/3/23 0023.
 */
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class LineService extends BaseService{

    @Autowired
    private TbLineDao tbLineDao;
    @Autowired
    private TbOrderDao tbOrderDao;

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

    /**
     * 新增线路
     * @param tbLine
     * @return
     */
    public RepSimpleMessageDTO addLine(TbLine tbLine){
        RepSimpleMessageDTO repSimpleMessageDTO = new RepSimpleMessageDTO();
        List<TbLine> tbLineList =tbLineDao.findByStartAddressAndEndAddress(tbLine.getStartAddress(),tbLine.getEndAddress());
        if(tbLineList !=null || tbLineList.size()!=0){
            repSimpleMessageDTO.setCode(0);
            repSimpleMessageDTO.setMes("该线路已存在");
        }
        tbLine.setCreateTime(new Date());
        tbLineDao.save(tbLine);
        repSimpleMessageDTO.setCode(1);
        repSimpleMessageDTO.setMes("新增线路成功");
        return repSimpleMessageDTO;
    }
    /**
     * 修改
     * @param tbLine
     * @return
     */
    public RepSimpleMessageDTO editLine(TbLine tbLine){
        TbLine editTbLine = tbLineDao.findOne(tbLine.getId());
        editTbLine.setStartAddress(tbLine.getStartAddress());
        editTbLine.setEndAddress(tbLine.getEndAddress());
        editTbLine.setUpdateTime(new Date());
        RepSimpleMessageDTO repSimpleMessageDTO = new RepSimpleMessageDTO();

        if(tbLineDao.save(editTbLine)==null?false:true){
            repSimpleMessageDTO.setCode(1);
            repSimpleMessageDTO.setMes("编辑成功");
        }else{
            repSimpleMessageDTO.setCode(0);
            repSimpleMessageDTO.setMes("编辑失败");
        }
        return repSimpleMessageDTO;
    }

    /**
     * 删除
     * @param lineId
     * @return
     */
    public RepSimpleMessageDTO deleteLine(long lineId){
        RepSimpleMessageDTO repSimpleMessageDTO = new RepSimpleMessageDTO();
        TbLine tbLine = tbLineDao.findOne(lineId);
        if(tbLine ==null){
            repSimpleMessageDTO.setCode(0);
            repSimpleMessageDTO.setMes("删除线路失败，线路已不存在");
            return repSimpleMessageDTO;
        }
        List<TbOrder> tbOrderList = tbOrderDao.findTbOrderByLineId(lineId);
        if(tbOrderList !=null|| tbOrderList.size()!=0){
            repSimpleMessageDTO.setCode(0);
            repSimpleMessageDTO.setMes("删除线路失败，线路已在订单中使用");
            return repSimpleMessageDTO;
        }
        return repSimpleMessageDTO;
    }

    /**
     *
     * @param lineId
     * @return
     */
    public TbLine findById(long lineId){
        return tbLineDao.findOne(lineId);
    }
}
