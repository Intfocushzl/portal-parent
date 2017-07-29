package com.yonghui.portal.service.impl.manage;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.mapper.manage.HrProfitMapper;
import com.yonghui.portal.model.global.User;
import com.yonghui.portal.model.manage.HrProfit;
import com.yonghui.portal.service.manage.HrProfitService;
import com.yonghui.portal.util.StringUtils;
import com.yonghui.portal.util.report.columns.HttpBasicPostUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by liuwei on 2017/7/17.
 * 人事分红serviceImpl
 */
public class HrProfitServiceImpl implements HrProfitService {

    @Autowired
    private HrProfitMapper hrProfitMapper;

    @Autowired
    private HttpBasicPostUtil util;

    private static final String URL = "http://10.0.66.65:50000/RESTAdapter/HR_NOR028";


    public List<Map<String, Object>> tmpProfitList(String jobNumber) {
        return hrProfitMapper.tmpProfitList(jobNumber);
    }

    public List<Map<String, Object>> profitList(String sMonth, String shopid, String oldshopid) {
        return hrProfitMapper.profitList(sMonth, shopid, oldshopid);
    }


    //根据jobNumber查询商品海报重复导入吻合的数据
    public List<Map<String, Object>> profitTmpJoinList(String jobNumber) {
        return hrProfitMapper.profitTmpJoinList(jobNumber);
    }

    //保存到临时表
    public int insertProfitTmp(List<HrProfit> list, String jobNumber) {
        //先删除临时表之前的数据
        hrProfitMapper.deleteProfitTmp(jobNumber);
        return hrProfitMapper.insertProfitTmp(list);
    }

    //根据id删除正式表已存在的
    @Transactional
    public List<Map<String, Object>> deleteProfit(List<Map<String, Object>> list, String jobNumber) {
        //删除正式匹配上的数据
        for (Map<String, Object> item : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("lkpMonth", item.get("lkpMonth"));
            map.put("shopid", item.get("shopid"));
            hrProfitMapper.deleteProfit(map);
        }
        //将临时表的数据导入到正式
        hrProfitMapper.insertProfit();
        List<Map<String, Object>> profitList = hrProfitMapper.tmpProfitList(jobNumber);
        //清空临时表
        hrProfitMapper.deleteProfitTmp(jobNumber);

        return profitList;
    }

    //将数据从临时表导入正式表
    public int insertProfit() {
        return hrProfitMapper.insertProfit();
    }

    //根据jobnumber，删除临时表里面的数据
    public int deleteProfitTmp(String jobNumber) {
        return hrProfitMapper.deleteProfitTmp(jobNumber);
    }

    //分红标准维护
    public int editRule(Integer id, Integer empNum, BigDecimal standardRule, BigDecimal friRule, BigDecimal secRule, Integer runtype) {
        Map<String, Object> map = new HashMap<>();
        StringBuffer editRecord = new StringBuffer();
        if (id != null) {
            map.put("id", id);
        }
        if (empNum != null) {
            map.put("empNum", empNum);
            editRecord.append("empNum,");
        }
        if (standardRule != null) {
            map.put("standardRule", standardRule);
            editRecord.append("standardRule,");
        }
        if (friRule != null) {
            map.put("friRule", friRule);
            editRecord.append("friRule,");
        }
        if (secRule != null) {
            map.put("secRule", secRule);
            editRecord.append("secRule,");
        }
        if (runtype != null) {
            map.put("runtype", runtype);
            editRecord.append("runtype,");
        }
        //处理editRecord字段，有可能记录多个
        List<Map<String, Object>> record = hrProfitMapper.queryRuleById(map.get("id").toString());
        if (record.size() != 0 && record.get(0).get("editRecord") != null) {
            String result = record.get(0).get("editRecord").toString() + editRecord;
            Map<String, Object> ruleMap = new HashMap<>();
            StringBuffer strBuf = new StringBuffer();
            for (String item : result.split(",")) {
                if (!ruleMap.containsKey(item)) {
                    ruleMap.put(item, item);
                    strBuf.append(item + ",");
                }
            }
            map.put("editRecord", strBuf.toString());
        } else {
            map.put("editRecord", editRecord.toString());
        }
        return hrProfitMapper.editRule(map);
    }

