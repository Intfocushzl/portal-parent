package com.yonghui.portal.service.sys;

import com.yonghui.portal.model.sys.PosterImportArea;
import com.yonghui.portal.model.sys.PosterImportGoods;

import java.util.List;
import java.util.Map;

/**
 * 海报导入
 * Created by 刘伟 on 2017/06/27.
 */
public interface PosterImportService {

    //查询区域和海报id对应关系
    List<Map<String, Object>> areaList(String area);

    List<Map<String, Object>> areaTmpJoinList(String jobNumber);

    List<Map<String, Object>> areaList();

    List<Map<String, Object>> tmpGoodsList(String jobNumber);

    List<Map<String, Object>> goodsList(String posterId ,  String area);

    //查询国家
    int queryCountry(String country);

    int queryVender(String venderid);

    //区域和城市对应关系
    List<Map<String, Object>> areamansList();

    int insertPosterImportAreaTmp(List<PosterImportArea> excellist , String jobNmuber , List<Map<String, Object>> list) throws Exception;

    void deleteArea(List<Integer> list , String jobNumber);


    //根据jobNumber查询商品海报重复导入吻合的数据(posterId+area+goodsId)
    List<Map<String, Object>> goodsTmpJoinList(String jobNumber);

    //将商品海报保存到临时表
    int insertPosterImportGoodsTmp(List<PosterImportGoods> list , String jobNumber);

    //根据id删除正式表已存在的海报商品信息
    List<Map<String, Object>> deleteGoods(List<Integer> list , String jobNumber);

    //将数据从临时表导入正式表
    int insertGoods();

    //根据jobnumber，删除临时表里面的数据
    int deleteGoodsTmp(String jobNumber);
}
