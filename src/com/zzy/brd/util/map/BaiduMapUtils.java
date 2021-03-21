package com.zzy.brd.util.map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.zzy.brd.dto.rep.map.baidu.RepCreatePoiDTO;
import com.zzy.brd.dto.req.map.baidu.ReqCreatePoiDTO;
import com.zzy.brd.util.map.baidu.dto.rep.geocoder.RepGeocoderResolve;
import com.zzy.brd.util.map.baidu.req.geocoder.ReqGeocoderResolve;

/**
 * @author:xpk
 *    2016年11月25日-下午3:28:57
 * 百度地图工具类
 * 
 **/
public class BaiduMapUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(BaiduMapUtils.class);
	
	/**
	 * 创建点
	 * @param reqCreatePoiDTO
	 * 
	 * */
	public static RepCreatePoiDTO createPoi(ReqCreatePoiDTO poi){
		RepCreatePoiDTO rep = null;
		try{
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8"));
			HttpEntity<String> entity = new HttpEntity<String>(poi.toQueryString(), headers);
			String retStr = restTemplate.postForObject(BaiduMapRequestUtils.createPoi, entity, String.class);
			rep = (RepCreatePoiDTO) JSONObject.toBean(JSONObject.fromObject(retStr),RepCreatePoiDTO.class);
		} catch(Exception e){
			logger.error("创建poi点失败:"+e.getMessage());
		}
		return rep;
	}
	
	/***
	 * 地址解析成坐标
	 * */
	public static RepGeocoderResolve addressGeocoding(ReqGeocoderResolve req ){
		RepGeocoderResolve rep =null;
		try{
			RestTemplate restTemplate = new RestTemplate();
			System.out.println(BaiduMapRequestUtils.geocoder + "?"
					+ req.toQueryString());
			String str = restTemplate.getForObject(BaiduMapRequestUtils.geocoder + "?"
					+ req.toQueryString(), String.class);
			rep = (RepGeocoderResolve) JSONObject.toBean(
					JSONObject.fromObject(str), RepGeocoderResolve.class);
		}catch(Exception e){
			logger.error("地址解析失败:"+e.getMessage());
		}		
		return rep;
	}
	
	
}
