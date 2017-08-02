package com.yonghui.portal.controller.businessman;

import com.yonghui.portal.annotation.SysLog;
import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.businessman.BusinessmanSubjectInfo;
import com.yonghui.portal.service.businessman.BusinessmanSubjectInfoService;
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
 * 专题信息表
 *
 * @author yangyang
 * @email 80715104@yonghui.cn
 * @date 2017-06-28 10:06:30
 */
@RestController
@RequestMapping("businessmansubjectinfo")
public class BusinessmanSubjectInfoController extends AbstractController {
    @Autowired
    private BusinessmanSubjectInfoService businessmanSubjectInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("businessmansubjectinfo:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<BusinessmanSubjectInfo> businessmanSubjectInfoList = businessmanSubjectInfoService.queryList(query);
        int total = businessmanSubjectInfoService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(businessmanSubjectInfoList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);
    }

    /**
     * 选择菜单(添加、修改菜单)
     */
    @RequestMapping("/select")
//    @RequiresPermissions("menu:select")
    public R select(@RequestParam Map<String, Object> params) {
        //查询列表数据
        List<BusinessmanSubjectInfo> menuList = businessmanSubjectInfoService.queryList(params);

        //添加顶级菜单
        BusinessmanSubjectInfo root = new BusinessmanSubjectInfo();
        root.setId(0L);
        root.setName("一级菜单");
        root.setPid(-1);
        root.setOpen(true);
        menuList.add(root);

        return R.success().put("menuList", menuList);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("businessmansubjectinfo:info")
    public R info(@PathVariable("id") Long id) {
        BusinessmanSubjectInfo businessmanSubjectInfo = businessmanSubjectInfoService.queryObject(id);
        return R.success().put("businessmanSubjectInfo", businessmanSubjectInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("businessmansubjectinfo:save")
    public R save(@RequestBody BusinessmanSubjectInfo businessmanSubjectInfo) {
        Long creater = ShiroUtils.getUserId();
        businessmanSubjectInfo.setCreater(creater);
        businessmanSubjectInfoService.save(businessmanSubjectInfo);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("businessmansubjectinfo:update")
    public R update(@RequestBody BusinessmanSubjectInfo businessmanSubjectInfo) {
        Long creater = ShiroUtils.getUserId();
        businessmanSubjectInfo.setModifier(creater);
        businessmanSubjectInfoService.update(businessmanSubjectInfo);
        return R.success();
    }

    /**
     * 删除
     */
    @SysLog("删除专题")
    @RequestMapping("/delete")
    @RequiresPermissions("businessmansubjectinfo:delete")
    public R delete(@RequestBody Long[] ids) {
        businessmanSubjectInfoService.deleteBatch(ids);
        return R.success();
    }

    @RequestMapping("/getActicleSubjectSelected")
    public R getActicleSubjectSelected() {
        List<BusinessmanSubjectInfo> businessmanSubjectInfoList = businessmanSubjectInfoService.getActicleSubjectSelected();
        return R.success().put("subjectList",businessmanSubjectInfoList);
    }
}
