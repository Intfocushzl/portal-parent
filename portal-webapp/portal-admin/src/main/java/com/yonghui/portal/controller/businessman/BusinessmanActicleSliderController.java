package com.yonghui.portal.controller.businessman;

import java.util.List;
import java.util.Map;

import com.yonghui.portal.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yonghui.portal.model.businessman.BusinessmanActicleSlider;
import com.yonghui.portal.service.businessman.BusinessmanActicleSliderService;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.Query;
import com.yonghui.portal.util.R;

/**
 * 文章轮播
 *
 * @author yangyang
 * @email 80715104@yonghui.cn
 * @date 2017-06-28 10:06:31
 */
@RestController
@RequestMapping("businessmanacticleslider")
public class BusinessmanActicleSliderController extends AbstractController {
    @Autowired
    private BusinessmanActicleSliderService businessmanActicleSliderService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("businessmanacticleslider:list")
    public R list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);

        List<BusinessmanActicleSlider> businessmanActicleSliderList = businessmanActicleSliderService.queryList(query);
        int total = businessmanActicleSliderService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(businessmanActicleSliderList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);
    }

    /**
     * 置顶列表
     */
    @RequestMapping("/perms")
    public R perms(){
        //查询列表数据
        List<BusinessmanActicleSlider> sliderList = businessmanActicleSliderService.queryAllList();

        return R.success().put("sliderList", sliderList);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("businessmanacticleslider:info")
    public R info(@PathVariable("id") Long id){
        BusinessmanActicleSlider businessmanActicleSlider = businessmanActicleSliderService.queryObject(id);
        return R.success().put("businessmanActicleSlider", businessmanActicleSlider);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("businessmanacticleslider:save")
    public R save(@RequestBody BusinessmanActicleSlider businessmanActicleSlider){
		businessmanActicleSliderService.save(businessmanActicleSlider);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("businessmanacticleslider:update")
    public R update(@RequestBody BusinessmanActicleSlider businessmanActicleSlider){
		businessmanActicleSliderService.update(businessmanActicleSlider);
        return R.success();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("businessmanacticleslider:delete")
    public R delete(@RequestBody Long[] ids){
		businessmanActicleSliderService.deleteBatch(ids);
        return R.success();
    }

}
