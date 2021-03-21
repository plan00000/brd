package com.zzy.brd.util.phone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.zzy.brd.constant.ConfigSetting;
import com.zzy.brd.dto.rep.phone.RepPhoneDTO;



public class PhoneUtils {

	private static String phoneHomeUrl = "http://apis.baidu.com/showapi_open_bus/mobile/find?num={num}";
	
	private static String httpUrl = "http://apis.baidu.com/apistore/mobilephoneservice/mobilephone";
	
	public static String getPhoneHome(String num){
		String info = "未知";
		RestTemplate template = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("apikey", ConfigSetting.baidu_api_key);
		
		HttpEntity<Void> entity = new HttpEntity<Void>(headers);
		ResponseEntity<String> responseEntity = template.exchange(phoneHomeUrl, HttpMethod.GET, entity, String.class, num);
		String body = responseEntity.getBody();
		if (body.contains("city") && body.contains("prov")){
			int ic = body.indexOf("city")+7;
			int icc = body.indexOf("\"", ic);
			String city = body.substring(ic, icc);
			ic =  body.indexOf("prov")+7;
			icc = body.indexOf("\"", ic);
			String prov = body.substring(ic, icc);
			if (city.endsWith("市")){
				city = city.substring(0,city.indexOf("市"));
			}
			info = prov + " " + city;
		}
		return info;
	}
	//得到手机号的所属省份
	/*public static String getPhoneProv(String num){
		String info = "未知";
		RestTemplate template = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("apikey", ConfigSetting.baidu_api_key);
		
		HttpEntity<Void> entity = new HttpEntity<Void>(headers);
		ResponseEntity<String> responseEntity = template.exchange(phoneHomeUrl, HttpMethod.GET, entity, String.class, num);
		String body = responseEntity.getBody();
		if (body.contains("city") && body.contains("prov")){
			int ic = body.indexOf("city")+7;
			int icc = body.indexOf("\"", ic);
			String city = body.substring(ic, icc);
			ic =  body.indexOf("prov")+7;
			icc = body.indexOf("\"", ic);;
			String prov = body.substring(ic, icc);
			if (city.endsWith("市")){
				city = city.substring(0,city.indexOf("市"));
			}
			info = prov;
		}
		System.out.println("归属地查询:"+num+"---:"+info);
		return info;
	}*/
	//淘宝网得到归属地
	/**
	 * @param num
	 * @return
	 */
	/**
	 * @param num
	 * @return
	 */
	/**
	 * @param num
	 * @return
	 */
	public static String getPhoneProv(String num){
		String purl = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel="+num+"";
		//String prov = "福建";
		String prov = "未知";
		BufferedReader reader = null;
		StringBuffer sbf = new StringBuffer();
		String result =null;
		 try {
		       URL url = new URL(purl);
		       HttpURLConnection connection = (HttpURLConnection) url
		               .openConnection();
		      connection.setRequestMethod("GET");
		      
		      connection.connect();
		      InputStream is = connection.getInputStream();
		       
		      reader = new BufferedReader(new InputStreamReader(is, "GB2312"));
		      String strRead = null;
		      while ((strRead = reader.readLine()) != null) {
		          sbf.append(strRead);
		          sbf.append("\r\n");
		      }
		      reader.close();
		      result = sbf.toString();
		   } catch (Exception e) {
		       e.printStackTrace();
		   }
		
		result = StringUtils.substringAfter(result, "__GetZoneResult_");
		result = result.substring(3);		
        try {
			JSONObject dataJson=JSONObject.fromObject(result);
			Object phone= dataJson.get("province");
			Object mts = dataJson.get("mts");	
			if(mts!=null){
				prov =phone.toString();
			}
        } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("查询号码:"+num+"---:"+prov);
        return  prov;
	
	}
	public static String getPhoneProv1(String num) {
		String prov = "未知";
		
	    BufferedReader reader = null;
	    String result = null;
	    StringBuffer sbf = new StringBuffer();
	    String httpArg = "tel="+num;
	    httpUrl = httpUrl + "?" + httpArg;

	    try {
	        URL url = new URL(httpUrl);
	        HttpURLConnection connection = (HttpURLConnection) url
	                .openConnection();
	        connection.setRequestMethod("GET");
	        // 填入apikey到HTTP header
	        connection.setRequestProperty("apikey",  ConfigSetting.baidu_api_key);
	        connection.connect();
	        InputStream is = connection.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String strRead = null;
	        while ((strRead = reader.readLine()) != null) {
	            sbf.append(strRead);
	            sbf.append("\r\n");
	        }
	        reader.close();
	        result = sbf.toString();
	        
	        RepPhoneDTO rep = (RepPhoneDTO) JSONObject.toBean(
					JSONObject.fromObject(result), RepPhoneDTO.class);
	        
	        if("0".equals(rep.getErrNum())){
	        	prov = rep.getRetData().getProvince();
	        }
	        if("-1".equals(rep.getErrNum())){
	        	prov = "未知";
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
//	    JSONObject jsonObject = JSONObject.fromObject(result);
	  
	   	
	    return prov;
	}
	public static void main(String[] args) {
		System.out.println(getPhoneProv("15392022257"));
		///System.out.println(getPhoneProv("13850053157"));
		//String jsonResult = getPhoneProv("13850053157");
		//System.out.println(jsonResult);
	}
}
