package com.yonghui.portal.controller.businessman;

import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.businessman.BusinessmanProblem;
import com.yonghui.portal.service.businessman.BusinessmanProblemService;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.Query;
import com.yonghui.portal.util.R;
import com.yonghui.portal.utils.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 用户问题反馈信息表
 *
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-07 16:33:28
 */
@RestController
@RequestMapping("businessmanproblem")
public class BusinessmanProblemController extends AbstractController {
    @Autowired
    private BusinessmanProblemService businessmanProblemService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("businessmanproblem:list")
    public R list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);

        List<BusinessmanProblem> businessmanProblemList = businessmanProblemService.queryList(query);
        int total = businessmanProblemService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(businessmanProblemList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("businessmanproblem:info")
    public R info(@PathVariable("id") Long id){
        BusinessmanProblem businessmanProblem = businessmanProblemService.queryObject(id);
        return R.success().put("businessmanProblem", businessmanProblem);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("businessmanproblem:save")
    public R save(@RequestBody BusinessmanProblem businessmanProblem){
        businessmanProblem.setCreater(ShiroUtils.getUserId());
		businessmanProblemService.save(businessmanProblem);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("businessmanproblem:update")
    public R update(@RequestBody BusinessmanProblem businessmanProblem){
        businessmanProblem.setModifier(ShiroUtils.getUserId());
		businessmanProblemService.update(businessmanProblem);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/delete")
    @RequiresPermissions("businessmanproblem:delete")
    public R delete(@RequestBody Long[] ids){
		businessmanProblemService.deleteBatch(ids);
        return R.success();
    }

}
