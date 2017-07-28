package com.yonghui.portal.controller.app;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.itextpdf.text.xml.simpleparser.handler.NeverNewLineHandler;
import com.yonghui.portal.utils.StringTools;
import com.yonghui.portal.utils.ComUtil;
//import com.yonghui.portal.utils.ExcelUtils;
import com.yonghui.portal.utils.ExportExcelUtils;
import com.yonghui.portal.mapper.app.AppRoleMapper;
import com.yonghui.portal.mapper.app.JobInformationMapper;
import com.yonghui.portal.model.message.AbnormalDataGrid;
import com.yonghui.portal.model.message.AppResource;
import com.yonghui.portal.model.message.AppRole;
import com.yonghui.portal.model.message.IntfocusMessage;
import com.yonghui.portal.model.message.JobInformationWithBLOBs;
import com.yonghui.portal.service.impl.message.MessagerServiceImpl;
import com.yonghui.portal.util.R;

@Controller
@RequestMapping("/message")
public class MessageController implements ApplicationContextAware  {
	
	private static Logger logger = Logger.getLogger(MessageController.class);
    private static ApplicationContext applicationContext;

    @Resource
    MessagerServiceImpl messsageServiceImpl ;
    
    @Resource
    JobInformationMapper jobInformationMapper;
    
    @Resource
	private AppRoleMapper appRoleMapper;
    
	private SchedulerFactory sf = new StdSchedulerFactory();
	private Scheduler sched    = null ;
    
	@RequestMapping(value="add" , method=RequestMethod.POST)
	@ResponseBody
    @RequiresPermissions("message:add")
	public String add(IntfocusMessage message) throws SchedulerException{
		
		JSONObject res = new JSONObject();
		try{
			boolean suc = messsageServiceImpl.add(message, sched);
			if(suc){
				sched.start();
			}
		    res.put("success", 0);
		    return res.toJSONString();
		}catch(Exception e){
			e.printStackTrace();
			res.put("success", 1);
			res.put("message", e.getMessage());
			return res.toJSONString();
		}
		
	}
	@RequestMapping(value="delete" , method=RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("message:delete")
	public R delete(String jobKey){
		
		try{
			IntfocusMessage m = new IntfocusMessage();
			m.setJobName(jobKey);
			messsageServiceImpl.delete(m,sched);
			return R.success();
		}catch(Exception e){
			e.printStackTrace();
			return R.error();
		}
	}
	
	@RequestMapping(value="gotoModify")
	@ResponseBody
	@RequiresPermissions("message:gotoModify")
	public R gotoModify(HttpServletRequest request, HttpServletResponse response , String jobKey){
		
		JSONObject res = new JSONObject();
		try{
			JobInformationWithBLOBs job = jobInformationMapper.selectByJobKey(jobKey);
			return R.success().put("messageJob", job);	
		} catch (Exception e) {
			e.printStackTrace();
			return R.error();
		}
	}

	@RequestMapping(value="getSome" , method=RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("message:getSome")
	public R getSome( @RequestParam(value="page") Integer page , Integer pageSize , String jobName){

		Map<String, Object> where = new HashMap<String , Object>();
		try{
			where.put("page", page);
			where.put("pageSize", pageSize);
			where.put("jobName", jobName);
			return R.success().put("rows",  jobInformationMapper.selectJobAll(where));
		}catch(Exception e){
			e.printStackTrace();
			return R.error().put("success", 1);
		}
		
	}
	@RequestMapping(value="modify" , method=RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("message:modify")
	public R modify(IntfocusMessage m){

		try {
			sched.clear();
			messsageServiceImpl.modify(m, sched);
			return R.success();
		} catch (SchedulerException e) {
			e.printStackTrace();
			return R.error();
		}
	}
	@RequestMapping(value="getObjs" , method=RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("message:getObjs")
	public R getObjs(HttpServletResponse response , String objTypes){
		
		try {
			List<AppResource> appResources = null ;
			Map<String , Object> where = new HashMap<String , Object>();
			List<String> objTypeList = new ArrayList<String>();
			objTypeList.add(objTypes);
			where.put("ObjTypes",objTypeList);
			appResources = appRoleMapper.selectReportsInfo(where);
			JSONObject json = new JSONObject();
			return R.success().put("rows", appResources);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error();
			
		}
		
	}

	@RequestMapping(value="getRoles" , method=RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("message:getRoles")
	public R getRoles(HttpServletResponse response){
		try {
			List<AppRole> appRoles = new ArrayList<AppRole>();
			appRoles = appRoleMapper.selectAll();
			return R.success().put("rows", appRoles);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error();
		}
		
	}	
	
	@Override
	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
      System.setProperty("org.quartz.properties"
    		  ,ExportExcelUtils.getResourcePath(this.getClass()).replace("com/yonghui/portal/controller/app/", "") + "quartz.properties");
      
	    try {
			sched = sf.getScheduler();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		applicationContext = ctx;
	}
	@RequestMapping(value="checkContactSummrize" , method=RequestMethod.GET)
	@ResponseBody
	public String checkContactSummrize(String jobName){
				
		return messsageServiceImpl.checkContactSummarize(jobName);
		
		
	}
	@RequestMapping(value="returnUncheckedContactSQL" , method=RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("message:returnUncheckedContactSQL")
	public String returnUncheckedContactSQL(String jobName){
		
		return messsageServiceImpl.returnUncheckedContactSQL(jobName);
	}
	@RequestMapping(value="returnUncheckedContactExcel" , method=RequestMethod.GET)
	@RequiresPermissions("message:returnUncheckedContactExcel")
	public void returnUncheckedContactExcel(HttpServletResponse response, String jobName){
		try{   
			
			List<AbnormalDataGrid> list = messsageServiceImpl.returnUncheckedContactExcel(jobName);	
			
			HSSFWorkbook workbook= new ExportExcelUtils<AbnormalDataGrid>().
					                   exportExcel( "unPassedUserAccount.xml", 
					                		   this.getClass(), null, list) ;
			response.setContentType("application/x-msdownload");       
			response.setHeader("Content-disposition", "attachment;filename=freshDetailDatas.xls");       
			
			OutputStream ouputStream  = response.getOutputStream();
			workbook.write(ouputStream);       
			ouputStream.flush();       
			ouputStream.close();   
	 	} catch (Exception e) {
	 		e.printStackTrace();
	 	}	
	}
}
