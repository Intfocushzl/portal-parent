package com.yonghui.portal.controller.cindex;

import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.cindex.CIndexRefer;
import com.yonghui.portal.service.cindex.CIndexReferService;
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
@RequestMapping("cindexrefer")
public class CIndexReferController extends AbstractController {
    @Autowired
    private CIndexReferService cIndexReferService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cindexrefer:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<CIndexRefer> cIndexReferList = cIndexReferService.queryList(query);
        int total = cIndexReferService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(cIndexReferList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);
    }

    /**
     * 列表
     */
    @RequestMapping("/listOpt")
    public R listOpt(@RequestParam Map<String, Object> params) {
        List<CIndexRefer> cIndexReferList = cIndexReferService.queryListOpt();
        return R.success(cIndexReferList);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cindexrefer:info")
    public R info(@PathVariable("id") Integer id) {
        CIndexRefer cIndexRefer = cIndexReferService.queryObject(id);
        return R.success().put("cIndexRefer", cIndexRefer);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cindexrefer:save")
    public R save(@RequestBody CIndexRefer cIndexRefer) {
        cIndexReferService.save(cIndexRefer);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cindexrefer:update")
    public R update(@RequestBody CIndexRefer cIndexRefer) {
        cIndexReferService.update(cIndexRefer);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cindexrefer:delete")
    public R delete(@RequestBody Integer[] ids) {
        cIndexReferService.deleteBatch(ids);
        return R.success();
    }

}
