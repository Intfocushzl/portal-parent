package com.yonghui.portal.util.poster;

/**
 * Created by liuwei on 2017/6/30.
 */
public class GoodsConstants {

    //商行id
    public static String[] groupId = {"210", "211", "221", "222", "223", "231", "232", "233", "234", "235", "242", "241"};

    //商行名称
    public static String[] groupName = {"加工", "餐饮", "干货", "蔬果", "肉禽水产", "烟酒饮料", "休闲食品", "干性杂货日配", "清洁用品", "家用文体家电", "家居生活馆", "男女装"};

    //DM表现方式ID
    public static String[] dmId = {"1", "2", "3"};

    //DM表现方式
    public static String[] dmName = {"正常商品", "新品", "换购", "惊爆", "30%OFF", "50%OFF", "买一送一", "第二件5折", "买2件省更多", "均一价", "感恩5折", "感恩价", "品类折扣", "品类满减", "厂商周"};

    //陈列表现方式
    public static String[] displayMode = {"流量", "关联", "主推"};

    //商品属性
    public static String[] goodsAttribute = {"全国", "地区"};

    //调价方式
    public static String[] priceMethod = {"促销扣款", "低价入库"};

    //配送方式
    public static String[] deliveryMethod = {"直送", "直通", "配送"};

    //是否能退货
    public static String[] returnFlag = {"是", "否"};

    //判断商行id是否存在
    public static boolean queryGroupId(String param) {
        boolean result = false;
        for (String item : groupId) {
            if (item.equals(param)) {
                result = true;
                break;
            }
        }
        return result;
    }

    ////商行名称
    public static boolean queryGroupName(String param) {
        boolean result = false;
        for (String item : groupName) {
            if (item.equals(param)) {
                result = true;
                break;
            }
        }
        return result;
    }

    //DM表现方式ID
    public static boolean queryDmId(String param) {
        boolean result = false;
        for (String item : dmId) {
            if (item.equals(param)) {
                result = true;
                break;
            }
        }
        return result;
    }

    //DM表现方式
    public static boolean queryDmName(String param) {
        boolean result = false;
        for (String item : dmName) {
            if (item.equals(param)) {
                result = true;
                break;
            }
        }
        return result;
    }

    //陈列表现方式
    public static boolean queryDisplayMode(String param) {
        boolean result = false;
        for (String item : displayMode) {
            if (item.equals(param)) {
                result = true;
                break;
            }
        }
        return result;
    }

    //商品属性
    public static boolean queryGoodsAttribute(String param) {
        boolean result = false;
        for (String item : goodsAttribute) {
            if (item.equals(param)) {
                result = true;
                break;
            }
        }
        return result;
    }

    //调价方式
    public static boolean queryPriceMethod(String param) {
        boolean result = false;
        for (String item : priceMethod) {
            if (item.equals(param)) {
                result = true;
                break;
            }
        }
        return result;
    }

    //配送方式
    public static boolean queryDeliveryMethod(String param) {
        boolean result = false;
        for (String item : deliveryMethod) {
            if (item.equals(param)) {
                result = true;
                break;
            }
        }
        return result;
    }

    //是否能退货
    public static boolean queryReturnFlag(String param) {
        boolean result = false;
        for (String item : returnFlag) {
            if (item.equals(param)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public Double isNumber(String str) {
        return Double.parseDouble(str);
    }
}
