package com.zzy.brd.util.weixin;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
//import com.zzy.brd.mobile.web.dto.req.weixinpost.RepWeixinQrcodeDTO;


/**
 * 通用工具类
 * 
 * @author liufeng
 */
public class WeixinCommonUtil {
    
    private static Logger log = LoggerFactory.getLogger(WeixinCommonUtil.class);
    
    // 凭证获取（GET）
    public final static String token_url =
    		"https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    public final static String code_url = 
    		"https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE#wechat_redirect";
    public final static String openid_url =  "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    public final static String info_url =  "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
   
    /**
     * 发送 https 请求
     * 
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject（通过 JSONObject.get(key) 的方式获取 JSON 对象的属性值）
     */
    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        
        JSONObject jsonObject = null;
        
        try {
            // 创建 SSLContext 对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述 SSLContext 对象中得到 SSLSocketFactory 对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);
            
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            
            // 当 outputStr 不为 null 时，向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            
            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            String result = buffer.toString();
            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            jsonObject = JSONObject.parseObject(result);
        } catch (ConnectException ce) {
            log.error(" 连接超时：{}", ce);
        } catch (Exception e) {
            log.error("https 请求异常：{}", e);
        }
        
        return jsonObject;
    }

    private static Token _token = null;

    /**
     * 获取接口访问凭证
     * 
     * @param appid 凭证
     * @param appsecret 密钥
     * @return
     */
    public synchronized static Token getToken(String appid,String appsecret) {
        if (_token != null && _token.getExpiresTime() > new Date().getTime()) {
            return _token;
        }

        Token token = null;
        String requestUrl = token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
        // 发起GET请求获取凭证
        JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);

        if (null != jsonObject) {
            try {
                token = _token = new Token();
                token.setToken(jsonObject.getString("access_token"));
                token.setExpiresIn(jsonObject.getInteger("expires_in"));
                token.setExpiresTime(new Date().getTime() + (token.getExpiresIn() - 5) * 1000);
                log.info("获取新的token: " + token.getToken() + " 过期时间: "+ token.getExpiresIn());
            } catch (JSONException e) {
                token = null;
                // 获取token失败
                log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInteger("errcode"), jsonObject.getString("errmsg"));
            }
        }
        else {
            throw new RuntimeException("获取token失败");
        }
        return token;
    }
    
    /**
     * 得到获得openid 所需要的code
     * @param appid
     * @return 带code的rul
     */
    public static String getCode(HttpServletRequest request,String appid,String red ) throws Exception{
    	//String scope="snsapi_userinfo";
    	String scope="snsapi_base";
		String redirectUrl = "";
		if (request.getServerPort() != 80) {
			redirectUrl = "http://" + request.getServerName() + ":"
					+ request.getServerPort() + request.getContextPath()
					+ "/wechat/weixinLogin?red="+red;
		} else {
			redirectUrl = "http://" + request.getServerName()
					+ request.getContextPath()
					+ "/wechat/weixinLogin?red="+red;
		}
    	try {
				redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
    	String url = code_url.replace("APPID", appid).replace("REDIRECT_URI", redirectUrl).replace("SCOPE", scope);
    	return url;
    }
    /**
     * 根据code获得openid
     * @param code
     * @return
     */
    public static String getOpenId(String code ,String appid,String secret){
    	String openid =null;
    	String url = openid_url.replace("APPID", appid).replace("SECRET", secret).replace("CODE", code);
    	JSONObject jsonObject = httpsRequest(url, "GET", null);

        if (null != jsonObject) {
            try {
                openid = jsonObject.getString("openid");
            } catch (JSONException e) {
                // 获取token失败
            	System.out.println("获取openid失败:"+jsonObject.getInteger("errcode")+"--"+jsonObject.getString("errmsg"));
                log.error("获取失败 errcode:{} errmsg:{}", jsonObject.getInteger("errcode"), jsonObject.getString("errmsg"));
            }
        }
		return openid;
    }
    
    /**
     * 根据openid 和access_token 获得用户相信信息 主要是要获得是否关注公众号
     * @param
     * @return 0没关注 ，1有关注
     */
    public static int isFollow(String openId,String appid,String secret){
        Token token = getToken(appid,secret);
        String access_token = token.getToken();
        int subscribe = -1;
    	String url = info_url.replace("ACCESS_TOKEN", access_token).replace("OPENID", openId);
    	JSONObject jsonObject = httpsRequest(url, "GET", null);
    	
    	if (null != jsonObject) {
    		try {
    			subscribe = jsonObject.getIntValue("subscribe");
    		} catch (JSONException e) {
    			// 获取token失败
    			log.error("获取失败 errcode:{} errmsg:{}", jsonObject.getInteger("errcode"), jsonObject.getString("errmsg"));
    		}
    	}
    	return subscribe;
    }
    /***
     * 根据openId 和access_token 获取用户信息。
     * 
     * */
//    public static void getPersonInformation(String openId,String appid,String srcret,WeixinUser weixinUser){
//    	Token token = getToken(appid,srcret);
//    	String access_token = token.getToken();
//    	String url = info_url.replace("ACCESS_TOKEN", access_token).replace("OPENID", openId);
//    	JSONObject jsonObject = httpsRequest(url, "GET", null);
//    	if(null!=jsonObject) {
//    		try{
//    			weixinUser.setNickname(jsonObject.getString("nickname"));
//    			int sex = jsonObject.getIntValue("sex");
//    			if(sex==0){
//    				weixinUser.setSex(WeixinUser.SexType.UNKONWN);
//    			} else if(sex==1){
//    				weixinUser.setSex(WeixinUser.SexType.MALE);
//    			} else{
//    				weixinUser.setSex(WeixinUser.SexType.FEMALE);
//    			}
//    			weixinUser.setCity(jsonObject.getString("city"));
//    			weixinUser.setCountry(jsonObject.getString("country"));
//    			weixinUser.setProvince(jsonObject.getString("province"));
//    			weixinUser.setLanguage(jsonObject.getString("language"));
//    			weixinUser.setHeadimgurl(jsonObject.getString("headimgurl"));
//    			weixinUser.setGroupid(jsonObject.getIntValue("groupid"));
//    		} catch (JSONException e) {
//    			log.error("获取失败 errcode:{} errmsg:{}", jsonObject.getInteger("errcode"), jsonObject.getString("errmsg"));
//    		}
//    	}
//    }
   

//    public static void main(String[] argv) throws InterruptedException {
//        for (int i = 0; i < 300; i++) {
//            testUser("od2wXwMU_g_nfpKcJ7DUmbWUSBxY");
//            testUser("od2wXwPvfz1za-X4XB-Bi_R7_CKA");
//            Thread.sleep(5000);
//        }
//
//    }
//
//    private static WeixinUser testUser(String openid) {
//        String appid = "wx9fec7cf60dbf5b33", src = "57085928130f2a4d49ec2809e9bc02cc";
//        WeixinUser user = new WeixinUser();
//        getPersonInformation(openid, appid, src, user);
//        System.out.println(JSON.toJSONString(user));
//        return user;
//    }
    
}