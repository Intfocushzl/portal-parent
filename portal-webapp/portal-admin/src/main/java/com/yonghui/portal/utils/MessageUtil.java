package com.yonghui.portal.utils;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.Trigger;

import com.alibaba.dubbo.common.json.JSONObject;
import com.yonghui.portal.intFocusDAO.DB;
import com.yonghui.portal.model.message.CommonJob;
import com.yonghui.portal.model.message.IntfocusMessage;
import com.yonghui.portal.model.message.JobInformation;
import com.yonghui.portal.model.message.JobInformationWithBLOBs;

public class MessageUtil {
    private static final Logger log = Logger.getLogger(CommonJob.class);
	public static String sendMessage(JobInformationWithBLOBs jobInfo){
		
		String textMessage = null ;
		String insertMessageSql = "" ;
		String field = null ;
		List<String> mapedUser = new ArrayList<String>() ;
		List<String> colsName = null ;
		if("".equalsIgnoreCase(jobInfo.getFieldsSelected())
		   && "1".equalsIgnoreCase(jobInfo.getIsNoRole())){
			// 发送人不在 sql 语句中查找的情况
			DB sender = new DB("123.59.75.85"
					  ,"3306"
					  ,"platform"
					  ,"biuser"
					  ,"1234509876"
					  );
			textMessage = jobInfo.getText();
    		try {
    			Connection connSender = sender.getConnection() ;
    			PreparedStatement stmtSender  = connSender.prepareStatement("") ;
    			
    			insertMessageSql = 
				"INSERT INTO yonghuibi.sys_message_center" +
						"             (content" +
						"	      ,notify_mode" +
						"	      ,notify_level" +
						"	      ,receivers" +
						"	      ,created_at" +
						"	      ,updated_at)" +
						"    SELECT '"+ textMessage +"'"+
						"   	   , '"+jobInfo.getMessageType()+"' 	AS notify_mode" +
						"	   , 0       	AS notify_level" +
						"	   , (SELECT GROUP_CONCAT( user_num SEPARATOR ',' ) AS recervers from yonghuibi.sys_users where 1=1 "+getRecervers(jobInfo)+") AS receivers" +
						"	   , now() 		AS created_at" +
						"	   , now() 		AS updated_at";
    			 log.info(insertMessageSql);
				 stmtSender.addBatch(insertMessageSql);
			     stmtSender.executeBatch();
			  } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			  }

		}else{
			textMessage = jobInfo.getText();
			DB db = new DB("123.59.75.85"
						  ,"3306"
						  ,"yonghuibi_s"
						  ,"biuser"
						  ,"1234509876"
						  );
			Connection conn = null ;
			Statement stmt  = null ;
			ResultSet rs  = null ;
			
			DB sender = new DB("123.59.75.85"
							  ,"3306"
							  ,"platform"
							  ,"biuser"
							  ,"1234509876"
							  );
			Connection connSender = null ;
			PreparedStatement stmtSender  = null ;
			
			try {
			
			conn = db.getConnection();
			
			stmt = db.getStatemente(conn);

			connSender = sender.getConnection();
			
			stmtSender = db.getPreparedStatemente(connSender);
			if(!"".equalsIgnoreCase(jobInfo.getSqlScript())
				&& null != jobInfo.getSqlScript()){
				rs = stmt.executeQuery(jobInfo.getSqlScript());
				colsName = getColumnsName(rs);
			}
			
			
		    if("1".equalsIgnoreCase(jobInfo.getIsIncludeReceiver())){
		    	//包含接收者
		    	
		    	
		    	if("firstRecord".equalsIgnoreCase(jobInfo.getSendType())){
		    		// 发送首条

		    		while(rs.next()){
		    			
		    			textMessage = jobInfo.getText();
		    			
		    			if(mapedUser.contains(rs.getString("account"))){
		    				continue;
		    			}else{
		    				mapedUser.add(rs.getString("account"));
		    			}
		    			
			    		for (String string : colsName ) {
							textMessage = textMessage.replaceAll("\\{" + string + "\\}", rs.getString(string));
			    		}
			    		stmtSender.addBatch(insertMessageSql ="INSERT INTO yonghuibi.sys_message_center" +
			    				"             (content" +
			    				"	      ,notify_mode" +
			    				"	      ,notify_level" +
			    				"	      ,receivers" +
			    				"	      ,extra_params_string" +
			    				"	      ,created_at" +
			    				"	      ,updated_at)" +
			    				"    SELECT '"+ textMessage +"'"+
			    				"   	   , '"+jobInfo.getMessageType()+"' 	AS notify_mode" +
			    				"	   , 0       	AS notify_level" +
			    				"	   , (SELECT GROUP_CONCAT( user_num SEPARATOR ',' ) AS recervers from yonghuibi.sys_users where user_num in ('"+rs.getString("account")+"')) AS receivers" +
			    				" ," + (null == jobInfo.getObjId() || null == jobInfo.getJumpType() ? " null " : getExtraParamsString(jobInfo)) +
			    				"	   , now() 		AS created_at" +
			    				"	   , now() 		AS updated_at");
			    		log.info(insertMessageSql);
		    		}
		    		stmtSender.executeBatch();
		    	}else if("allRecord".equalsIgnoreCase(jobInfo.getSendType())){
		    		// 全部发送
		    		while(rs.next()){
		    			
		    			textMessage = jobInfo.getText();
			    		for (String string : colsName ) {
							textMessage = textMessage.replaceAll("\\{" + string + "\\}", rs.getString(string));
			    		}
			    		stmtSender.addBatch(insertMessageSql="INSERT INTO yonghuibi.sys_message_center" +
			    				"             (content" +
			    				"	      ,notify_mode" +
			    				"	      ,notify_level" +
			    				"	      ,receivers" +
			    				"	      ,extra_params_string" +
			    				"	      ,created_at" +
			    				"	      ,updated_at)" +
			    				"    SELECT '"+ textMessage +"'" +
			    				"   	   , '"+jobInfo.getMessageType()+"' 	AS notify_mode" +
			    				"	   , 0       	AS notify_level" +
			    				"	   , (SELECT GROUP_CONCAT( user_num SEPARATOR ',' ) AS recervers from yonghuibi.sys_users where user_num in ('"+rs.getString("account")+"')) AS receivers" +
			    				" ," + (null == jobInfo.getObjId() || null == jobInfo.getJumpType() ? " null " : getExtraParamsString(jobInfo)) +
			    				"	   , now() 		AS created_at" +
			    				"	   , now() 		AS updated_at");
			    		log.info(insertMessageSql);
		    	       }
		    		stmtSender.executeBatch();	
		    	}
		    }else{
		    	//SQL 中不发送接收者的情况
		    	
		    	if("firstRecord".equalsIgnoreCase(jobInfo.getSendType())){
		    			
		    			textMessage = jobInfo.getText();

							stmtSender.addBatch(insertMessageSql = "INSERT INTO yonghuibi.sys_message_center" +
									"             (content" +
									"	      ,notify_mode" +
									"	      ,notify_level" +
									"	      ,receivers" +
				    				"	      ,extra_params_string" +
									"	      ,created_at" +
									"	      ,updated_at)" +
									"    SELECT '"+ textMessage +"'" +
									"   	   , '"+jobInfo.getMessageType()+"' 	AS notify_mode" +
									"	   , 0       	AS notify_level" +
									"	   , (SELECT GROUP_CONCAT( user_num SEPARATOR ',' ) AS recervers from yonghuibi.sys_users as u where 1=1 and "
									+getMessageType(jobInfo.getMessageType()) +
									getRecervers(jobInfo)+") AS receivers" +
				    				" ," + (null == jobInfo.getObjId() || null == jobInfo.getJumpType() ? " null " : getExtraParamsString(jobInfo)) +
									"	   , now() 		AS created_at" +
									"	   , now() 		AS updated_at");
							log.info(insertMessageSql);
		    		stmtSender.executeBatch();	
		    	}else if("allRecord".equalsIgnoreCase(jobInfo.getSendType())){

		    			textMessage = jobInfo.getText();

			    		stmtSender.addBatch(insertMessageSql = "INSERT INTO yonghuibi.sys_message_center" +
			    				"             (content" +
			    				"	      ,notify_mode" +
			    				"	      ,notify_level" +
			    				"	      ,receivers" +
			    				"	      ,extra_params_string" +
			    				"	      ,created_at" +
			    				"	      ,updated_at)" +
			    				"    SELECT '"+ textMessage +"'" +
			    				"   	   , '"+jobInfo.getMessageType()+"' 	AS notify_mode" +
			    				"	   , 0       	AS notify_level" +
			    				"	   , (SELECT GROUP_CONCAT( u.user_num SEPARATOR ',' ) AS recervers from yonghuibi.sys_users as u where 1=1 and  "
			    				+getMessageType(jobInfo.getMessageType()) + "  " +
			    				getRecervers(jobInfo)+") AS receivers" +
			    				" ," + (null == jobInfo.getObjId() || null == jobInfo.getJumpType() ? " null " : getExtraParamsString(jobInfo)) +
			    				"	   , now() 		AS created_at" +
			    				"	   , now() 		AS updated_at");
			  
			    		log.info(insertMessageSql);
		    		   
		    		stmtSender.executeBatch();	
		    	
		    }
		    }
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				db.close(connSender);
				db.close(stmtSender);
				db.close(rs);
				db.close(stmt);
				db.close(conn);
			}
		}
		
