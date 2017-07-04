package com.yonghui.portal.util.ftp;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.jcraft.jsch.ChannelSftp;



public class SFTPUpload {
	 public SFTPChannel getSFTPChannel() {
	        return new SFTPChannel();
	    }

	    /**
	     * @param args
	     * @throws Exception
	     */
	    public static void upload(InputStream in,String dist,String dir) throws Exception {
	    	SFTPUpload test = new SFTPUpload();

	        Map<String, String> sftpDetails = new HashMap<String, String>();
	        // 设置主机ip，端口，用户名，密码
	        sftpDetails.put(SFTPConstants.SFTP_REQ_HOST, "10.67.241.244");
	        sftpDetails.put(SFTPConstants.SFTP_REQ_USERNAME, "root");
	        sftpDetails.put(SFTPConstants.SFTP_REQ_PASSWORD, "yhcs1234");
	        sftpDetails.put(SFTPConstants.SFTP_REQ_PORT, "22");
	   
	              
	        SFTPChannel channel = test.getSFTPChannel();
	        ChannelSftp chSftp = channel.getChannel(sftpDetails, 60000);
	        

	        try{
	        	chSftp.ls(dir); //首先查看下目录，如果不存在，系统会被错，捕获这个错，生成新的目录。
	        }catch(Exception e){
	        	chSftp.mkdir(dir); 
	        }
	        chSftp.put(in, dist, ChannelSftp.OVERWRITE); // 代码段3
	        chSftp.quit();
	        channel.closeChannel();
	    }
}
