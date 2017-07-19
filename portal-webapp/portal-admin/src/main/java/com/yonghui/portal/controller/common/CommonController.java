package com.yonghui.portal.controller.common;

import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.service.common.CommonService;
import com.yonghui.portal.util.R;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Author : 杨杨
 * Date : 2017/07/18
 * Description :
 */
@RestController
@RequestMapping(value = "common")
public class CommonController  extends AbstractController {

    Logger log= Logger.getLogger(this.getClass());

    @Autowired
    private CommonService commonService;


    /**
     * 获取老大区、新大区、门店
     * reportLabel : 1.老大区   pid: null 或任意字符均取得全部老大区
     * reportLabel : 2.新大区   pid: null取得全部老大区  '福建','华东'根据老大区获取新大区
     * reportLabel : 3.门店     pid: null取得全部门店  '三明区','上海区'根据新大区获取门店
     * reportLabel : 4.门店     pid: null取得全部门店  '三明区','上海区'根据老大区获取门店
     */
    @RequestMapping(value = "getDataList")
    public R getDataList(Integer reportLabel,String pid) {
        Map<String,Object> map=new HashedMap();
        map.put("reportLabel",reportLabel);
        map.put("pid",pid);
        try {
            List<Map<String,Object>> list=commonService.getDataList(map);
            Map<String,Object> result=new HashedMap();
            result.put("list",list);
            return R.success().setData(result);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error().setMsg(e.toString());
        }
    }

    /**
     * reportLabel : 5.根据新大区定位老大区     pid: 非空 上海区 根据新大区定位老大区
     * */

    /**
     * reportLabel : 6.老大区、新大区、门店级联     pid: null 或任意字符均取得全部级联
     * reportLabel : 7.新大区、门店级联     pid: null 或任意字符均取得全部级联
     * */
}
