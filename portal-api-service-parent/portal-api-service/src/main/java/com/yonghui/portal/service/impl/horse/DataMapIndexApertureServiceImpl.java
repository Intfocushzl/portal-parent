package com.yonghui.portal.service.impl.horse;

import com.yonghui.portal.mapper.horse.DataMapIndexApertureMapper;
import com.yonghui.portal.model.horse.DataMapIndexAperture;
import com.yonghui.portal.service.horse.DataMapIndexApertureService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liuwei on 2017/5/7.
 */
public class DataMapIndexApertureServiceImpl implements DataMapIndexApertureService {

    @Resource
    private DataMapIndexApertureMapper dataMapIndexApertureMapper;


   public List<String> getindexlist(String theme){
       return dataMapIndexApertureMapper.getindexlist(theme);
    };

    public List<String> getthemelist(){
        return  dataMapIndexApertureMapper.getthemelist();
    };

    public List<DataMapIndexAperture> getDataMapIndexAperture(String theme, String index){
        return  dataMapIndexApertureMapper.getDataMapIndexAperture(theme,index);
    };

}
