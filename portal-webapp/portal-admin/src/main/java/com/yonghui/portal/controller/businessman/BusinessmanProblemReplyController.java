package com.yonghui.portal.controller.businessman;

import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.businessman.BusinessmanProblemReply;
import com.yonghui.portal.service.businessman.BusinessmanProblemReplyService;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.Query;
import com.yonghui.portal.util.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 用户问题回复信息表
 *
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-07 16:33:28
 */
@RestController
@RequestMapping("businessmanproblemreply")
public class BusinessmanProblemReplyController extends AbstractController {
    @Autowired
    private BusinessmanProblemReplyService businessmanProblemReplyService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("businessmanproblemreply:list")
    public R list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);

        List<BusinessmanProblemReply> businessmanProblemReplyList = businessmanProblemReplyService.queryList(query);
        int total = businessmanProblemReplyService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(businessmanProblemReplyList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("businessmanproblemreply:info")
    public R info(@PathVariable("id") Long id){
        BusinessmanProblemReply businessmanProblemReply = businessmanProblemReplyService.queryObject(id);
        return R.success().put("businessmanProblemReply", businessmanProblemReply);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("businessmanproblemreply:save")
    public R save(@RequestBody BusinessmanProblemReply businessmanProblemReply){
		businessmanProblemReplyService.save(businessmanProblemReply);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("businessmanproblemreply:update")
    public R update(@RequestBody BusinessmanProblemReply businessmanProblemReply){
		businessmanProblemReplyService.update(businessmanProblemReply);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/delete")
    @RequiresPermissions("businessmanproblemreply:delete")
    public R delete(@RequestBody Long[] ids){
		businessmanProblemReplyService.deleteBatch(ids);
        return R.success();
    }

}
