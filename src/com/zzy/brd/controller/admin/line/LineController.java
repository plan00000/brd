package com.zzy.brd.controller.admin.line;

import com.zzy.brd.constant.Constant;
import com.zzy.brd.dto.rep.RepSimpleMessageDTO;
import com.zzy.brd.entity.TbLine;
import com.zzy.brd.entity.TbOrder;
import com.zzy.brd.mobile.util.ShiroUtil;
import com.zzy.brd.service.LineService;
import com.zzy.brd.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wpr on 2021/3/23 0023.
 */
@Controller
@RequestMapping("admin/line")
public class LineController {
    @Autowired
    private LineService lineService;

    /**
     * 获取线路列表
     * @param pageNum
     * @param status
     * @param sortName
     * @param sortType
     * @param searchName
     * @param searchValue
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("list")
    public String list(@RequestParam(value = "page",required = true,defaultValue = "1") int pageNum
            , @RequestParam(value = "status",required = false,defaultValue = "") String status
            , @RequestParam(value = "sortName",required = false,defaultValue = "") String sortName
            , @RequestParam(value = "sortType",required = false,defaultValue = "") String sortType
            , @RequestParam(value = "searchName",required = false,defaultValue ="") String searchName
            , @RequestParam(value = "searchValue",required = false,defaultValue = "") String searchValue
            , HttpServletRequest request, Model model){
        Map<String,Object> searchParams = new HashMap<String, Object>();
        /*if(!StringUtils.isBlank(status)){
            if("0".equals(status)){
                searchParams.put("EQ_orderStatus", "0");
            }
            if("1".equals(status)){
                searchParams.put("EQ_orderStatus", "1");
            }
            if("2".equals(status)){
                searchParams.put("EQ_orderStatus", "2");
            }
            if("3".equals(status)){
                searchParams.put("EQ_orderStatus", "3");
            }
            if("4".equals(status)){
                searchParams.put("EQ_orderStatus", "4");
            }
            if("5".equals(status)){
                searchParams.put("EQ_orderStatus", "5");
            }
        }*/
        if(!StringUtils.isBlank(searchName)){
            if("startAddress".equals(searchName)){
                String search = "LIKE_startAddress";
                searchParams.put(search, searchValue);
            }
            if("endAddress".equals(searchName)){
                String search = "LIKE_endAddress";
                searchParams.put(search, searchValue);
            }

        }

        Page<TbLine> lineforms = lineService.adminLineList(searchParams, sortName, sortType, pageNum, Constant.PAGE_SIZE);
        model.addAttribute("lineforms", lineforms);
        model.addAttribute("status", status);
        model.addAttribute("sortName", sortName);
        model.addAttribute("sortType", sortType);
        model.addAttribute("searchName", searchName);
        model.addAttribute("searchValue", searchValue);
        model.addAttribute("page", pageNum);
        model.addAttribute("queryStr", request.getQueryString());
        model.addAttribute("totalcount", lineforms.getTotalElements());
        return "admin/line/linelist";
    }

    /**
     * 跳转到新增司机页面
     * @return
     */
    @RequestMapping(value = "toAddLine")
    public String toAddDriver() {

        return "admin/line/addLine";
    }

    @RequestMapping(value = "addLine")
    @ResponseBody
    public RepSimpleMessageDTO addLine(
            @RequestParam(value = "startAddress", required = false) String startAddress,
            @RequestParam(value = "endAddress", required = false) String endAddress,
            HttpServletRequest request) {
        TbLine tbLine = new TbLine();
        tbLine.setStartAddress(startAddress);
        tbLine.setEndAddress(endAddress);
        RepSimpleMessageDTO res = lineService.addLine(tbLine);
        return res;
    }
    /**
     * 跳转到编辑页面
     * @param pageNumber
     * @param type
     * * @param model
     * @return
     */
    @RequestMapping(value = "toEditLine/{lineId}")
    public String toEditDriver(
            @PathVariable long lineId,
            @RequestParam(value = "page", required = true, defaultValue = "1") int pageNumber,
            @RequestParam(value = "type", required = false, defaultValue = "brokerage") String type,
            Model model) {
        TbLine tbLine = lineService.findById(lineId);

        model.addAttribute("tbLine", tbLine);
        model.addAttribute("type", type);
        return "admin/line/editLine";
    }

    /**
     * 编辑
     * @param phone
     * @param password

     * @param request
     * @return
     */
    @RequestMapping(value = "editLine")
    @ResponseBody
    public RepSimpleMessageDTO editLine(
            @RequestParam(value="id",required = true) long id,
            @RequestParam(value = "startAddress", required = false) String startAddress,
            @RequestParam(value = "endAddress", required = false) String endAddress,
            HttpServletRequest request) {
        TbLine tbLine = new TbLine();
        tbLine.setEndAddress(startAddress);
        tbLine.setEndAddress(endAddress);
        RepSimpleMessageDTO res = lineService.editLine(tbLine);
        return res;
    }
    /**
     * 删除线路
     * @param id
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public RepSimpleMessageDTO deleteProduct(@RequestParam("id") long id
            ,HttpServletRequest request){
        RepSimpleMessageDTO rep = lineService.deleteLine(id);
        return rep;
    }
}
