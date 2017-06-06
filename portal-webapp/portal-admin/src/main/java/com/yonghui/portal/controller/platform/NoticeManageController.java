package com.yonghui.portal.controller.platform;

import java.util.List;
import java.util.Map;

import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.service.global.NoticeManageService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yonghui.portal.model.global.Notice;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.Query;
import com.yonghui.portal.util.R;

/**
 *
 * @author yangyang
 * @email 80715104@yonghui.cn
 * @date 2017-06-02 20:21:18
 */
@RestController
@RequestMapping("notice")
public class NoticeManageController extends AbstractController {
    @Autowired
    private NoticeManageService noticeManageService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("notice:list")
    public R list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);

        List<Notice> noticeList = noticeManageService.queryList(query);
        int total = noticeManageService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(noticeList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("notice:info")
    public R info(@PathVariable("id") Integer id){
        Notice notice = noticeManageService.queryObject(id);
        return R.success().put("notice", notice);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("notice:save")
    public R save(@RequestBody Notice notice){
		noticeManageService.save(notice);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("notice:update")
    public R update(@RequestBody Notice notice){
		noticeManageService.update(notice);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/delete")
    @RequiresPermissions("notice:delete")
    public R delete(@RequestBody Integer[] ids){
		noticeManageService.deleteBatch(ids);
        return R.success();
    }

}