    //分红人员维护
    public int editEmp(Integer id, Float guarantees, Integer flagBonus, Date turnDate, String shopName, String groupName) throws Exception {
        Map<String, Object> map = new HashMap<>();
        StringBuffer editRecord = new StringBuffer();
        if (id != null) {
            map.put("id", id);
        }
        if (guarantees != null) {
            map.put("guarantees", guarantees);
            editRecord.append("guarantees,");
        }
        if (flagBonus != null) {
            map.put("flagBonus", flagBonus);
            editRecord.append("flagBonus,");
        }
        if (turnDate != null) {
            map.put("turnDate", turnDate);
            editRecord.append("turnDate,");
        }
        if (shopName != null) {
            map.put("shopName", shopName);
            editRecord.append("shopName,");
        }
        if (groupName != null) {
            map.put("groupName", groupName);
            editRecord.append("groupName,");
        }
        //处理editRecord字段，有可能记录多个
        List<Map<String, Object>> record = hrProfitMapper.queryEmpById(map.get("id").toString());
        if (record.size() != 0 && record.get(0).get("editRecord") != null) {
            String result = record.get(0).get("editRecord").toString() + editRecord;
            Map<String, Object> ruleMap = new HashMap<>();
            StringBuffer strBuf = new StringBuffer();
            for (String item : result.split(",")) {
                if (!ruleMap.containsKey(item)) {
                    ruleMap.put(item, item);
                    strBuf.append(item + ",");
                }
            }
            map.put("editRecord", strBuf.toString());
        } else {
            map.put("editRecord", editRecord.toString());
        }
        if (map.get("shopName") != null) {
            Map<String, Object> parMap = new HashMap<>();
            //如果是修改门店，或者小店
            parMap.put("shopName", map.get("shopName"));
            List<Map<String, Object>> resList = hrProfitMapper.queryShopID(parMap);
            if (resList.size() == 0 || resList.size() > 1) {
                throw new Exception("输入的门店名称有误");
            } else {
                map.put("shopId", resList.get(0).get("ShopID"));
                map.put("shopName", resList.get(0).get("ShopName"));
            }
        }
        if (map.get("groupName") != null) {
            Map<String, Object> parMap = new HashMap<>();
            parMap.put("groupName", map.get("groupName"));
            List<Map<String, Object>> resList = hrProfitMapper.queryGroupID(parMap);
            if (resList.size() == 0 || resList.size() > 1) {
                throw new Exception("输入的小店名称有误");
            } else {
                map.put("groupId", resList.get(0).get("groupid"));
                map.put("groupName", resList.get(0).get("groupname"));
            }
        }
        return hrProfitMapper.editEmp(map);
    }

    //查询分红标准
    public List<Map<String, Object>> queryRule(Map<String, Object> map) {
        return hrProfitMapper.queryRule(map);
    }

    //查询分红人员
    public List<Map<String, Object>> queryEmp(Map<String, Object> map) {
        return hrProfitMapper.queryEmp(map);
    }

