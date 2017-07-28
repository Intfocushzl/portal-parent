package com.yonghui.portal.utils;


import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

public class StringTools {
	
	public static void sWrite(HttpServletResponse response, String json) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
//		response.setContentType("application/json");
		Writer w= response.getWriter();
		w.write(json);
	}
	
	public static String subStr(String str,int start,int end)throws Exception {
//		System.out.println(str);
		if(str==null||str==""){
			return null;
		}
		byte[] bt=str.getBytes("GBK");
		int num=end-start;
		byte[] c=new byte[num];
		int j=0;
		for(int i=start;i<end;i++){
			c[j]=bt[i];
			j++;
		}
		return new String(c,"GBK");
	}
}
