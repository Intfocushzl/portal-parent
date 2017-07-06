package com.yonghui.portal.service.impl.sys;

import com.yonghui.portal.mapper.sys.PosterImportMapper;
import com.yonghui.portal.model.sys.PosterImportArea;
import com.yonghui.portal.model.sys.PosterImportGoods;
import com.yonghui.portal.service.sys.PosterImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuwei on 2017/6/27.
 * 海报上传serviceImpl
 */
public class PosterImportServiceImpl implements PosterImportService {

    @Autowired
    private PosterImportMapper posterImportMapper;

    @Override
    public List<Map<String, Object>> areaList(String area) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if (area == null) {
            list = posterImportMapper.areaList();
        } else {
            list = posterImportMapper.listByArea(area);
        }
        return list;
    }

    public List<Map<String, Object>> areaTmpJoinList(String jobNumber) {
        return posterImportMapper.areaTmpJoinList(jobNumber);
    }

    public List<Map<String, Object>> areaList() {
        return posterImportMapper.areaList();

    }

    public List<Map<String, Object>> tmpGoodsList(String jobNumber) {
        return posterImportMapper.tmpGoodsList(jobNumber);
    }

    public List<Map<String, Object>> goodsList(String posterId, String area) {
        return posterImportMapper.goodsList(posterId, area);
    }

    public int queryCountry(String country) {
        return posterImportMapper.queryCountry(country);

    }

    public int queryVender(String venderid) {
        return posterImportMapper.queryVender(venderid);
    }

    public List<Map<String, Object>> areamansList() {
        return posterImportMapper.areamansList();
    }

    public int insertPosterImportArea(List<PosterImportArea> list) {
        return posterImportMapper.insertPosterImportArea(list);
    }

    public int insertPosterImportAreaTmp(List<PosterImportArea> excellist, String jobNumber, List<Map<String, Object>> list) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        //请空该用户之前导入临时表的数据
        posterImportMapper.deleteAreaTmp(jobNumber);
        //查询区域和城市对应关系
        for (Map<String, Object> item : list) {
            map.put(item.get("areamans") + "-" + item.get("city"), item);
        }
        List<Map<String, Object>> tmpList = posterImportMapper.tmpAreaList(jobNumber);
        //校验表格数据
        Integer posterId = excellist.get(0).getPosterId();
        for (PosterImportArea item : excellist) {
            if (!item.getPosterId().equals(posterId)) {
                throw new Exception("海报档期ID不唯一");
            }
            if (map.get(item.getArea() + "-" + item.getCity()) == null) {
                throw new Exception("填写的区域和城市不对应");
            }
            item.setJobNumber(jobNumber);
        }
        return posterImportMapper.insertPosterImportAreaTmp(excellist);
    }


    @Transactional
    public void deleteArea(List<Integer> list, String jobNumber) {
        //删除正式匹配上的数据
        if (list.size() > 0 && list != null) {
            posterImportMapper.deleteArea(list);
        }
        //将临时表的数据导入到正式
        posterImportMapper.insertArea();
        //清空临时表
        posterImportMapper.deleteAreaTmp(jobNumber);
    }

    public int insertArea() {
        return posterImportMapper.insertArea();
    }

    public int deleteAreaTmp(String jobNumber) {
        return posterImportMapper.deleteAreaTmp(jobNumber);
    }


    //根据jobNumber查询商品海报重复导入吻合的数据
    public List<Map<String, Object>> goodsTmpJoinList(String jobNumber) {
        return posterImportMapper.goodsTmpJoinList(jobNumber);
    }

    //将商品海报保存到临时表
    public int insertPosterImportGoodsTmp(List<PosterImportGoods> list , String jobNumber) {
        //先删除临时表之前的数据
        posterImportMapper.deleteGoodsTmp(jobNumber);
        return posterImportMapper.insertPosterImportGoodsTmp(list);
    }

    //根据id删除正式表已存在的海报信息
    @Transactional
    public List<Map<String, Object>> deleteGoods(List<Integer> list, String jobNumber) {
        //删除正式匹配上的数据
        if (list.size() > 0 && list != null) {
            posterImportMapper.deleteGoods(list);
        }
        //将临时表的数据导入到正式
        posterImportMapper.insertGoods();

        List<Map<String, Object>> goodsList = posterImportMapper.tmpGoodsList(jobNumber);
        //清空临时表
        posterImportMapper.deleteGoodsTmp(jobNumber);

        return goodsList;
    }

    //将数据从临时表导入正式表
    public int insertGoods() {
        return posterImportMapper.insertGoods();
    }

    //根据jobnumber，删除临时表里面的数据
    public int deleteGoodsTmp(String jobNumber) {
        return posterImportMapper.deleteGoodsTmp(jobNumber);
    }
}
