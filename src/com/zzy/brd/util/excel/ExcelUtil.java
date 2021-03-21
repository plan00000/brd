/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * 
 *******************************************************************************/
package com.zzy.brd.util.excel;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springside.modules.web.struts2.Struts2Utils;

/**
 * Excel 文件导出工具
 * 
 * @author wengzc 2015年3月9日
 */
public class ExcelUtil {
	
	/**
	 * 导出数据封装 
	 */
	public static class ExcelBean {
		private String name;
		
		private String sheetName;
		
		private String[] titles;
		
		private List<String[]> dataList;
		
		private boolean headBold = true;
		
		private int columnWidth = 6000;
		
		public ExcelBean(String name, String sheetName, String[] titles){
			this.name = name;
			this.sheetName = sheetName;
			this.titles = titles;
			this.dataList = new ArrayList<String[]>();
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getSheetName() {
			return sheetName;
		}

		public void setSheetName(String sheetName) {
			this.sheetName = sheetName;
		}

		public String[] getTitles() {
			return titles;
		}

		public void setTitles(String[] titles) {
			this.titles = titles;
		}

		public List<String[]> getDataList() {
			return dataList;
		}

		public void setDataList(List<String[]> dataList) {
			this.dataList = dataList;
		}
		
		public boolean isHeadBold() {
			return headBold;
		}

		public void setHeadBold(boolean headBold) {
			this.headBold = headBold;
		}

		public int getColumnWidth() {
			return columnWidth;
		}

		public void setColumnWidth(int columnWidth) {
			this.columnWidth = columnWidth;
		}

		public void add(String[] data){
			this.dataList.add(data);
		}
		
	}

	public static void export( HttpServletResponse response, ExcelBean excelBean) throws Exception{
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(excelBean.getSheetName());
		HSSFRow row = sheet.createRow(0);
		
		//设置样式
		HSSFCellStyle style = wb.createCellStyle();
		if(excelBean.isHeadBold()){
			HSSFFont headfont = wb.createFont(); 
		    headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style.setFont(headfont);
		}
		
		HSSFCell cell;
		String[] titles = excelBean.getTitles();
		for(int i=0; i < titles.length; i++){
			cell= row.createCell(i);
			cell.setCellValue(titles[i]);
			cell.setCellStyle(style);
			sheet.setColumnWidth(i, excelBean.getColumnWidth());
		}
		
		int rowNumber = 1;
		for(String[] data : excelBean.getDataList()){
			row = sheet.createRow(rowNumber ++ );
			for(int j=0; j<data.length; j ++){
				cell = row.createCell(j);
				cell.setCellValue(data[j]);
			}
		}
		
		response.setContentType("application/octet-stream;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		
		String filename = excelBean.getName();
        filename = new String(filename.replaceAll("\\s|;", "").getBytes("gbk"), "ISO8859-1");
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + filename);
        OutputStream ouputStream = response.getOutputStream();   
        wb.write(ouputStream);   
        ouputStream.flush();   
        ouputStream.close();  
	}
	
}
