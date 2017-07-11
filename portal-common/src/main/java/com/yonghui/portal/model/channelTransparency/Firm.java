package com.yonghui.portal.model.channelTransparency;

import java.io.Serializable;

public class Firm implements Serializable {

	private static final long serialVersionUID = -4348575981742078466L;
	private int groupid;
	private String groupname;
	private String gname;
	private int type;
	public int getGroupid() {
		return groupid;
	}
	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String getGname() {
		return gname;
	}
	public void setGname(String gname) {
		this.gname = gname;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	
}
