package com.yonghui.portal.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import com.yonghui.portal.model.message.ParamVo;

public class ComUtil{

	public static  Map<String ,Object> TransListToMap(List<ParamVo> list){
		if(null == list || 0 ==  list.size() ){
			return null ;
		}
		
		
   	  Map<String ,Object> where = new HashMap<String, Object>();
   	  for (ParamVo paramVo : list) {
   		  if(null != paramVo.getKey() && null != paramVo.getValue()){
		    where.put(paramVo.getKey(), paramVo.getValue());
		  }
	}
	  return where ;
	}
	   public static Map<String , Object> getMap(Map<String ,String[]> mp){
	    	Map<String , Object> where = new HashMap<String, Object>();
			for (Map.Entry<String ,String[]> entry : mp.entrySet()) {  
				  
				if(entry.getKey().endsWith("[key]")){
					int i = entry.getKey().indexOf("]") ;
					String sub = entry.getKey().substring(0,i +1 );
					where.put(entry.getValue()[0],mp.get( sub+ "[value]")[0]);

				}
			  
			}  
	      return where ;	
	    	
	    }
	   public static Map<String , Object> transMap(Map<String ,String[]> mp){
		   Map<String , Object> where = new HashMap<String, Object>();
		   for (Map.Entry<String ,String[]> entry : mp.entrySet()) {  
			   
                   if("page_index".equalsIgnoreCase(entry.getKey()) || "rows".equalsIgnoreCase(entry.getKey())){
				       where.put(entry.getKey(),Integer.parseInt(entry.getValue()[0]));
                   } else {
                	   where.put(entry.getKey(),entry.getValue()[0]);
                   }
		   }  
		   return where ;	
		   
	   }
	
	public static String getCongfig(Class clazz , String target){
	    Properties pps = new Properties();
		try {
			InputStream is = clazz.getResourceAsStream("/dafaultConditions.properties");
			BufferedReader bf = new BufferedReader(new   InputStreamReader(is));  
			pps.load(bf);
			String str=pps.getProperty(target);
			return str;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null ;
	}
	public static String getWhere(Map<String ,Object> map){
		String where  = "where 1=1" ;
		
		for (Map.Entry<String ,Object> entry : map.entrySet()) {  
			  
			if(entry.getKey().startsWith("w.")&&!"".equalsIgnoreCase(entry.getValue().toString() )){
				
				if(entry.getKey().startsWith("w.like.")){
					where  = where + " and " + entry.getKey().replace("w.like.", "")+ " like '%"  + entry.getValue().toString() + "%'" ;
				}else if(entry.getKey().startsWith("w.")&&(entry.getKey().indexOf("like") ==-1)&&!entry.getKey().startsWith("w.userDefine")){
					where  = where + " and " + entry.getKey().replace("w.", "")+ " = '"  + entry.getValue().toString() + "'" ;
					
				}else if(entry.getKey().startsWith("w.userDefine")){
					where  = where + " and ( "  + entry.getValue().toString() + ")" ;					
				}
				
			}
		  
		} 
		
		return where;
	}
	
}
