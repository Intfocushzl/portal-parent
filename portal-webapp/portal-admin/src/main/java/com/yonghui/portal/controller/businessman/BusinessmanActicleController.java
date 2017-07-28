package com.yonghui.portal.controller.businessman;

import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.businessman.BusinessmanActicle;
import com.yonghui.portal.model.businessman.BusinessmanActicleRecommend;
import com.yonghui.portal.model.businessman.BusinessmanActicleSlider;
import com.yonghui.portal.model.businessman.BusinessmanTagInfo;
import com.yonghui.portal.service.businessman.BusinessmanActicleRecommendService;
import com.yonghui.portal.service.businessman.BusinessmanActicleService;
import com.yonghui.portal.service.businessman.BusinessmanActicleSliderService;
import com.yonghui.portal.service.businessman.BusinessmanTagInfoService;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.Query;
import com.yonghui.portal.util.R;
import com.yonghui.portal.utils.ShiroUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 生意人数据学院文章信息表
 *
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-07 16:33:29
 */
@RestController
@RequestMapping("businessmanacticle")
public class BusinessmanActicleController extends AbstractController {
    @Autowired
    private BusinessmanActicleService businessmanActicleService;
    @Autowired
    private BusinessmanTagInfoService businessmanTagInfoService;
    @Autowired
    private BusinessmanActicleRecommendService businessmanActicleRecommendService;
    @Autowired
    private BusinessmanActicleSliderService businessmanActicleSliderService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("businessmanacticle:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<BusinessmanActicle> businessmanActicleList = businessmanActicleService.queryList(query);

        int total = businessmanActicleService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(businessmanActicleList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("businessmanacticle:info")
    public R info(@PathVariable("id") Long id) {
        BusinessmanActicle businessmanActicle = businessmanActicleService.queryObject(id);
        return R.success().put("businessmanActicle", businessmanActicle);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("businessmanacticle:save")
    public R save(@RequestBody BusinessmanActicle businessmanActicle) {
        businessmanActicle.setCreater(ShiroUtils.getUserId());
        businessmanActicle.setContentManuscript(businessmanActicle.getContentManuscript().replace("\\\"", "'").replace("	", "").replace("\\r\\n", "<br/>"));
        businessmanActicleService.save(businessmanActicle);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("businessmanacticle:update")
    public R update(@RequestBody BusinessmanActicle businessmanActicle) {
        businessmanActicle.setModifier(ShiroUtils.getUserId());
        businessmanActicle.setContentManuscript(businessmanActicle.getContentManuscript().replace("\\\"", "'").replace("	", "").replace("\\r\\n", "<br/>"));
        businessmanActicleService.update(businessmanActicle);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/delete")
    @RequiresPermissions("businessmanacticle:delete")
    public R delete(@RequestBody Long[] ids) {
        businessmanActicleService.deleteBatch(ids);
        return R.success();
    }

    @RequestMapping("/tagInfoList")
    @ResponseBody
    public R tagInfoList(@RequestParam Integer type) {
        List<BusinessmanTagInfo> list = businessmanTagInfoService.queryByTagType(type);
        return R.success().setData(list);
    }

    @RequestMapping(value = "beTopAndSlider")
    public R topAndSlider(String topStr, String sliderStr) {

        String[] topStrs = topStr.split("\\|");
        String[] sliderStrs = sliderStr.split("\\|");
        List<BusinessmanActicleRecommend> recommendList = new ArrayList<>();
        List<BusinessmanActicleSlider> sliderList = new ArrayList<>();

        for (int i = 0; i < topStrs.length; i++) {
            String str = topStrs[i];
            String[] strs = str.split("-");
            BusinessmanActicleRecommend recommend = new BusinessmanActicleRecommend();
            Integer acticleId = Integer.parseInt(strs[0].trim());
            recommend.setActicleId(acticleId);
            recommend.setOrderNum(i + 1);
            Long acticleType = Long.parseLong(strs[2].trim());
            recommend.setActicleType(acticleType);
            recommendList.add(recommend);
        }
        businessmanActicleRecommendService.deleteAll();
        Map<String, Object> map = new HashedMap();
        map.put("creater",ShiroUtils.getUserId());
        map.put("recommendList", recommendList);
        businessmanActicleRecommendService.saveRecommend(map);
        for (int i = 0; i < sliderStrs.length; i++) {
            String str = sliderStrs[i];
            String[] strs = str.split("-");
            BusinessmanActicleSlider slider = new BusinessmanActicleSlider();
            Integer acticleId = Integer.parseInt(strs[0].trim());
            slider.setActicleId(acticleId);
            slider.setOrderNum(i + 1);
            Long acticleType = Long.parseLong(strs[2].trim());
            slider.setActicleType(acticleType);
            sliderList.add(slider);
        }
        businessmanActicleSliderService.deleteAll();
        map.put("sliderList", sliderList);
        map.put("creater",ShiroUtils.getUserId());
        businessmanActicleSliderService.saveSlider(map);
        return R.success();
    }
}
