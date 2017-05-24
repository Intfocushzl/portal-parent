package com.yonghui.portal.model.horse;

public class HorseSort implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	private double currentMonthScore;
	private int currentMonthGidUpDown;

	private double threeMonthScore;
	private int threeMonthSort;
	private int threeMonthGidUpDown;
	private String remark;
	private String result;

	private int lkpDate;
	private String shopId;
	private String shopName;
	private int groupId;
	private String groupName;

	private String indexName;
	private int allScore;

	private String theValue;
	private String theValueComp;
	private double score;

	private int sortID; // 新增 排名
	private String upDown; // 新增 均值上下标记
	private String abc; // 新增 ABC
	private int shopNum; // 新增 参与排名门店数
	private String avgScore; // 新增 累计平均

	private double monthScore1; // 第一个月
	private double monthScore2; // 第二个月
	private double monthScore3; // 第三个月
	private double monthScore4; // 第四个月
	private double monthScore5; // 第五个月
	private double monthScore6; // 第六个月
	private double monthAvgScore; // 第六个月
	
	private int measureNum1; // 第一个月参与排名的指标数
	private int measureNum2; // 第二个月参与排名的指标数
	private int measureNum3; // 第三个月参与排名的指标数
	private int measureNum4; // 第四个月参与排名的指标数
	private int measureNum5; // 第五个月参与排名的指标数
	private int measureNum6; // 第六个月参与排名的指标数
	
	private String calculateLogic; // 计算逻辑
	private String dataSource; 	   // 取数来源	

	public double getCurrentMonthScore() {
		return currentMonthScore;
	}

	public void setCurrentMonthSoce(double currentMonthScore) {
		this.currentMonthScore = currentMonthScore;
	}

	public int getCurrentMonthGidUpDown() {
		return currentMonthGidUpDown;
	}

	public void setCurrentMonthGidUpDown(int currentMonthGidUpDown) {
		this.currentMonthGidUpDown = currentMonthGidUpDown;
	}

	public double getThreeMonthScore() {
		return threeMonthScore;
	}

	public void setThreeMonthScore(double threeMonthScore) {
		this.threeMonthScore = threeMonthScore;
	}

	public int getThreeMonthSort() {
		return threeMonthSort;
	}

	public void setThreeMonthSort(int threeMonthSort) {
		this.threeMonthSort = threeMonthSort;
	}

	public int getThreeMonthGidUpDown() {
		return threeMonthGidUpDown;
	}

	public void setThreeMonthGidUpDown(int threeMonthGidUpDown) {
		this.threeMonthGidUpDown = threeMonthGidUpDown;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public int getAllScore() {
		return allScore;
	}

	public void setAllScore(int allScore) {
		this.allScore = allScore;
	}

	public String getTheValue() {
		return theValue;
	}

	public void setTheValue(String theValue) {
		this.theValue = theValue;
	}

	public String getTheValueComp() {
		return theValueComp;
	}

	public void setTheValueComp(String theValueComp) {
		this.theValueComp = theValueComp;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public int getSortID() {
		return sortID;
	}

	public void setSortID(int sortID) {
		this.sortID = sortID;
	}

	public String getUpDown() {
		return upDown;
	}

	public void setUpDown(String upDown) {
		this.upDown = upDown;
	}

	public String getAbc() {
		return abc;
	}

	public void setAbc(String abc) {
		this.abc = abc;
	}

	public int getShopNum() {
		return shopNum;
	}

	public void setShopNum(int shopNum) {
		this.shopNum = shopNum;
	}

	public String getAvgScore() {
		return avgScore;
	}

	public void setAvgScore(String avgScore) {
		this.avgScore = avgScore;
	}

	public void setCurrentMonthScore(double currentMonthScore) {
		this.currentMonthScore = currentMonthScore;
	}

	public double getMonthScore1() {
		return monthScore1;
	}

	public void setMonthScore1(double monthScore1) {
		this.monthScore1 = monthScore1;
	}

	public double getMonthScore2() {
		return monthScore2;
	}

	public void setMonthScore2(double monthScore2) {
		this.monthScore2 = monthScore2;
	}

	public double getMonthScore3() {
		return monthScore3;
	}

	public void setMonthScore3(double monthScore3) {
		this.monthScore3 = monthScore3;
	}

	public double getMonthScore4() {
		return monthScore4;
	}

	public void setMonthScore4(double monthScore4) {
		this.monthScore4 = monthScore4;
	}

	public double getMonthScore5() {
		return monthScore5;
	}

	public void setMonthScore5(double monthScore5) {
		this.monthScore5 = monthScore5;
	}

	public double getMonthScore6() {
		return monthScore6;
	}

	public void setMonthScore6(double monthScore6) {
		this.monthScore6 = monthScore6;
	}

	public double getMonthAvgScore() {
		return monthAvgScore;
	}

	public void setMonthAvgScore(double monthAvgScore) {
		this.monthAvgScore = monthAvgScore;
	}

	public int getLkpDate() {
		return lkpDate;
	}

	public void setLkpDate(int lkpDate) {
		this.lkpDate = lkpDate;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupID(int groupID) {
		this.groupId = groupID;
	}
	
	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getMeasureNum1() {
		return measureNum1;
	}

	public void setMeasureNum1(int measureNum1) {
		this.measureNum1 = measureNum1;
	}

	public int getMeasureNum2() {
		return measureNum2;
	}

	public void setMeasureNum2(int measureNum2) {
		this.measureNum2 = measureNum2;
	}

	public int getMeasureNum3() {
		return measureNum3;
	}

	public void setMeasureNum3(int measureNum3) {
		this.measureNum3 = measureNum3;
	}

	public int getMeasureNum4() {
		return measureNum4;
	}

	public void setMeasureNum4(int measureNum4) {
		this.measureNum4 = measureNum4;
	}

	public int getMeasureNum5() {
		return measureNum5;
	}

	public void setMeasureNum5(int measureNum5) {
		this.measureNum5 = measureNum5;
	}

	public int getMeasureNum6() {
		return measureNum6;
	}

	public void setMeasureNum6(int measureNum6) {
		this.measureNum6 = measureNum6;
	}

	public String getCalculateLogic() {
		return calculateLogic;
	}

	public void setCalculateLogic(String calculateLogic) {
		this.calculateLogic = calculateLogic;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
	
}
