package com.yonghui.portal.mapper.manage;

import com.yonghui.portal.model.manage.HrProfit;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 人事分红
 * Created by 刘伟 on 2017/07/17.
 */
public interface HrProfitMapper {

    List<Map<String, Object>> tmpProfitList(String jobNumber);

    List<Map<String, Object>> profitList(@Param("sMonth") String sMonth, @Param("shopid") String shopid, @Param("oldshopid") String oldshopid);

    //根据jobNumber查询商品海报重复导入吻合的数据(posterId+area+goodsId)
    List<Map<String, Object>> profitTmpJoinList(String jobNumber);

    //将商品海报保存到临时表
    int insertProfitTmp(List<HrProfit> list);

    //删除正式表已存在的
    int deleteProfit(Map<String, Object> map);

    //将数据从临时表导入正式表
    int insertProfit();

    //根据jobnumber，删除临时表里面的数据
    int deleteProfitTmp(String jobNumber);

    //分红标准维护
    int editRule(Map<String, Object> map);

    //分红人员维护
    int editEmp(Map<String, Object> map);

    //模糊查询shopid
    List<Map<String, Object>> queryShopID(Map<String, Object> map);

    //模糊查询groupid
    List<Map<String, Object>> queryGroupID(Map<String, Object> map);

    //通过id查询分红规则信息
    List<Map<String, Object>> queryRuleById(@Param("id") String id);

    //通过id查询分红人员信息
    List<Map<String, Object>> queryEmpById(@Param("id") String id);

    //查询分红标准
    List<Map<String, Object>> queryRule(Map<String, Object> map);

    //查询分红人员
    List<Map<String, Object>> queryEmp(Map<String, Object> map);

    //门店分红，查询该用户所属门店
    List<Map<String, Object>> queryShop(List<String> list);

    //查询所有门店
    List<Map<String, Object>> queryShopAll();

    //查询联动规则
    List<Map<String, Object>> queryDimRule(@Param("groupId") String groupId, @Param("empNum") String empNum);

    //查询某个门店所有员工分红信息
    List<Map<String, Object>> queryEmpProfit(@Param("shopId") String shopId, @Param("areaMans") String areaMans);

    //查询某个区域下所有门店的所有员工分红信息
    List<Map<String, Object>> queryEmpProfit1(@Param("shopId") String shopId, @Param("areaMans") String areaMans);

    //更新数据库分红人员的推送状态和信息
    int updateEmpProfit(@Param("jobNumber") String jobNumber , @Param("status") String status , @Param("msg") String msg);

    //查询某个人用户的分红信息
    List<Map<String, Object>> queryProfit(@Param("shopId") String shopId , @Param("empNo") String empNo);
}
