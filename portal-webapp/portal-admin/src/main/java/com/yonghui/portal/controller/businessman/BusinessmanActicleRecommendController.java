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

import com.yonghui.portal.model.businessman.BusinessmanActicleRecommend;
import com.yonghui.portal.service.businessman.BusinessmanActicleRecommendService;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.Query;
import com.yonghui.portal.util.R;

/**
 * 文章推荐置顶
 *
 * @author yangyang
 * @email 80715104@yonghui.cn
 * @date 2017-06-28 10:06:30
 */
@RestController
@RequestMapping("businessmanacticlerecommend")
public class BusinessmanActicleRecommendController extends AbstractController {
    @Autowired
    private BusinessmanActicleRecommendService businessmanActicleRecommendService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("businessmanacticlerecommend:list")
    public R list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);

        List<BusinessmanActicleRecommend> businessmanActicleRecommendList = businessmanActicleRecommendService.queryList(query);
        int total = businessmanActicleRecommendService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(businessmanActicleRecommendList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);
    }


    /**
     * 置顶列表
     */
    @RequestMapping("/perms")
    public R perms(){
        //查询列表数据
        List<BusinessmanActicleRecommend> topList = businessmanActicleRecommendService.queryAllList();

        return R.success().put("topList", topList);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("businessmanacticlerecommend:info")
    public R info(@PathVariable("id") Long id){
        BusinessmanActicleRecommend businessmanActicleRecommend = businessmanActicleRecommendService.queryObject(id);
        return R.success().put("businessmanActicleRecommend", businessmanActicleRecommend);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("businessmanacticlerecommend:save")
    public R save(@RequestBody BusinessmanActicleRecommend businessmanActicleRecommend){
		businessmanActicleRecommendService.save(businessmanActicleRecommend);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("businessmanacticlerecommend:update")
    public R update(@RequestBody BusinessmanActicleRecommend businessmanActicleRecommend){
		businessmanActicleRecommendService.update(businessmanActicleRecommend);
        return R.success();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("businessmanacticlerecommend:delete")
    public R delete(@RequestBody Long[] ids){
		businessmanActicleRecommendService.deleteBatch(ids);
        return R.success();
    }

}
