package com.yonghui.portal.controller.businessman;

import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.businessman.BusinessmanActicle;
import com.yonghui.portal.service.businessman.BusinessmanActicleService;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.Query;
import com.yonghui.portal.util.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        businessmanActicle.setContent(businessmanActicle.getContent().replace("\\\"", "'").replace("	", "").replace("\\r\\n", "<br/>"));
        businessmanActicleService.save(businessmanActicle);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("businessmanacticle:update")
    public R update(@RequestBody BusinessmanActicle businessmanActicle) {
        businessmanActicle.setContent(businessmanActicle.getContent().replace("\\\"", "'").replace("	", "").replace("\\r\\n", "<br/>"));
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

}