    //门店分红，查询该用户所属门店
    public List<Map<String, Object>> queryShop(User user) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (user.getStoreNumber().equals("ALL")) {
            list = hrProfitMapper.queryShopAll();
        } else if (user.getStoreNumber() != null) {
            List<String> shopList = new ArrayList<>();
            for (String item : user.getStoreNumber().split(",")) {
                shopList.add(item);
            }
            list = hrProfitMapper.queryShop(shopList);
        }
        return list;
    }

    public List<Map<String, Object>> queryDimRule(String groupId, String empNum) {
        return hrProfitMapper.queryDimRule(groupId, empNum);
    }

    //推送分红数据到sap（按区域或者门店）
    public List<Map<String, Object>> queryEmpProfit(String shopId, String areaMans) {
        //按照区域或者门店查询出分红数据
        if (shopId != null) {
            List<Map<String, Object>> list = hrProfitMapper.queryEmpProfit(shopId);
            return push(list);
        }
        //如果是推区域 分别查询出门店了推
        if (areaMans != null) {
            List<Map<String, Object>> profitList = null;
            List<Map<String, Object>> resultList = null;
            List<Map<String, Object>> shopIdList = hrProfitMapper.queryShopId(areaMans);
            for (Map<String, Object> item : shopIdList) {
                profitList = new ArrayList<>();
                profitList = hrProfitMapper.queryEmpProfit(item.get("ShopID").toString());
                resultList = push(profitList);
            }
            return resultList;
        }
        return null;
    }

    //撤销分红数据到sap
    public Map<String, Object> cancelProfit(String shopId, String empNo) {
        String result = null;
        JSONObject jsonObject = null;
        Map<String, Object> map = new HashMap<>();
        //查询某个人的分红信息
        List<Map<String, Object>> list = hrProfitMapper.queryProfit(empNo);
        //封装参数，推送到sap
        JSONObject node = new JSONObject();
        for (Map<String, Object> item : list) {
            node.put("BETRG1", item.get("endProfit"));
            map.put("status", item.get("push_status"));
            map.put("msg", item.get("push_message"));
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String time = sdf.format(new Date());
        JSONArray jsonAry1 = new JSONArray();
        node.put("PERNR", empNo);
        node.put("BEGDA", time);
        node.put("LGART", "3031");
        node.put("BETRG2", "");
        node.put("ZUORD", "ni");
        node.put("FLAG", "D");
        jsonAry1.add(node);
        JSONObject node1 = new JSONObject();
        node1.put("ITEM", jsonAry1);
        System.out.println("调用sap参数=======" + node1.toJSONString());
        result = util.getPostJsonResult(URL, node1.toJSONString());
        System.out.print("调用sap返回结果=========" + result);
        //根据返回信息，更新数据库状态
        if (!StringUtils.isEmpty(result)) {
            jsonObject = JSONObject.parseObject(result);
            JSONArray jsonArray = (JSONArray) jsonObject.get("ITEM");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject job = jsonArray.getJSONObject(i);
                if (job.get("MSGTYP") != null && job.get("MESSAGE") != null) {
                    if (job.get("MSGTYP").equals("E")) {
                        hrProfitMapper.updateEmpProfit(job.getString("PERNR"), "4", job.getString("MESSAGE"));
                        map.put("status", "4");
                        map.put("msg", job.get("MESSAGE"));
                    } else if (job.get("MSGTYP").equals("S")) {
                        hrProfitMapper.updateEmpProfit(job.getString("PERNR"), "3", job.getString("MESSAGE"));
                        map.put("status", "3");
                        map.put("msg", job.get("MESSAGE"));
                    }
                }
            }
        }
        return map;
    }

    //查询某个人用户的分红信息
    public List<Map<String, Object>> queryProfit(String empNo) {
        return hrProfitMapper.queryProfit(empNo);
    }

    //查询推送失败的分红信息
    public List<Map<String, Object>> queryFailPush() {
        return hrProfitMapper.queryFailPush();
    }

    //重新推送，已经失败的分红数据到sap
    public List<Map<String, Object>> pushFailedProfit() {
        List<Map<String, Object>> list = hrProfitMapper.queryFailPush();
        return push(list);
    }

    //推送分红到sap，公用方法
    public List<Map<String, Object>> push(List<Map<String, Object>> list) {
        List<Map<String, Object>> resList = new ArrayList<>();
        Map<String, Object> resMap = null;
        String result = null;
        JSONObject jsonObject = null;
        boolean flag = true;
        //推送到sap
        JSONObject node1 = new JSONObject();
        JSONArray jsonAry1 = new JSONArray();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String time = sdf.format(new Date());
        for (Map<String, Object> item : list) {
            JSONObject node = new JSONObject();
            node.put("PERNR", item.get("empNo"));
            node.put("BEGDA", time);
            node.put("LGART", "3031");
            node.put("BETRG1", item.get("endProfit"));
            node.put("BETRG2", "");
            node.put("ZUORD", "ni");
            node.put("FLAG", "I");
            jsonAry1.add(node);
        }
        node1.put("ITEM", jsonAry1);
        System.out.println("调用sap参数=======" + node1.toJSONString());
        result = util.getPostJsonResult(URL, node1.toJSONString());
        System.out.print("调用sap返回结果=======" + result);
        //并且更新数据库状态和信息
        if (!StringUtils.isEmpty(result)) {
            jsonObject = JSONObject.parseObject(result);
            JSONArray jsonArray = (JSONArray) jsonObject.get("ITEM");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject job = jsonArray.getJSONObject(i);
                if (job.get("MSGTYP") != null && job.get("MESSAGE") != null) {
                    if (job.get("MSGTYP").equals("E")) {
                        flag = false;
                        hrProfitMapper.updateEmpProfit(job.getString("PERNR"), "2", job.getString("MESSAGE"));
                        resMap = new HashMap<>();
                        resMap.put("empNo", job.getString("PERNR"));
                        resMap.put("status", "2");
                        resMap.put("msg", job.get("MESSAGE"));
                        resList.add(resMap);
                    } else if (job.get("MSGTYP").equals("S")) {
                        hrProfitMapper.updateEmpProfit(job.getString("PERNR"), "1", job.getString("MESSAGE"));
                    }
                }
            }
        }
        return resList;
    }
}
