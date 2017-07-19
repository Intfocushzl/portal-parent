package com.yonghui.portal.service.impl.manage;

import com.yonghui.portal.mapper.manage.HrProfitMapper;
import com.yonghui.portal.model.global.User;
import com.yonghui.portal.model.manage.HrProfit;
import com.yonghui.portal.service.manage.HrProfitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by liuwei on 2017/7/17.
 * 人事分红serviceImpl
 */
public class HrProfitServiceImpl implements HrProfitService {

    @Autowired
    private HrProfitMapper hrProfitMapper;


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
        if (record.get(0).get("editRecord") != null) {
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
        if (record.get(0).get("editRecord") != null) {
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
}
