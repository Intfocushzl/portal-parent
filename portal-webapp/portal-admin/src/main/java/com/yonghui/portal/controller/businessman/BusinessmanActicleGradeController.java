package com.yonghui.portal.controller.businessman;

import com.yonghui.portal.annotation.SysLog;
import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.businessman.BusinessmanActicleGrade;
import com.yonghui.portal.service.businessman.BusinessmanActicleGradeService;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.Query;
import com.yonghui.portal.util.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 文章评分
 *
 * @author yangyang
 * @email 80715104@yonghui.cn
 * @date 2017-07-06 09:49:53
 */
@RestController
@RequestMapping("businessmanacticle/grade")
public class BusinessmanActicleGradeController extends AbstractController {
    @Autowired
    private BusinessmanActicleGradeService businessmanActicleGradeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("businessmanacticle:grade:list")
    public R list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);

        List<BusinessmanActicleGrade> businessmanActicleGradeList = businessmanActicleGradeService.queryList(query);
        int total = businessmanActicleGradeService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(businessmanActicleGradeList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("businessmanacticle:grade:info")
    public R info(@PathVariable("id") Long id){
        BusinessmanActicleGrade businessmanActicleGrade = businessmanActicleGradeService.queryObject(id);
        return R.success().put("businessmanActicleGrade", businessmanActicleGrade);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("businessmanacticle:grade:save")
    public R save(@RequestBody BusinessmanActicleGrade businessmanActicleGrade){
		businessmanActicleGradeService.save(businessmanActicleGrade);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("businessmanacticle:grade:update")
    public R update(@RequestBody BusinessmanActicleGrade businessmanActicleGrade){
		businessmanActicleGradeService.update(businessmanActicleGrade);
        return R.success();
    }

    /**
     * 删除
     */
    @SysLog("文章评分删除")
    @RequestMapping("/delete")
    @RequiresPermissions("businessmanacticle:grade:delete")
    public R delete(@RequestBody Long[] ids){
		businessmanActicleGradeService.deleteBatch(ids);
        return R.success();
    }

    @RequestMapping("/getListByActicleId")
    public R getListByActicleId(@RequestParam Integer id,@RequestParam Map<String, Object> params) {
        params.put("id",id);
        //查询列表数据
        Query query = new Query(params);

        List<BusinessmanActicleGrade> businessmanActicleGradeList = businessmanActicleGradeService.queryList(query);
        int total = businessmanActicleGradeService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(businessmanActicleGradeList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);
    }
}
