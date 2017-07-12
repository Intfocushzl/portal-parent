package com.yonghui.portal.model.channelTransparency;

import java.io.Serializable;
import java.util.Date;

public class OdsHanaHtsr implements Serializable {


	private static final long serialVersionUID = -2555798664139943425L;
	private String tlx;

	private String cprCreate;

	private String purchOrg;

	private String tpurchOrg;

	private String purGroup;

	private String tpurGroup;

	private String plant;

	private String tplant;

	private String knart;

	private String tknart;

	private String vendor;

	private String tvendor;

	private String tzopc0009;

	private String zopc2000;

	private String zpurc025;

	private String gmVldFr;

	private String tmcEndda;

	private Double amount;

	private String updatetime;

	public String getTlx() {
		return tlx;
	}

	public void setTlx(String tlx) {
		this.tlx = tlx == null ? null : tlx.trim();
	}

	public String getCprCreate() {
		return cprCreate;
	}

	public void setCprCreate(String cprCreate) {
		this.cprCreate = cprCreate;
	}

	public String getPurchOrg() {
		return purchOrg;
	}

	public void setPurchOrg(String purchOrg) {
		this.purchOrg = purchOrg == null ? null : purchOrg.trim();
	}

	public String getTpurchOrg() {
		return tpurchOrg;
	}

	public void setTpurchOrg(String tpurchOrg) {
		this.tpurchOrg = tpurchOrg == null ? null : tpurchOrg.trim();
	}

	public String getPurGroup() {
		return purGroup;
	}

	public void setPurGroup(String purGroup) {
		this.purGroup = purGroup == null ? null : purGroup.trim();
	}

	public String getTpurGroup() {
		return tpurGroup;
	}

	public void setTpurGroup(String tpurGroup) {
		this.tpurGroup = tpurGroup == null ? null : tpurGroup.trim();
	}

	public String getPlant() {
		return plant;
	}

	public void setPlant(String plant) {
		this.plant = plant == null ? null : plant.trim();
	}

	public String getTplant() {
		return tplant;
	}

	public void setTplant(String tplant) {
		this.tplant = tplant == null ? null : tplant.trim();
	}

	public String getKnart() {
		return knart;
	}

	public void setKnart(String knart) {
		this.knart = knart == null ? null : knart.trim();
	}

	public String getTknart() {
		return tknart;
	}

	public void setTknart(String tknart) {
		this.tknart = tknart == null ? null : tknart.trim();
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor == null ? null : vendor.trim();
	}

	public String getTvendor() {
		return tvendor;
	}

	public void setTvendor(String tvendor) {
		this.tvendor = tvendor == null ? null : tvendor.trim();
	}

	public String getTzopc0009() {
		return tzopc0009;
	}

	public void setTzopc0009(String tzopc0009) {
		this.tzopc0009 = tzopc0009 == null ? null : tzopc0009.trim();
	}

	public String getZopc2000() {
		return zopc2000;
	}

	public void setZopc2000(String zopc2000) {
		this.zopc2000 = zopc2000 == null ? null : zopc2000.trim();
	}

	public String getZpurc025() {
		return zpurc025;
	}

	public void setZpurc025(String zpurc025) {
		this.zpurc025 = zpurc025 == null ? null : zpurc025.trim();
	}

	public String getGmVldFr() {
		return gmVldFr;
	}

	public void setGmVldFr(String gmVldFr) {
		this.gmVldFr = gmVldFr;
	}

	public String getTmcEndda() {
		return tmcEndda;
	}

	public void setTmcEndda(String tmcEndda) {
		this.tmcEndda = tmcEndda;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
}
