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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.intfocus.hdk.dao.JsapiTicketMapper;
import com.intfocus.hdk.dao.JsapiTokenMapper;
import com.intfocus.hdk.dao.SalesDataMapper;
import com.intfocus.hdk.dao.StateMapper;
import com.intfocus.hdk.dao.UsersMapper;
import com.intfocus.hdk.util.JuheDemo;
import com.intfocus.hdk.util.StaticVariableUtil;
import com.intfocus.hdk.util.WeiXinUserInfoUtil;
import com.intfocus.hdk.vo.SalesData;
import com.intfocus.hdk.vo.State;
import com.intfocus.hdk.vo.Users;

@Controller
@RequestMapping("/state")
public class StateController implements ApplicationContextAware {
    private final static Logger log =  Logger.getLogger(StateController.class);
    
    
    @Resource
    private StateMapper statemapper ;
	@RequestMapping(value = "submit" , method=RequestMethod.POST)
    @ResponseBody
    public Integer submit(HttpServletResponse res , HttpServletRequest req ,HttpSession session
    		              ,  State state ){
            	try{
            	statemapper.insertSelective(state);
            	}catch(Exception e){
            		e.printStackTrace();
            		return 0;
            	}
            	return 1;
    }
    
    
    @RequestMapping(value = "getSome" , method=RequestMethod.GET)
    @ResponseBody
    public void getSome(HttpServletResponse res , HttpServletRequest req ,HttpSession session
    		              , State state ,String time ,String callback){
    	Map<String, String> where = new HashMap<String,String>();
    	
    	where.put("ownerTable", state.getOwnerTable());
    	
		List<State> states = statemapper.selectByWhere(where );
		Writer w;
		try {
			w = res.getWriter();
			w.write("state_"+time+"_getSome("+JSONObject.toJSONString(states)+")");
//			w.write(callbackparam+"("+JSONObject.toJSONString(states)+")");
			
		} catch (IOException e) {
			e.printStackTrace();
		}	
    }
    @RequestMapping(value = "modify" , method=RequestMethod.POST)
    @ResponseBody
    public void modify(HttpServletResponse res , HttpServletRequest req ,HttpSession session
    		, State state){
    	
    }
    private static ApplicationContext applicationContext; 
	@Override
	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		applicationContext = ctx;
	}   

}