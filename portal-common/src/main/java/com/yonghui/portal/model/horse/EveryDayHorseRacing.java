package com.yonghui.portal.model.horse;

/**
 * 
 * @author handx 天天赛马
 *
 */
public class EveryDayHorseRacing {

	private String sdate;
	private String sname;
	private String shopid;
	private String groupname;
	private String groupid;
	private String title;
	private Double total;// 总计
	private Double sale;// 销售
	private Double turnoverDays;// 周转天数
	private Double inventory;// 进销存
	private Double score;
	
	private String xstbDes;//销售对标说明
	private Double xstbValueComp;//对标值
	private Double xstbValue;//门店值
	private Double xstb;//得分
	
	private String jxczqxDes;//进销存对标说明
	private Double jxczqxValueComp;//对标值
	private Double jxczqxValue;//门店值
	private Double jxczqx;//得分
	
	private String zztsDes;//周转天数对标说明
	private Double zztsComp;//对标值
	private Double zztsValue;//门店值
	private Double zzts;//得分
	
	public String getXstbDes() {
		return xstbDes;
	}

	public void setXstbDes(String xstbDes) {
		this.xstbDes = xstbDes;
	}

	public Double getXstbValueComp() {
		return xstbValueComp;
	}

	public void setXstbValueComp(Double xstbValueComp) {
		this.xstbValueComp = xstbValueComp;
	}

	public Double getXstbValue() {
		return xstbValue;
	}

	public void setXstbValue(Double xstbValue) {
		this.xstbValue = xstbValue;
	}

	public Double getXstb() {
		return xstb;
	}

	public void setXstb(Double xstb) {
		this.xstb = xstb;
	}

	public String getJxczqxDes() {
		return jxczqxDes;
	}

	public void setJxczqxDes(String jxczqxDes) {
		this.jxczqxDes = jxczqxDes;
	}

	public Double getJxczqxValueComp() {
		return jxczqxValueComp;
	}

	public void setJxczqxValueComp(Double jxczqxValueComp) {
		this.jxczqxValueComp = jxczqxValueComp;
	}

	public Double getJxczqxValue() {
		return jxczqxValue;
	}

	public void setJxczqxValue(Double jxczqxValue) {
		this.jxczqxValue = jxczqxValue;
	}

	public Double getJxczqx() {
		return jxczqx;
	}

	public void setJxczqx(Double jxczqx) {
		this.jxczqx = jxczqx;
	}

	public String getZztsDes() {
		return zztsDes;
	}

	public void setZztsDes(String zztsDes) {
		this.zztsDes = zztsDes;
	}

	public Double getZztsComp() {
		return zztsComp;
	}

	public void setZztsComp(Double zztsComp) {
		this.zztsComp = zztsComp;
	}

	public Double getZztsValue() {
		return zztsValue;
	}

	public void setZztsValue(Double zztsValue) {
		this.zztsValue = zztsValue;
	}

	public Double getZzts() {
		return zzts;
	}

	public void setZzts(Double zzts) {
		this.zzts = zzts;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getShopid() {
		return shopid;
	}

	public void setShopid(String shopid) {
		this.shopid = shopid;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Double getSale() {
		return sale;
	}

	public void setSale(Double sale) {
		this.sale = sale;
	}

	public Double getTurnoverDays() {
		return turnoverDays;
	}

	public void setTurnoverDays(Double turnoverDays) {
		this.turnoverDays = turnoverDays;
	}

	public Double getInventory() {
		return inventory;
	}

	public void setInventory(Double inventory) {
		this.inventory = inventory;
	}

}
