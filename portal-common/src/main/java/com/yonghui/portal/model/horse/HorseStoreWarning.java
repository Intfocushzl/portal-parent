package com.yonghui.portal.model.horse;

/**
 * 
 * @author handx 天天赛马预警
 *
 */
public class HorseStoreWarning implements java.io.Serializable{

	private String sdate;
	private String areamans;
	private String sapshopid;
	private String shopname;
	private Integer groupid;
	private String groupname;
	private Integer gid;
	private Double sale;//销售--门店值
	private String sale_abc;//销售成长档次
	private String sale_flag;//门店销售预警亮灯标记
	private String sale_flag1;//小区销售预警亮灯标记
	private Double profitrate;//毛利率--门店值
	private String profitrate_abc;//毛利率档次
	private String profitrate_flag;//门店毛利率预警亮灯标记
	private String profitrate_flag1;//小区毛利率预警亮灯标记
	private Double sheetqty;//客流--门店值
	private String sheetqty_abc;//客流档次
	private String sheetqty_flag;//门店客流预警标记
	private String sheetqty_flag1;//小区客流预警标记
	private Double kd;//客单--门店值
	private String kd_abc;//客单档次
	private String kd_flag;//门店客单预警标记
	private String kd_flag1;//小区客单预警标记
	private Double zzts;//周转天数--门店值
	private String zzts_abc;//周转天数档次
	private String zzts_flag;//门店周转天数预警标记
	private String zzts_flag1;//小区周转天数预警标记
	private Double jxc;//进销存--门店值
	private String jxc_abc;//进销存档次
	private String jxc_flag;//门店进销存预警标记
	private String jxc_flag1;//小区进销存预警标记
	
	public String getSdate() {
		return sdate;
	}
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
	public String getAreamans() {
		return areamans;
	}
	public void setAreamans(String areamans) {
		this.areamans = areamans;
	}
	public String getSapshopid() {
		return sapshopid;
	}
	public void setSapshopid(String sapshopid) {
		this.sapshopid = sapshopid;
	}
	public String getShopname() {
		return shopname;
	}
	public void setShopname(String shopname) {
		this.shopname = shopname;
	}
	public Integer getGroupid() {
		return groupid;
	}
	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public Integer getGid() {
		return gid;
	}
	public void setGid(Integer gid) {
		this.gid = gid;
	}
	public Double getSale() {
		return sale;
	}
	public void setSale(Double sale) {
		this.sale = sale;
	}
	public String getSale_abc() {
		return sale_abc;
	}
	public void setSale_abc(String sale_abc) {
		this.sale_abc = sale_abc;
	}
	
	public Double getProfitrate() {
		return profitrate;
	}
	public void setProfitrate(Double profitrate) {
		this.profitrate = profitrate;
	}
	public String getProfitrate_abc() {
		return profitrate_abc;
	}
	public void setProfitrate_abc(String profitrate_abc) {
		this.profitrate_abc = profitrate_abc;
	}
	public String getProfitrate_flag() {
		return profitrate_flag;
	}
	public void setProfitrate_flag(String profitrate_flag) {
		this.profitrate_flag = profitrate_flag;
	}
	public String getProfitrate_flag1() {
		return profitrate_flag1;
	}
	public void setProfitrate_flag1(String profitrate_flag1) {
		this.profitrate_flag1 = profitrate_flag1;
	}
	public Double getSheetqty() {
		return sheetqty;
	}
	public void setSheetqty(Double sheetqty) {
		this.sheetqty = sheetqty;
	}
	public String getSheetqty_abc() {
		return sheetqty_abc;
	}
	public void setSheetqty_abc(String sheetqty_abc) {
		this.sheetqty_abc = sheetqty_abc;
	}
	public String getSheetqty_flag() {
		return sheetqty_flag;
	}
	public void setSheetqty_flag(String sheetqty_flag) {
		this.sheetqty_flag = sheetqty_flag;
	}
	public String getSheetqty_flag1() {
		return sheetqty_flag1;
	}
	public void setSheetqty_flag1(String sheetqty_flag1) {
		this.sheetqty_flag1 = sheetqty_flag1;
	}
	public Double getKd() {
		return kd;
	}
	public void setKd(Double kd) {
		this.kd = kd;
	}
	public String getKd_abc() {
		return kd_abc;
	}
	public void setKd_abc(String kd_abc) {
		this.kd_abc = kd_abc;
	}
	public String getKd_flag() {
		return kd_flag;
	}
	public void setKd_flag(String kd_flag) {
		this.kd_flag = kd_flag;
	}
	public String getKd_flag1() {
		return kd_flag1;
	}
	public void setKd_flag1(String kd_flag1) {
		this.kd_flag1 = kd_flag1;
	}
	public Double getZzts() {
		return zzts;
	}
	public void setZzts(Double zzts) {
		this.zzts = zzts;
	}
	public String getZzts_abc() {
		return zzts_abc;
	}
	public void setZzts_abc(String zzts_abc) {
		this.zzts_abc = zzts_abc;
	}
	public String getZzts_flag() {
		return zzts_flag;
	}
	public void setZzts_flag(String zzts_flag) {
		this.zzts_flag = zzts_flag;
	}
	public String getZzts_flag1() {
		return zzts_flag1;
	}
	public void setZzts_flag1(String zzts_flag1) {
		this.zzts_flag1 = zzts_flag1;
	}
	public Double getJxc() {
		return jxc;
	}
	public void setJxc(Double jxc) {
		this.jxc = jxc;
	}
	public String getJxc_abc() {
		return jxc_abc;
	}
	public void setJxc_abc(String jxc_abc) {
		this.jxc_abc = jxc_abc;
	}
	public String getJxc_flag() {
		return jxc_flag;
	}
	public void setJxc_flag(String jxc_flag) {
		this.jxc_flag = jxc_flag;
	}
	public String getJxc_flag1() {
		return jxc_flag1;
	}
	public void setJxc_flag1(String jxc_flag1) {
		this.jxc_flag1 = jxc_flag1;
	}
	public String getSale_flag() {
		return sale_flag;
	}
	public void setSale_flag(String sale_flag) {
		this.sale_flag = sale_flag;
	}
	public String getSale_flag1() {
		return sale_flag1;
	}
	public void setSale_flag1(String sale_flag1) {
		this.sale_flag1 = sale_flag1;
	}
}
