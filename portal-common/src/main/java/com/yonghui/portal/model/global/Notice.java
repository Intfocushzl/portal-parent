package com.yonghui.portal.model.global;

import java.io.Serializable;

/**
 * 
 * @author handx 公告通知实体类
 *
 */
public class Notice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private String title;
	private String introduction;
	private String content;
	private Integer userid;
	private String createtime;
	private String updatetime;
	private Integer position;
	private Integer ishide;
	private String month;
	private String day;

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getIshide() {
		return ishide;
	}

	public void setIshide(Integer ishide) {
		this.ishide = ishide;
	}

	@Override
	public String toString() {
		return "Notice [id=" + id + ", title=" + title + ", introduction=" + introduction + ", content=" + content
				+ ", userid=" + userid + ", createtime=" + createtime + ", updatetime=" + updatetime + ", position="
				+ position + ", ishide=" + ishide + "]";
	}

}