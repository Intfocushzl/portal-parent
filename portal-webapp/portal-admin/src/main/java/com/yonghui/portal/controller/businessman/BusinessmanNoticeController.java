package com.yonghui.portal.controller.businessman;

import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.businessman.BusinessmanNotice;
import com.yonghui.portal.service.businessman.BusinessmanNoticeService;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.Query;
import com.yonghui.portal.util.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 生意人公告信息表
 *
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-07 16:33:29
 */
@RestController
@RequestMapping("businessmannotice")
public class BusinessmanNoticeController extends AbstractController {
    @Autowired
    private BusinessmanNoticeService businessmanNoticeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("businessmannotice:list")
    public R list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);

        List<BusinessmanNotice> businessmanNoticeList = businessmanNoticeService.queryList(query);
        int total = businessmanNoticeService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(businessmanNoticeList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("businessmannotice:info")
    public R info(@PathVariable("id") Long id){
        BusinessmanNotice businessmanNotice = businessmanNoticeService.queryObject(id);
        return R.success().put("businessmanNotice", businessmanNotice);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("businessmannotice:save")
    public R save(@RequestBody BusinessmanNotice businessmanNotice){
		businessmanNoticeService.save(businessmanNotice);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("businessmannotice:update")
    public R update(@RequestBody BusinessmanNotice businessmanNotice){
		businessmanNoticeService.update(businessmanNotice);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/delete")
    @RequiresPermissions("businessmannotice:delete")
    public R delete(@RequestBody Long[] ids){
		businessmanNoticeService.deleteBatch(ids);
        return R.success();
    }

}
