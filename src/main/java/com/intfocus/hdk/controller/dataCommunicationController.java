package com.intfocus.hdk.controller;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.intfocus.hdk.dao.SalesDataMapper;
import com.intfocus.hdk.dao.UsersMapper;
import com.intfocus.hdk.util.JuheDemo;
import com.intfocus.hdk.vo.SalesData;
import com.intfocus.hdk.vo.Users;

@Controller
@RequestMapping("/data")
public class dataCommunicationController implements ApplicationContextAware {
    private final static Logger log =  Logger.getLogger(dataCommunicationController.class);

    	@Resource 
    	private SalesDataMapper sdm ;
    	@Resource 
    	private         UsersMapper um ;

    private static ApplicationContext applicationContext;
    @RequestMapping(value = "submit" , method=RequestMethod.POST)
    @ResponseBody
    public Integer submit(HttpServletResponse res , HttpServletRequest req ,HttpSession session
    		              , SalesData sd ){
    	
    		log.info(JSONObject.toJSONString(sd));
    		String uuid = (String)session.getAttribute("uuid");
    		String userId = (String)session.getAttribute("userId");
    		sd.setCreatedAt(new Date());
    		sd.setStoreUuid(uuid);
			sd.setUserId(userId);
			JuheDemo.setCharset("GBK");
    		Map<String , String> mp = JuheDemo.uplaodSalesData(sd);
    		if("成功".equalsIgnoreCase(mp.get("message"))){
    			sd.setState((byte) 1);
    		}else{
    			sd.setState((byte) 0);
    			sd.setRemark(mp.get("message"));
    		}
    		sd.setCreatedAt(null);
    		return sdm.insertSelective(sd) + sd.getState() ;

    }
    
