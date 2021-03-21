package com.zzy.brd.util.ipparse;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.zzy.brd.constant.Constant;
/*import com.zzy.brd.entity.Weather;
import com.zzy.brd.service.WeatherService;*/
import com.zzy.brd.util.cache.CommCache;



/**
 * 线程池，根据IP获取天气的线程池
 * @author zzy 2015年2月4日
 */
public class IpParseThread extends Thread {
	/**
	 * 创建线程池
	 */
	static private ExecutorService executor = Executors.newFixedThreadPool(10);
	
	/**
	 * 单例对象
	 */
	static private IpParseThread shareThread = new IpParseThread();
	
	/**
	 * servlet上下文
	 */
	private static ServletContextEvent servletContextEvent;

	/**
	 * 单例，获取单例对象
	 */
	public static IpParseThread getShareThread() {
			return shareThread;
	}

	@Override
	public void run() {
		while (true) {
			try {
				//从消息队列中取出IP消息，如果为空则等待
				final String ip = IpParseQueue.getShareQueue().popMessages();
				if(null==ip) {
					synchronized (this) {
						// 等待
						wait();
						continue;
					}
				}
				
				//启动一个线程来进行处理
				executor.execute(new Runnable() {
					@Override
					public void run() {
						//获取天气业务操作对象
						WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContextEvent().getServletContext());
//						WeatherService service = (WeatherService) context.getBean("weatherService");
						//查询数据库时是否存在该IP天气信息
//						Weather wIp = service.findByIp(ip);
//						if(null!=wIp){//存在则直接写入缓存退出
//							//写入缓存
//							CommCache.getInstance().set(IpWeather.prefix+ip, wIp.getInfo());
//							CommCache.getInstance().set(IpWeather.prefixCityname+ip, wIp.getCityname());
//							return;
//						}
						//从纯真IP数据库中获取到对应的地址信息
						IPLocation location = IpWeather.getIpSeeker().getIPLocation(ip);
						String place = location.getCountry()+location.getArea();
						//遍历CITY数据获取城市编号
						String cityno;
						int index = -1;
						for(int i=0; i<City.names.size(); i++){
							String name = City.names.get(i);
							if(place.indexOf(name)!=-1){
								index = i;
								break;
							}
						}
						if(index==-1){//不存在的城市编号
							cityno = IpWeather.defaultCityno;
						}else{
							cityno = City.areaids.get(index);
						}
						//查询数据库是否存在该城市天气信息
						/*String cacheCity = null;
//						Weather weather = service.findByCityno(cityno);
						Weather w = new Weather();
						w.setIp(ip);
						w.setCityno(cityno);
						if(index==-1){//不存在的城市编号
							w.setCityname(Constant.UNKNOWN_REGION);
							cacheCity = Constant.UNKNOWN_REGION; 
						}else{
							w.setCityname(City.names.get(index));
							cacheCity = City.names.get(index)+"|"+City.districts.get(index)+"|"+City.provs.get(index);
						}*/
//						if(null==weather){//不存在
//							//通讯获取数据
//							String result = SmartWeatherBaseInfo.getWeatherInfo(cityno);
//							if(null==result || result.length()==0){//出错
//								System.out.println("通讯获取天气信息出错");
//								return;
//							}
//					        //转换天气数据并获取需要的信息
//					        SmartWeather sw = SmartWeatherBaseInfo.fromJson(result);
//					        if(null==sw){
//					        	System.out.println("通讯获取天气信息出错");
//					        	return;
//					        }
//					        try {
//						        w.setInfo(SmartWeatherBaseInfo.getChineseWeather(sw, null));
//							} catch (Exception e) {
//								// TODO: handle exception
//								//不打印错误信息
//								return ;
//							}
//						}else{//存在
//							w.setInfo(weather.getInfo());
//						}
						//写入库表
//						service.getWeatherDao().save(w);
						//写入缓存
						/*CommCache.getInstance().set(IpWeather.prefix+ip, w.getInfo());
						CommCache.getInstance().set(IpWeather.prefixCityname+ip, cacheCity);*/
					}
				});
			} catch (Exception e) {
				System.out.println("通讯获取天气信息出错");
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return the servletContextEvent
	 */
	public ServletContextEvent getServletContextEvent() {
		return servletContextEvent;
	}

	/**
	 * @param servletContextEvent the servletContextEvent to set
	 */
	public void setServletContextEvent(ServletContextEvent servletContextEvent) {
		IpParseThread.servletContextEvent = servletContextEvent;
	}

}
