package com.yonghui.portal.controller.businessman;

import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.businessman.ImgsInfo;
import com.yonghui.portal.service.businessman.ImgsInfoService;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.Query;
import com.yonghui.portal.util.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 图片信息
 *
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-07 16:33:28
 */
@RestController
@RequestMapping("imgsinfo")
public class ImgsInfoController extends AbstractController {
    @Autowired
    private ImgsInfoService imgsInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("imgsinfo:list")
    public R list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);

        List<ImgsInfo> imgsInfoList = imgsInfoService.queryList(query);
        int total = imgsInfoService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(imgsInfoList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("imgsinfo:info")
    public R info(@PathVariable("id") Long id){
        ImgsInfo imgsInfo = imgsInfoService.queryObject(id);
        return R.success().put("imgsInfo", imgsInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("imgsinfo:save")
    public R save(@RequestBody ImgsInfo imgsInfo){
		imgsInfoService.save(imgsInfo);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("imgsinfo:update")
    public R update(@RequestBody ImgsInfo imgsInfo){
		imgsInfoService.update(imgsInfo);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/delete")
    @RequiresPermissions("imgsinfo:delete")
    public R delete(@RequestBody Long[] ids){
		imgsInfoService.deleteBatch(ids);
        return R.success();
    }

}
