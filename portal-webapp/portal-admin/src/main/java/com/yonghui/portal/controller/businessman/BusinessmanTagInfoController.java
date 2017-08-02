package com.yonghui.portal.controller.businessman;

import com.yonghui.portal.annotation.SysLog;
import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.businessman.BusinessmanTagInfo;
import com.yonghui.portal.service.businessman.BusinessmanTagInfoService;
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
 * 标签信息表
 *
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-07 16:33:29
 */
@RestController
@RequestMapping("businessmantaginfo")
public class BusinessmanTagInfoController extends AbstractController {
    @Autowired
    private BusinessmanTagInfoService businessmanTagInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("businessmantaginfo:list")
    public R list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);

        List<BusinessmanTagInfo> businessmanTagInfoList = businessmanTagInfoService.queryList(query);
        int total = businessmanTagInfoService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(businessmanTagInfoList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("businessmantaginfo:info")
    public R info(@PathVariable("id") Long id){
        BusinessmanTagInfo businessmanTagInfo = businessmanTagInfoService.queryObject(id);
        return R.success().put("businessmanTagInfo", businessmanTagInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("businessmantaginfo:save")
    public R save(@RequestBody BusinessmanTagInfo businessmanTagInfo){
        businessmanTagInfo.setCreater(ShiroUtils.getUserId());
		businessmanTagInfoService.save(businessmanTagInfo);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("businessmantaginfo:update")
    public R update(@RequestBody BusinessmanTagInfo businessmanTagInfo){
        businessmanTagInfo.setModifier(ShiroUtils.getUserId());
		businessmanTagInfoService.update(businessmanTagInfo);
        return R.success();
    }

    /**
     * 修改
     */
    @SysLog("删除标签")
    @RequestMapping("/delete")
    @RequiresPermissions("businessmantaginfo:delete")
    public R delete(@RequestBody Long[] ids){
		businessmanTagInfoService.deleteBatch(ids);
        return R.success();
    }

}
