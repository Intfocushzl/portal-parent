package com.yonghui.portal.service.impl.message;
import static com.yonghui.portal.utils.MessageUtil.getTrigger;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.JobKey.jobKey;
import static org.quartz.TriggerKey.triggerKey;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.intFocusDAO.DB;
import com.yonghui.portal.mapper.app.JobInformationMapper;
import com.yonghui.portal.service.message.MessageService;
import com.yonghui.portal.model.message.AbnormalDataGrid;
import com.yonghui.portal.model.message.CommonJob;
import com.yonghui.portal.model.message.IntfocusMessage;
import com.yonghui.portal.model.message.JobInformationWithBLOBs;
import com.yonghui.portal.utils.MessageUtil;

@Service
public class MessagerServiceImpl implements MessageService {

	@Resource
	JobInformationMapper jobInfo ;	
	public boolean add(IntfocusMessage message, Scheduler scheduler) {
		
		try {
			
			Trigger trigger = getTrigger(message);
			
			JobDetail job = newJob(CommonJob.class).withIdentity(message.getJobName(), "group_1").build();
			
			JobInformationWithBLOBs record = new JobInformationWithBLOBs();
			
			record.setFieldsSelected(message.getFieldsSelected());

			record.setJobKey(message.getJobName());
			
			record.setSqlScript(message.getSqlScript());

			record.setText(message.getMessage());
		    	
			record.setAcounterUser(message.getAccountUser());

			record.setIsIncludeReceiver(message.getIsIncludeReceiver());
		
			record.setIsNoRole(message.getIsNoRule());

			record.setMessageType(message.getMessageType());
			
			record.setObjId(message.getObjID());
		
			record.setObjUser(message.getObjUser());
			
			record.setRoleUser(message.getRoleUser());
			
			record.setSendType(message.getSendType());
			
			record.setType(message.getType());
			
			record.setTriggertime(message.getTriggerTime());
			
			record.setExecutetime(message.getExecuteTime());
			
			record.setCycle(message.getCycle());
			
			record.setDayinmounth(message.getDayInMounth());
			
			record.setDayinweek(message.getDayInWeek());
			
			record.setJumpType(message.getJumpType());
			
			record.setObjUserType(message.getObjUserType());

			record.setEndTime(message.getEndTime());
			
			jobInfo.insertSelective(record);
			
			scheduler.scheduleJob(job, trigger);
			
			
		} catch (SchedulerException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false ;
		}
		
		return true;
	}

	public boolean modify(IntfocusMessage message, Scheduler scheduler) {
		
		try {
			Trigger trigger = getTrigger(message);
			scheduler.rescheduleJob(triggerKey(message.getJobName()), trigger);
			
			JobInformationWithBLOBs record = new JobInformationWithBLOBs();
			
			record.setFieldsSelected(message.getFieldsSelected());
			
			record.setJobKey(message.getJobName());
			
			record.setSqlScript(message.getSqlScript());
			
			record.setText(message.getMessage());
			
			record.setAcounterUser(message.getAccountUser());
			
			record.setIsIncludeReceiver(message.getIsIncludeReceiver());
			
			record.setIsNoRole(message.getIsNoRule());
			
			record.setMessageType(message.getMessageType());
			
			record.setObjId(message.getObjID());
			
			record.setObjUser(message.getObjUser());
			
			record.setRoleUser(message.getRoleUser());
			
			record.setSendType(message.getSendType());
			
			record.setType(message.getType());
			
			record.setTriggertime(message.getTriggerTime());
			
			record.setExecutetime(message.getExecuteTime());
			
			record.setCycle(message.getCycle());
			
			record.setDayinmounth(message.getDayInMounth());
			
			record.setDayinweek(message.getDayInWeek());
			
			record.setJumpType(message.getJumpType());
			
			record.setObjUserType(message.getObjUserType());
			
			record.setEndTime(message.getEndTime());
			
			record.setId(jobInfo.selectByJobKey(message.getJobName()).getId());
			
			jobInfo.updateByPrimaryKeySelective(record);
			
		} catch (SchedulerException | ParseException e) {
			return false;
		}
		return true;
	}

