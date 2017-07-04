package com.yonghui.portal.model.sys;

public class PosterImportGoods implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    //海报档期id
    private Integer posterId;

    //开始时间
    private String startDate;

    //结束时间
    private String endDate;

    //主题
    private String theme;

    //商行id
    private String groupId;

    //商行
    private String groupName;

    //DM表现方式ID
    private Integer dmId;

    //DM表现方式
    private String dmName;

    //陈列表现方式
    private String displayMode;

    //商品属性
    private String goodsAttribute;

    //区域
    private String area;

    //商品编码
    private String goodsId;

    //商品条码
    private String barcode;

    //商品名称
    private String name;

    //销售单位
    private String unit;

    //进价
    private String inPrice;

    //促销进价
    private String promotionInPrice;

    //原售价
    private String salePrice;

    //促销售价
    private String promotionSalePrice;

    //正常毛利率
    private String grossMargin;

    //促销毛利率
    private String promotionGrossMargin;

    //预估销量
    private String estimatedSaleNumber;

    //预估销售额
    private String estimatedSaleAmount;

    //海报收取费用
    private String posterFee;

    //进口国
    private String country;

    //调价方式
    private String priceMethod;

    //供应商分摊率
    private String supplieRating;

    //配送方式
    private String deliveryMethod;

    //是否能退货
    private String returnFlag;

    //供应商编码
    private String supplierCode;

    //供应商名称
    private String supplierName;

    private String jobNumber;

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDmName() {
        return dmName;
    }

    public void setDmName(String dmName) {
        this.dmName = dmName;
    }

    public String getDisplayMode() {
        return displayMode;
    }

    public void setDisplayMode(String displayMode) {
        this.displayMode = displayMode;
    }

    public String getGoodsAttribute() {
        return goodsAttribute;
    }

    public void setGoodsAttribute(String goodsAttribute) {
        this.goodsAttribute = goodsAttribute;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getInPrice() {
        return inPrice;
    }

    public void setInPrice(String inPrice) {
        this.inPrice = inPrice;
    }

    public String getPromotionInPrice() {
        return promotionInPrice;
    }

    public void setPromotionInPrice(String promotionInPrice) {
        this.promotionInPrice = promotionInPrice;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getPromotionSalePrice() {
        return promotionSalePrice;
    }

    public void setPromotionSalePrice(String promotionSalePrice) {
        this.promotionSalePrice = promotionSalePrice;
    }

    public String getGrossMargin() {
        return grossMargin;
    }

    public void setGrossMargin(String grossMargin) {
        this.grossMargin = grossMargin;
    }

    public String getPromotionGrossMargin() {
        return promotionGrossMargin;
    }

    public void setPromotionGrossMargin(String promotionGrossMargin) {
        this.promotionGrossMargin = promotionGrossMargin;
    }

    public String getEstimatedSaleNumber() {
        return estimatedSaleNumber;
    }

    public void setEstimatedSaleNumber(String estimatedSaleNumber) {
        this.estimatedSaleNumber = estimatedSaleNumber;
    }

    public String getEstimatedSaleAmount() {
        return estimatedSaleAmount;
    }

    public void setEstimatedSaleAmount(String estimatedSaleAmount) {
        this.estimatedSaleAmount = estimatedSaleAmount;
    }

    public String getPosterFee() {
        return posterFee;
    }

    public void setPosterFee(String posterFee) {
        this.posterFee = posterFee;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPriceMethod() {
        return priceMethod;
    }

    public void setPriceMethod(String priceMethod) {
        this.priceMethod = priceMethod;
    }

    public String getSupplieRating() {
        return supplieRating;
    }

    public void setSupplieRating(String supplieRating) {
        this.supplieRating = supplieRating;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public String getReturnFlag() {
        return returnFlag;
    }

    public void setReturnFlag(String returnFlag) {
        this.returnFlag = returnFlag;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Integer getPosterId() {
        return posterId;
    }

    public void setPosterId(Integer posterId) {
        this.posterId = posterId;
    }

    public Integer getDmId() {
        return dmId;
    }

    public void setDmId(Integer dmId) {
        this.dmId = dmId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
