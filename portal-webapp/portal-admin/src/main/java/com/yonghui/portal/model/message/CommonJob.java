package com.yonghui.portal.model.message;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.intFocusDAO.DB;
import com.yonghui.portal.utils.MessageUtil;

public class CommonJob implements Job {

    private static final Logger log = Logger.getLogger(CommonJob.class);
	
	@Override
	public void execute(JobExecutionContext cnt) {
		//取出任务
		JobKey jobKey = cnt.getJobDetail().getKey();
		String jobName = jobKey.toString();
		jobName = jobName.substring(jobName.indexOf(".")+1);
		JobInformationWithBLOBs job = new JobInformationWithBLOBs();
		
		DB db = new DB("10.67.241.242"
				  ,"3306"
				  ,"platform"
				  ,"biuser"
				  ,"1234509876"
				  );
		Connection conn = db.getConnection() ;
		Statement stmt  = db.getStatemente(conn) ;
		ResultSet rs  = db.getResultSet(stmt, 
										" SELECT id, text, sql_script, fields_selected, job_key, is_no_role,"+
										" is_include_receiver, message_type, obj_id, send_type, obj_user, "+
										" role_user, acounter_user, `type`, triggerTime, executeTime, `cycle`, "+
										" dayInMounth, dayInWeek, jump_type, obj_user_type, end_time " +
										" FROM platform.job_information where job_key = '" + jobName+"'") ;
		
		try {
			while(rs.next()){
				job.setAcounterUser(rs.getString("acounter_user"));
				job.setCycle(rs.getString("cycle"));
				job.setDayinmounth(rs.getString("dayInMounth"));
				job.setDayinweek(rs.getString("dayInWeek"));
				job.setEndTime(rs.getString("end_time"));
				job.setExecutetime(rs.getString("executeTime"));
				job.setFieldsSelected(rs.getString("fields_selected"));
				job.setIsIncludeReceiver(rs.getString("is_include_receiver"));
				job.setIsNoRole(rs.getString("is_no_role"));
				job.setJobKey(rs.getString("job_key"));
				job.setJumpType(rs.getString("jump_type"));
				job.setMessageType(rs.getString("message_type"));
				job.setObjId(rs.getString("obj_id"));
				job.setObjUser(rs.getString("obj_user"));
				job.setObjUserType(rs.getString("obj_user_type"));
				job.setRoleUser(rs.getString("role_user"));
				job.setSendType(rs.getString("send_type"));
				job.setSqlScript(rs.getString("sql_script"));
				job.setText(rs.getString("text"));
				job.setTriggertime("triggerTime");
				job.setType(rs.getString("type"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(null!=rs){
					rs.close();
					rs = null ;
				}
				if(null!=stmt){
					stmt.close();
					stmt = null;
				}
				if(null!=conn){
					conn.close();
					conn = null ;
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		log.info("insert into sys_message_center with: " + JSONObject.toJSONString(job));
		if(null != job){
			MessageUtil.sendMessage(job);
		}
	}

}