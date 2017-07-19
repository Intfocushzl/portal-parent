package com.yonghui.portal.service.manage;

import com.yonghui.portal.model.global.User;
import com.yonghui.portal.model.manage.HrProfit;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 人事分红
 * Created by 刘伟 on 2017/07/17.
 */
public interface HrProfitService {

    List<Map<String, Object>> tmpProfitList(String jobNumber);

    List<Map<String, Object>> profitList(@Param("sMonth") String sMonth, @Param("shopid") String shopid ,@Param("oldshopid") String oldshopid );

    //根据jobNumber查询商品海报重复导入吻合的数据(posterId+area+goodsId)
    List<Map<String, Object>> profitTmpJoinList(String jobNumber);

    //将商品海报保存到临时表
    int insertProfitTmp(List<HrProfit> list, String jobNumber);

    //根据id删除正式表已存在的
    List<Map<String, Object>> deleteProfit(List<Map<String, Object>> list, String jobNumber);

    //将数据从临时表导入正式表
    int insertProfit();

    //根据jobnumber，删除临时表里面的数据
    int deleteProfitTmp(String jobNumber);

    //分红标准维护
    int editRule(Integer id, Integer empNum, BigDecimal standardRule, BigDecimal friRule, BigDecimal secRule, Integer runtype);

    //分红人员维护
    int editEmp(Integer id , Float guarantees, Integer flagBonus, Date turnDate , String shopName , String groupName) throws Exception;

    //查询分红标准
    List<Map<String, Object>> queryRule(Map<String, Object> map);

    //查询分红人员
    List<Map<String, Object>> queryEmp(Map<String, Object> map);

    //查询该用户所在门店
    List<Map<String, Object>> queryShop(User user);

    //联动规则查询
    List<Map<String, Object>> queryDimRule(String groupId , String empNum);
}
