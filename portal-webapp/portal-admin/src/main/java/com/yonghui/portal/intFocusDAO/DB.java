package com.yonghui.portal.intFocusDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.PreparedStatement;

public class DB {
	private String ip ; 
	private String database;
	private String user;
	private String pwd;
	private String port;
	
	public DB(String ip , String port , String database , String user , String pwd){
		this.ip = ip ;
		this.port = port;
		this.database = database;
		this.user =  user;
		this.pwd = pwd ;
	}
	public DB(){
		this.ip = "123.59.75.85" ;
		this.port = "3306";
		this.database = "platform";
		this.user =  "biuser";
		this.pwd = "1234509876" ;
	}
	
	public Connection getConnection(){
		
		Connection conn = null ; 
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = DriverManager.getConnection("jdbc:mysql://"+ip+":"+port+"/"+database+"?characterEncoding=utf8&amp;useUnicode=true&allowMultiQueries=true"
																						,user
																						,pwd);
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return conn ; 
	}
	
	public Statement getStatemente(Connection conn){
		Statement stmt = null ;
		
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return stmt ;
	}
	public PreparedStatement getPreparedStatemente(Connection conn){
		PreparedStatement stmt = null ;
		
		try {
			stmt = conn.prepareStatement("");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return stmt ;
	}
	public ResultSet getResultSet(Statement stmt , String sql ){
		ResultSet rs = null ;
				
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs ;
	}
	public void close(Connection conn){
		
		try {
			if(null != conn){
				conn.close();
				conn = null ;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void close(Statement stmt){
		
		try {
			if(null != stmt){
				stmt.close();
				stmt = null ;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void close(ResultSet rs){
		
		try {
			if(null != rs){
				rs.close();
				rs = null ;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void close(PreparedStatement stmt){
		
		try {
			if(null != stmt){
				stmt.close();
				stmt = null ;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	
}