    @RequestMapping(value = "getSalesDate" , method=RequestMethod.POST)
    @ResponseBody
    public void getSalesDate(HttpServletResponse res , HttpServletRequest req ,HttpSession session
            , String startDate , String endDate , Integer page , Integer pageSize){

    	// 获得登陆用户的 ID
    	String uuid = (String) session.getAttribute("uuid");
    	Map<String , Object> where = new HashMap<String , Object>();
    	where.put("startDate", startDate);
    	where.put("endDate", endDate);
    	where.put("uuid", uuid);
    	where.put("page", page);
    	where.put("pageSize", pageSize);
    	List<SalesData> sds  = sdm.selectByWhere(where);
    	
		String json = JSONObject.toJSONString(sds);
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=UTF-8");
		Writer w;
		try {
			w = res.getWriter();
			w.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
    
    @RequestMapping(value = "scan" , method=RequestMethod.GET)
    public String scan(HttpServletResponse res , HttpServletRequest req,String uuid, String keyid ){   	
    	
    	String uri = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE#wechat_redirect";

    	uri = uri.replace("APPID", "wx1b5cef3e2e36fa21");
    	uri = uri.replace("SCOPE", "snsapi_userinfo");
    	String url = "http://www.chuanzhen.mobi/hdk/data/checkOnUser?uuid=";
//    	try {
//			uri = uri.replace("REDIRECT_URI", java.net.URLDecoder.decode("http://www.chuanzhen.mobi/hdk/data/checkOnUser?uuid="+uuid+"&keyid="+keyid,   "utf-8"));
    	  
    	   if(null != uuid){
    		   
    		   url = url+uuid ;
    		   
    		   if(null != keyid){
    			   url =url +"&keyid="+keyid;
    		   }
    		   
    		   uri = uri.replace("REDIRECT_URI", url);
    		   
    		   
    		   
    	   }else{
    		   return "forward:/error.jsp";
    	   }
    		
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
    	uri = uri.replace("SCOPE", "code");
    	log.info("wangyifeiURI"+uri);
    	return "redirect:"+uri;
    }
    
    @RequestMapping(value = "checkOnregisteredUser" , method=RequestMethod.GET)
    public String checkOnregisteredUser(HttpServletResponse res , HttpServletRequest req , HttpSession session, String code){
    	
    	
    	JuheDemo.setCharset("GBK");
        Map<String , String> param = new HashMap<String ,String>();
        param.put("appid", "wx1b5cef3e2e36fa21");
        param.put("secret", "62eb7eb80215894d51996ab26e00236b");
        param.put("code",code);
        param.put("grant_type", "authorization_code");
        String  result = "";
        
			try {
				result = JuheDemo.net("https://api.weixin.qq.com/sns/oauth2/access_token", param, "GET",null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JSONObject object = JSONObject.parseObject(result);
			
            if(null !=object.getString("errcode")){
            	log.info("userInfo request fail:openid:" + object.getString("openid")+ " error msg:" +object.getString("errmsg"));
            	return "redirect:/error.jsp";
            }else{
                Map<String , String> param2 = new HashMap<String ,String>();
                //?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
                param2.put("appid", "wx1b5cef3e2e36fa21");
                param2.put("secret", "62eb7eb80215894d51996ab26e00236b");
                param2.put("code",code);
                param2.put("grant_type", "authorization_code");
                JuheDemo.setCharset("UTF-8");
                try {
					result = JuheDemo.net("https://api.weixin.qq.com/sns/userinfo?access_token="+object.getString("access_token")+"&openid="+object.getString("openid")+"&lang=zh_CN", param, "GET",null);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                JSONObject object1 = JSONObject.parseObject(result);
                if(null !=object1.getString("errcode")){
                	log.info("userInfo request fail:openid:" + object.getString("openid")+ " error msg:" +object1.getString("errmsg"));
                	return "redirect:/error.jsp";
                }else{
                	Map<String , String> where = new HashMap<String,String>();
                    where.put("weixinid", object.getString("openid"));
                    List<Users> users = um.selectByWhere(where);
                    
                    if( 0 < users.size()){
                    	
                    	session.setAttribute("uuid",users.get(0).getStoreUuid());
                    	session.setAttribute("userId", users.get(0).getWeixinId());
                    	req.setAttribute("storeName", users.get(0).getStoreName());
                    	return "redirect:/index.jsp";
                    }else{
                    	
                    	req.setAttribute("errorMsg", "您的微信账号没有和门店关联，请联系管理员，发送给你门店二维码进行关联");
                    	
                    	return "redirect:/error.jsp";
                    }
                    
                }
            }
    }
    
    
    @RequestMapping(value = "gotoSubmit" , method=RequestMethod.GET)
    public String gotoSubmit(HttpServletResponse res , HttpServletRequest req){

       	String uri = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE#wechat_redirect";

    	uri = uri.replace("APPID", "wx1b5cef3e2e36fa21");
    	uri = uri.replace("SCOPE", "snsapi_userinfo");
    	uri = uri.replace("REDIRECT_URI", "http://www.chuanzhen.mobi/hdk/data/checkOnregisteredUser");
    	
    	return "redirect:"+uri;
    }
    
    
    @RequestMapping(value = "checkOnUser" , method=RequestMethod.GET)
    public String checkOnUser(HttpServletResponse res , HttpServletRequest req,HttpSession session,String uuid, String keyid , String code){
    	
    log.info("can:" +uuid +" " + keyid +" code : " + code);
    
    Map<String , String> where = new HashMap<String,String>();

    

    	JuheDemo.setCharset("GBK");
        Map<String , String> rs = JuheDemo.check(uuid , keyid);

        Map<String , String> param = new HashMap<String ,String>();
        //?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
        param.put("appid", "wx1b5cef3e2e36fa21");
        param.put("secret", "62eb7eb80215894d51996ab26e00236b");
        param.put("code",code);
        param.put("grant_type", "authorization_code");
        String  result = "";
        
			try {
				result = JuheDemo.net("https://api.weixin.qq.com/sns/oauth2/access_token", param, "GET",null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
            JSONObject object = JSONObject.parseObject(result);
            
            if(null !=object.getString("errcode")){
            	log.info("code request fail:" + object.getString("errmsg"));
            	req.setAttribute("errorMsg", "微信服务器出现错误，请重试");
            	return "redirect:/error.jsp";
            }else{
            	
                where.put("uuid", uuid);
                where.put("keyid", keyid);
                where.put("weixinid", object.getString("openid"));
                List<Users> users = um.selectByWhere(where);
                // 0 < users.size() 说明此微信用户已经绑定过门店，另外一种情况是重新绑定，即已经绑定过了，例如 A 绑定了门店1
                // 然后，本次扫的是门店2，此时需要另外判断。
                if(0 < users.size()){
                	session.setAttribute("uuid", uuid);
                	session.setAttribute("storeName", users.get(0).getStoreName());
                	session.setAttribute("userId", users.get(0).getWeixinId());
                	return "forward:/index.jsp";
                }else{	
                
                	// 这里的情况分两种
                	//一种是重绑定
                	// 一种是第一次绑定
                	where.clear();
                	
                	where.put("userId", object.getString("openid"));
                	
                	List<Users> regeUsers = um.selectByWhere(where);
                	
                	if( 0 < regeUsers.size()){
                		//此微信账号已经绑定过，但是本次扫码还未绑定
                		
                		req.setAttribute("redirectUrl", "/data/gotoBindingStore?code="+code);
                		req.setAttribute("oldStoreName", regeUsers.get(0).getStoreName());
                		req.setAttribute("newStoreName","");
                		return "redirect:/redirect.jsp";
                				
                	}
                	
	                Map<String , String> param2 = new HashMap<String ,String>();
	                //?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
	                param2.put("appid", "wx1b5cef3e2e36fa21");
	                param2.put("secret", "62eb7eb80215894d51996ab26e00236b");
	                param2.put("code",code);
	                param2.put("grant_type", "authorization_code");
	                JuheDemo.setCharset("UTF-8");
	                try {
						result = JuheDemo.net("https://api.weixin.qq.com/sns/userinfo?access_token="+object.getString("access_token")+"&openid="+object.getString("openid")+"&lang=zh_CN", param, "GET",null);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                JSONObject object1 = JSONObject.parseObject(result);
	                if(null !=object1.getString("errcode")){
	                	log.info("userInfo request fail:openid:" + object.getString("openid")+ " error msg:" +object1.getString("errmsg"));
	                	return "redirect:/error.jsp";
	                }else{
	                	log.info("userInfo request success: nickname"  + object1.getString("nickname") + "    openid:" + object.getString("openid"));
	                    if("成功".equalsIgnoreCase(rs.get("message"))){
	                    	Users record = new Users();
	            			//关联操作
	                    	record.setStoreUuid(uuid);
	                    	record.setStoreKeyId(keyid);
	                    	record.setStoreName(rs.get("shop"));
	                    	record.setWeixinName(object1.getString("nickname"));
	                    	record.setWeixinId(object1.getString("openid"));
	                    	um.insertSelective(record );
	                    	session.setAttribute("uuid", uuid);
	                    	session.setAttribute("userId", object.getString("openid"));
	                    	req.setAttribute("storeName", rs.get("shop"));
	                    	
	                    	return "forward:/index.jsp";
                    }
                	
                }
            }
            }
    
    return "";
}
	@Override
	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		applicationContext = ctx;
	}   
}
