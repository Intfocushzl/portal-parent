package com.yonghui.portal.controller.cindex;

import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.cindex.CIndexAperture;
import com.yonghui.portal.service.cindex.CIndexApertureService;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.Query;
import com.yonghui.portal.util.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-02 17:12:47
 */
@RestController
@RequestMapping("cindexaperture")
public class CIndexApertureController extends AbstractController {
    @Autowired
    private CIndexApertureService cIndexApertureService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cindexaperture:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<CIndexAperture> cIndexApertureList = cIndexApertureService.queryList(query);
        int total = cIndexApertureService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(cIndexApertureList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);
    }

    /**
     * 列表
     */
    @RequestMapping("/listOpt")
    @RequiresPermissions("cindexaperture:list")
    public R listOpt(@RequestParam Map<String, Object> params) {
        List<CIndexAperture> cIndexApertureList = cIndexApertureService.queryListOpt();
        return R.success(cIndexApertureList);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cindexaperture:info")
    public R info(@PathVariable("id") Integer id) {
        CIndexAperture cIndexAperture = cIndexApertureService.queryObject(id);
        return R.success().put("cIndexAperture", cIndexAperture);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cindexaperture:save")
    public R save(@RequestBody CIndexAperture cIndexAperture) {
        cIndexApertureService.save(cIndexAperture);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cindexaperture:update")
    public R update(@RequestBody CIndexAperture cIndexAperture) {
        cIndexApertureService.update(cIndexAperture);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cindexaperture:delete")
    public R delete(@RequestBody Integer[] ids) {
        cIndexApertureService.deleteBatch(ids);
        return R.success();
    }

}
