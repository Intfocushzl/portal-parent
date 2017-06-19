package com.yonghui.portal.controller.businessman;

import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.businessman.BusinessmanComment;
import com.yonghui.portal.service.businessman.BusinessmanCommentService;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.Query;
import com.yonghui.portal.util.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 用户评论信息表
 *
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-07 16:33:29
 */
@RestController
@RequestMapping("businessmancomment")
public class BusinessmanCommentController extends AbstractController {
    @Autowired
    private BusinessmanCommentService businessmanCommentService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("businessmancomment:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<BusinessmanComment> businessmanCommentList = businessmanCommentService.queryList(query);
        int total = businessmanCommentService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(businessmanCommentList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("businessmancomment:info")
    public R info(@PathVariable("id") Long id) {
        BusinessmanComment businessmanComment = businessmanCommentService.queryObject(id);
        return R.success().put("businessmanComment", businessmanComment);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("businessmancomment:save")
    public R save(@RequestBody BusinessmanComment businessmanComment) {
        businessmanCommentService.save(businessmanComment);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("businessmancomment:update")
    public R update(@RequestBody BusinessmanComment businessmanComment) {
        businessmanCommentService.update(businessmanComment);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/delete")
    @RequiresPermissions("businessmancomment:delete")
    public R delete(@RequestBody Long[] ids) {
        businessmanCommentService.deleteBatch(ids);
        return R.success();
    }

    @RequestMapping("/getListByActicleId")
    public R getListByActicleId(@RequestParam Integer id) {
        List<BusinessmanComment> list = businessmanCommentService.getListByActicleId(id);
        return R.success().setData(list);
    }

    @RequestMapping(value = "updateIsopen")
    public R updateIsopen(HttpServletRequest request) {
        Integer id = Integer.parseInt(request.getParameter("id"));
        Integer result = Integer.parseInt(request.getParameter("result"));
        businessmanCommentService.updateIsopen(id, result);
        return R.success();
    }
}