		return textMessage;
		
		
	}
	public static String getRecervers(JobInformationWithBLOBs jobInfo) {
		String receivers = "" ;
		int objType = 0;
		
		if("report".equalsIgnoreCase(jobInfo.getObjUserType())){
			objType = 1;
		}else if("analysis".equalsIgnoreCase(jobInfo.getObjUserType())){
			objType = 2 ;
		}else if("app".equalsIgnoreCase(jobInfo.getObjUserType())){
			objType = 3 ;
		}
		
		if(!"".equalsIgnoreCase(jobInfo.getRoleUser()) && null != jobInfo.getRoleUser()){
			receivers = receivers + " and id in (select user_id from yonghuibi.sys_user_roles where role_id in ( "+jobInfo.getRoleUser()+"))";
		}
		if(!"".equalsIgnoreCase(jobInfo.getObjUser()) && null != jobInfo.getObjUser()){
			receivers = receivers + " and id in (select user_id from yonghuibi.sys_user_roles where role_id in ( select role_id from yonghuibi.sys_role_resources where obj_type = "+
		    
		    objType+" and obj_id in ("+jobInfo.getObjUser()+")))";
		}
		if(!"".equalsIgnoreCase(jobInfo.getAcounterUser()) && null != jobInfo.getAcounterUser()){
			String[] rers = jobInfo.getAcounterUser().split(",");
			String accounterUser = "'<nothing>'" ;
			for(String s : rers){
				accounterUser =accounterUser + ",'" + s +"'" ;
			}
			
			if("".equalsIgnoreCase(receivers)){
				receivers = " and user_num in ("+accounterUser+")";
			}else{
				receivers = "and ( "  +receivers + ") or user_num in ("+accounterUser+")";
			}
			
		}
		
		
		return receivers;
	}
	public static String getMessageType(String messageType) {
		
		StringBuilder rtn = new StringBuilder();
		if("sms".equalsIgnoreCase(messageType)){
			rtn.append(" u.mobile <> '' and u.mobile is not null ");
		}else if("push".equalsIgnoreCase(messageType)){
			rtn.append(" exists ( ")
			   .append(" 			select 1 ")
			   .append(" 			from yonghuibi.sys_devices  as sd ")
			   .append(" 			inner join yonghuibi.sys_user_devices as sud on sd.id = sud.device_id " )
			   .append(" 			where length(sd.push_device_token) > 0 and u.user_num = sud.user_num" )
			   .append(" )");
		}else if("email".equalsIgnoreCase(messageType)){
			rtn.append(" u.email <> '' and u.email is not null ");
		}
		
		return rtn.toString();
	}
	public static Trigger getTrigger(IntfocusMessage message) throws ParseException{
	    System.out.println("triggerTime" + message.getTriggerTime());	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-d hh:mm:ss");
		Trigger trigger    = null ;
		int repeatInterval = 0 ;
		int repeatCount    = 0;
		String cronStr     = null ;
		Date triggerDateTime = null ;
		if(message.getType().equals("1")){
			
			
			trigger = newTrigger().withIdentity(message.getJobName(), "group_1").withSchedule(simpleSchedule().withMisfireHandlingInstructionNextWithExistingCount())
								  .startAt(sdf.parse(message.getTriggerTime())).build();
		}else{
			repeatInterval = Integer.parseInt(message.getCycle());
			repeatCount = Integer.parseInt(message.getExecuteTime()) ;
			switch (message.getType()) {
			case "2":
				//按分钟
				trigger = newTrigger().withIdentity(message.getJobName(), "group_1")
				  .withSchedule(simpleSchedule().withMisfireHandlingInstructionNextWithExistingCount()
				  .withIntervalInMinutes(repeatInterval).repeatForever()).startAt(sdf.parse(message.getTriggerTime())).endAt(sdf.parse(message.getEndTime()))
				  .build();
				break;

			case "3":
				//按小时
				trigger = newTrigger().withIdentity(message.getJobName(), "group_1")
				.withSchedule(simpleSchedule().withMisfireHandlingInstructionNextWithExistingCount()
				.withIntervalInHours(repeatInterval*24).repeatForever())
				.startAt(sdf.parse(message.getTriggerTime()))
				.endAt(sdf.parse(message.getEndTime()))
				.build();
				break;
			case "4":
				//按周
				triggerDateTime = sdf.parse(message.getTriggerTime());
				cronStr = "" + triggerDateTime.getSeconds() + " " +  triggerDateTime.getMinutes() + " " + triggerDateTime.getHours() + " ? *" ;
				cronStr = cronStr + " " + message.getDayInWeek();
				trigger = (CronTrigger)newTrigger().withIdentity(message.getJobName(), "group_1").withSchedule(cronSchedule(cronStr).withMisfireHandlingInstructionFireAndProceed()).build();

				break;
			case "5":
				triggerDateTime = sdf.parse(message.getTriggerTime());
				cronStr = "" + triggerDateTime.getSeconds() + " " +  triggerDateTime.getMinutes() + " " + triggerDateTime.getHours() ;
				cronStr = cronStr + " "+ message.getDayInMounth() +" * ?";
				trigger = (CronTrigger)newTrigger().withIdentity(message.getJobName(), "group_1").withSchedule(cronSchedule(cronStr).withMisfireHandlingInstructionFireAndProceed()).build();
				
				break;
			default:
				break;
			}
			
		
		}
		
		return trigger ;
	}
	
	public static String getExtraParamsString(JobInformationWithBLOBs info){
		com.alibaba.fastjson.JSONObject extraParamsString = new com.alibaba.fastjson.JSONObject();
		Integer objType = null ;
		String sql = null;
		Integer templateID = null ;
		try{
			if("kpi".equalsIgnoreCase(info.getJumpType())){
				objType = 1 ;
			}else if("report".equalsIgnoreCase(info.getJumpType())){
				objType = 2 ;
			}else if("analysis".equalsIgnoreCase(info.getJumpType())){
				objType = 3 ;
			}
			
			DB db = new DB();
			Connection conn  = db.getConnection();
			PreparedStatement stmt = db.getPreparedStatemente(conn);
			sql = "select report_id , template_id , title from yonghuibi.sys_template_reports where report_id = " + info.getObjId();
			ResultSet rs = db.getResultSet(stmt, sql);
			while(rs.next()){
				templateID = rs.getInt("template_id");
			}
			extraParamsString.put("type", info.getJumpType());
			extraParamsString.put("title","");
			extraParamsString.put("url", "/mobile/v2/group/%@/template/"+templateID+"/report/" + info.getObjId());
			extraParamsString.put("obj_id", Integer.parseInt( info.getObjId()));
			extraParamsString.put("obj_type", objType);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
				
		}
		return "'" + extraParamsString.toJSONString() +"'";
	}
	
	public static List<String> getColumnsName(ResultSet rs) {  
		List<String> colsName = new ArrayList<String>();
		int colsCount = 0 ;
		try{  
	    	ResultSetMetaData metaData = rs.getMetaData();
	    	colsCount = metaData.getColumnCount();
	    	
	    	for(int i = 0 ; i < colsCount;i++){
	    		colsName.add(metaData.getColumnLabel(i + 1));
	    	}
	    	
		}catch (Exception e) {
			e.printStackTrace();
	    }  
	     return colsName;
	}  
}