	public boolean delete(IntfocusMessage message, Scheduler scheduler) {
		
		try {
			scheduler.deleteJob(jobKey(message.getJobName()));
			JobInformationWithBLOBs record = new JobInformationWithBLOBs();
			record = jobInfo.selectByJobKey(message.getJobName());
			jobInfo.deleteByPrimaryKey(record.getId());
		} catch (SchedulerException e) {
			return false;
		}
		return true;
	}
	public List<AbnormalDataGrid> returnUncheckedContactExcel(String jobName ){
		List<AbnormalDataGrid> data = new ArrayList<AbnormalDataGrid>();
		JobInformationWithBLOBs job =  jobInfo.selectByJobKey(jobName);
		String sql = "" ;
		String userNum = ""; 
		DB db = new DB("123.59.75.85"
				,"3306"
				,"platform"
				,"biuser"
				,"1234509876");
		ResultSet rs    = null ;
		Connection conn = db.getConnection();
		Statement stmt  = db.getStatemente(conn);
		try {
			if("1".equalsIgnoreCase(job.getIsIncludeReceiver())
				&& !"".equalsIgnoreCase(job.getSqlScript())){
				
						rs = stmt.executeQuery("select GROUP_CONCAT( distinct concat('''',account,'''')  SEPARATOR ',') as userNum from ("
												+ job.getSqlScript() + " ) as a ");

				while(rs.next()){
					userNum = rs.getString("userNum");	
				}		
				if(!"push".equalsIgnoreCase(job.getMessageType())){
					sql = "select ("+MessageUtil.getMessageType(job.getMessageType())+") as receiver , user_num , user_name from yonghuibi.sys_users where 1=1 " +
							" and user_num in (" + userNum + ") "	
							+ " and ( "+MessageUtil.getMessageType(job.getMessageType())+" is null or "+MessageUtil.getMessageType(job.getMessageType())+" ='' )";	
				}else{
					sql =   "	  select " + 
							"	distinct 'push' as receivers , sud.user_num," +
							"	   su.user_name	" +
							"	from sys_user_devices as sud" +
							"	left join sys_users as su " +
							"	on su.user_num = sud.user_num" +
							"	where not exists(" +
							"	select 1 " +
							"	  from sys_devices as sd" +
							"	 where sd.id = sud.device_id" +
							"	   and length(push_device_token) > 0" +
							"	) and su.user_num in (" + userNum + ")"
							+ " and ( "+MessageUtil.getMessageType(job.getMessageType())+" is null or "+MessageUtil.getMessageType(job.getMessageType())+" ='' )";	
				}
			}else{
				
				if(!"push".equalsIgnoreCase(job.getMessageType())){
					sql = "select ("+MessageUtil.getMessageType(job.getMessageType())+") as receiver , user_num , user_name from yonghuibi.sys_users where 1=1 " +
							 MessageUtil.getRecervers(job)
							 + " and ( "+MessageUtil.getMessageType(job.getMessageType())+" is null or "+MessageUtil.getMessageType(job.getMessageType())+" ='' )";
				}else{
					sql =   "	  select " + 
							"	distinct 'push' as receiver , sud.user_num," +
							"	   su.user_name	" +
							"	from sys_user_devices as sud" +
							"	left join sys_users as su " +
							"	on su.user_num = sud.user_num" +
							"	where not exists(" +
							"	select 1 " +
							"	  from sys_devices as sd" +
							"	 where sd.id = sud.device_id" +
							"	   and length(push_device_token) > 0" +
							"	) " + MessageUtil.getRecervers(job) + 
							" and ( "+MessageUtil.getMessageType(job.getMessageType())+" is null or "+MessageUtil.getMessageType(job.getMessageType())+" ='' )";	
			
				}
		   }
		  if(null != rs){
			  rs.close();
			  rs = null;
		  }
		  rs = stmt.executeQuery(sql);
		  AbnormalDataGrid a = new AbnormalDataGrid();
		  while(null!=rs && rs.next()){
			  a.setCat1Id(rs.getString("receiver"));
			  a.setCat1Name(rs.getString("user_num"));
			  a.setName(rs.getString("user_name"));
		  }
			data.add(a);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(null!=rs){
						rs.close();
				}
				if(null!=stmt){
					stmt.close();
				}
				if(null!=conn){
					conn.close();	
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}	

		return data;
	}
	
	public String returnUncheckedContactSQL(String jobName){
		JobInformationWithBLOBs job =  jobInfo.selectByJobKey(jobName);
		String sql = "" ;
		String userNum = ""; 
		DB db = new DB("123.59.75.85"
				,"3306"
				,"platform"
				,"biuser"
				,"1234509876");
		Connection conn = db.getConnection();
		Statement stmt  = db.getStatemente(conn);
		ResultSet rs    = null ;
		try {
		if("1".equalsIgnoreCase(job.getIsIncludeReceiver())
				&& !"".equalsIgnoreCase(job.getSqlScript())){
				
						rs = stmt.executeQuery("select GROUP_CONCAT( distinct concat('''',account,'''')  SEPARATOR ',') as userNum from ("
												+ job.getSqlScript() + " ) as a ");

				while(rs.next()){
					userNum = rs.getString("userNum");	
				}		
				if(!"push".equalsIgnoreCase(job.getMessageType())){
					sql = "select ("+MessageUtil.getMessageType(job.getMessageType())+") as receiver from yonghuibi.sys_users where 1=1 " +
							" and user_num in (" + userNum + ") "	
							+ " and ( "+MessageUtil.getMessageType(job.getMessageType())+" is null or "+MessageUtil.getMessageType(job.getMessageType())+" ='' )";	
				}else{
					sql =   "	  select " + 
							"	distinct sud.user_num," +
							"	   su.user_name	" +
							"	from sys_user_devices as sud" +
							"	left join sys_users as su " +
							"	on su.user_num = sud.user_num" +
							"	where not exists(" +
							"	select 1 " +
							"	  from sys_devices as sd" +
							"	 where sd.id = sud.device_id" +
							"	   and length(push_device_token) > 0" +
							"	) and su.user_num in (" + userNum + ")"
							+ " and ( "+MessageUtil.getMessageType(job.getMessageType())+" is null or "+MessageUtil.getMessageType(job.getMessageType())+" ='' )";	
				}
			}else{
				
				if(!"push".equalsIgnoreCase(job.getMessageType())){
					sql = "select ("+MessageUtil.getMessageType(job.getMessageType())+") as receiver from yonghuibi.sys_users where 1=1 " +
							 MessageUtil.getRecervers(job)
							 + " and ( "+MessageUtil.getMessageType(job.getMessageType())+" is null or "+MessageUtil.getMessageType(job.getMessageType())+" ='' )";
				}else{
					sql =   "	  select " + 
							"	distinct sud.user_num," +
							"	   su.user_name	" +
							"	from sys_user_devices as sud" +
							"	left join sys_users as su " +
							"	on su.user_num = sud.user_num" +
							"	where not exists(" +
							"	select 1 " +
							"	  from sys_devices as sd" +
							"	 where sd.id = sud.device_id" +
							"	   and length(push_device_token) > 0" +
							"	) " + MessageUtil.getRecervers(job) + 
							" and ( "+MessageUtil.getMessageType(job.getMessageType())+" is null or "+MessageUtil.getMessageType(job.getMessageType())+" ='' )";	
			
				}
		   }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sql; 

	}
	
	public String checkContactSummarize(String jobName){
		
		JobInformationWithBLOBs job =  jobInfo.selectByJobKey(jobName);
		String userNum = ""; 
		
		DB db = new DB("123.59.75.85"
				,"3306"
				,"platform"
				,"biuser"
				,"1234509876");
		Connection conn = db.getConnection();
		Statement stmt  = db.getStatemente(conn);
		ResultSet rs    = null ;
		String sql = "" ;
	    int unCheckedUserCount = 0 ;
		try {
			if("1".equalsIgnoreCase(job.getIsIncludeReceiver())
				&& !"".equalsIgnoreCase(job.getSqlScript())){
				
					rs = stmt.executeQuery("select GROUP_CONCAT( distinct concat('''',account,'''')  SEPARATOR ',') as userNum from ("
											+ job.getSqlScript() + " ) as a ");
					rs = stmt.executeQuery("select GROUP_CONCAT( distinct concat('''',account,'''')  SEPARATOR ',') as userNum , 1 as a from ("
							+ job.getSqlScript() + " ) as a ");
				while(rs.next()){
					userNum = rs.getString("a");	
				}		
				
				//判断是否发送 APP 推送
				
				if(!"push".equalsIgnoreCase(job.getMessageType())){
					sql = "select ("+MessageUtil.getMessageType(job.getMessageType())+") as receiver from yonghuibi.sys_users where 1=1 " +
							" and user_num in (" + userNum + ") "	
							+ " and ( "+MessageUtil.getMessageType(job.getMessageType())+" is null or "+MessageUtil.getMessageType(job.getMessageType())+" ='' )";	
				}else{
					sql =   "	  select " + 
							"	distinct sud.user_num," +
							"	   su.user_name	" +
							"	from sys_user_devices as sud" +
							"	left join sys_users as su " +
							"	on su.user_num = sud.user_num" +
							"	where not exists(" +
							"	select 1 " +
							"	  from sys_devices as sd" +
							"	 where sd.id = sud.device_id" +
							"	   and length(push_device_token) > 0" +
							"	) and su.user_num in (" + userNum + ")"
							+ " and ( "+MessageUtil.getMessageType(job.getMessageType())+" is null or "+MessageUtil.getMessageType(job.getMessageType())+" ='' )";	
				}
			}else{
				
				if(!"push".equalsIgnoreCase(job.getMessageType())){
					sql = "select ("+MessageUtil.getMessageType(job.getMessageType())+") as receiver from yonghuibi.sys_users where 1=1 " +
							 MessageUtil.getRecervers(job)
							 + " and ( "+MessageUtil.getMessageType(job.getMessageType())+" is null or "+MessageUtil.getMessageType(job.getMessageType())+" ='' )";
				}else{
					sql =   "	  select " + 
							"	distinct sud.user_num," +
							"	   su.user_name	" +
							"	from sys_user_devices as sud" +
							"	left join sys_users as su " +
							"	on su.user_num = sud.user_num" +
							"	where not exists(" +
							"	select 1 " +
							"	  from sys_devices as sd" +
							"	 where sd.id = sud.device_id" +
							"	   and length(push_device_token) > 0" +
							"	) " + MessageUtil.getRecervers(job) + 
							" and ( "+MessageUtil.getMessageType(job.getMessageType())+" is null or "+MessageUtil.getMessageType(job.getMessageType())+" ='' )";	
			
				}
			}
		  if(null != rs){
			  rs.close();
			  rs = null;
		  }
		  rs = stmt.executeQuery(sql);
		  while(null!=rs && rs.next()){
			  unCheckedUserCount ++ ;
		  }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String , Object> m = new HashMap<String , Object>();
		m.put("jobName",jobName);
		m.put("unCheckedCount", unCheckedUserCount);
		
		return JSONObject.toJSONString(m)  ;
	}

}
