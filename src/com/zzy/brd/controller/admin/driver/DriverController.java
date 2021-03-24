package com.zzy.brd.controller.admin.driver;

import com.zzy.brd.constant.Constant;
import com.zzy.brd.entity.TbDriver;
import com.zzy.brd.entity.TbOrder;
import com.zzy.brd.service.DriverService;
import com.zzy.brd.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wpr on 2021/3/23 0023.
 */
@Controller
@RequestMapping("admin/driver")
public class DriverController {
    @Autowired
    private DriverService driverService;

    @RequestMapping("list")
    public String list(@RequestParam(value = "page",required = true,defaultValue = "1") int pageNum
            , @RequestParam(value = "status",required = false,defaultValue = "") String status
            , @RequestParam(value = "sortName",required = false,defaultValue = "") String sortName
            , @RequestParam(value = "sortType",required = false,defaultValue = "") String sortType
            , @RequestParam(value = "searchName",required = false,defaultValue ="") String searchName
            , @RequestParam(value = "searchValue",required = false,defaultValue = "") String searchValue
            , HttpServletRequest request, Model model){
        Map<String,Object> searchParams = new HashMap<String, Object>();
        if(!StringUtils.isBlank(status)){
            if("0".equals(status)){
                searchParams.put("EQ_driverStatus", 0);
            }
            if("1".equals(status)){
                searchParams.put("EQ_driverStatus", 1);
            }
        }

        if(!StringUtils.isBlank(searchName)){
            if("userName".equals(searchName)){
                String search = "LIKE_userName";
                searchParams.put(search, searchValue);
            }
            if("carNo".equals(searchName)){
                String search = "LIKE_carNo";
                searchParams.put(search, searchValue);
            }
            if("idCard".equals(searchName)){
                String search = "LIKE_idCard";
                searchParams.put(search, searchValue);
            }

        }

        Page<TbDriver> driverforms = driverService.adminDriverformList(searchParams, sortName, sortType, pageNum, Constant.PAGE_SIZE);
        model.addAttribute("driverforms", driverforms);
        model.addAttribute("status", status);
        model.addAttribute("sortName", sortName);
        model.addAttribute("sortType", sortType);
        model.addAttribute("searchName", searchName);
        model.addAttribute("searchValue", searchValue);
        model.addAttribute("page", pageNum);
        model.addAttribute("queryStr", request.getQueryString());
        model.addAttribute("totalcount", driverforms.getTotalElements());
        return "admin/driver/driverlist";
    }
}
