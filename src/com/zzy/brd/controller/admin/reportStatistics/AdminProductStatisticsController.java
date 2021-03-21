package com.zzy.brd.controller.admin.reportStatistics;


import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zzy.brd.dto.rep.admin.reportStatistics.RepProductStatistics;
import com.zzy.brd.dto.rep.admin.reportStatistics.RepProductStatisticsList;
import com.zzy.brd.service.ProductService;

/**
 * @author:csy
 *    2016年10月18日
 **/
@Controller
@RequestMapping(value="admin/reportStatistics/productStatistics")
public class AdminProductStatisticsController {
	
	@Autowired
	private ProductService productService;
	
	/**
	 * 转到产品统计
	 * @param model
	 * @return
	 */
	@RequestMapping(value="productStatistics")
	public String productStatistics(
			@RequestParam(value="page",required=true,defaultValue="1") int pageNumber,
			HttpServletRequest request,Model model){
		Map<String, Object> map = productService.getProductCount();
		int productCount =  (Integer) map.get("productCount");
		int productTypeCount = (Integer) map.get("productTypeCount");
		ArrayList<RepProductStatistics> repList = productService.getBillTypeList();
		Page<RepProductStatisticsList> page = productService.ProductStatisticsList(pageNumber);
		
		model.addAttribute("repList", repList);
		model.addAttribute("page", page);
		model.addAttribute("productCount", productCount);
		model.addAttribute("productTypeCount", productTypeCount);
		return "admin/reportStatistics/productStatistics";
	}
	
}
