package com.yonghui.portal.model.horse;

import java.io.Serializable;

/**
 * 
 * @author shengwm 新版天天赛马
 *
 */
public class EveryDayHorseRacingNew implements Serializable {
	//日期
	private String sdate;
	
	//店编
	private String sapshopid;
	
	//店名
	private String sname;
	
	//商行ID
	private String groupid;
	
	//商行名称
	private String groupname;
	
	//销售同比
	private Double saleRate;
	
	//毛利率同比
	private Double profitRate;
	
	//客流
	private Integer custSheet;
	
	//客单价
	private Double price;
	
	//周转天数
	private Double turnOverDay;
	
	//进销存
	private Double invoicing;
	
	//分数总计
	private Double totalScore;
	
	//项目名称
	private String projectName;
	
	//门店值
	private Double shopValue;
	
	//对标值
	private Double onValue;
	
	//排名
	private Integer ranking;
	
	//均值上下标记
	private String flag;
	
	//参与排名门店数
	private Integer shopNum;
	
	//得分
	private Double score;
	
	//abc等级
	private String abc;
	
	
	
	public Integer getShopNum() {
		return shopNum;
	}
	public String getAbc() {
		return abc;
	}
	public void setAbc(String abc) {
		this.abc = abc;
	}
	public void setShopNum(Integer shopNum) {
		this.shopNum = shopNum;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Double getShopValue() {
		return shopValue;
	}
	public void setShopValue(Double shopValue) {
		this.shopValue = shopValue;
	}
	public Double getOnValue() {
		return onValue;
	}
	public void setOnValue(Double onValue) {
		this.onValue = onValue;
	}
	public Integer getRanking() {
		return ranking;
	}
	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public String getSdate() {
		return sdate;
	}
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
	public String getSapshopid() {
		return sapshopid;
	}
	public void setSapshopid(String sapshopid) {
		this.sapshopid = sapshopid;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public Double getSaleRate() {
		return saleRate;
	}
	public void setSaleRate(Double saleRate) {
		this.saleRate = saleRate;
	}
	public Double getProfitRate() {
		return profitRate;
	}
	public void setProfitRate(Double profitRate) {
		this.profitRate = profitRate;
	}
	public Integer getCustSheet() {
		return custSheet;
	}
	public void setCustSheet(Integer custSheet) {
		this.custSheet = custSheet;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getTurnOverDay() {
		return turnOverDay;
	}
	public void setTurnOverDay(Double turnOverDay) {
		this.turnOverDay = turnOverDay;
	}
	public Double getInvoicing() {
		return invoicing;
	}
	public void setInvoicing(Double invoicing) {
		this.invoicing = invoicing;
	}
	public Double getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(Double totalScore) {
		this.totalScore = totalScore;
	}

	


}
