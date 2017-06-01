package com.yonghui.portal.controller.platform;

import java.util.List;
import java.util.Map;

import com.yonghui.portal.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yonghui.portal.model.platform.BravoShop;
import com.yonghui.portal.service.platform.BravoShopService;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.Query;
import com.yonghui.portal.util.R;

/**
 * 绿标门店
 *
 * @author yangyang
 * @email 80715104@yonghui.cn
 * @date 2017-05-26 17:02:43
 */
@RestController
@RequestMapping("bravoshop")
public class BravoShopController extends AbstractController {
    @Autowired
    private BravoShopService bravoShopService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("bravoshop:list")
    public R list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);

        List<BravoShop> bravoShopList = bravoShopService.queryList(query);
        int total = bravoShopService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(bravoShopList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{shopid}")
    @RequiresPermissions("bravoshop:info")
    public R info(@PathVariable("shopid") String shopid){
        BravoShop bravoShop = bravoShopService.queryObject(shopid);
        return R.success().put("bravoShop", bravoShop);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("bravoshop:save")
    public R save(@RequestBody BravoShop bravoShop){
		bravoShopService.save(bravoShop);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("bravoshop:update")
    public R update(@RequestBody BravoShop bravoShop){
		bravoShopService.update(bravoShop);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/delete")
    @RequiresPermissions("bravoshop:delete")
    public R delete(@RequestBody String[] shopids){
		bravoShopService.deleteBatch(shopids);
        return R.success();
    }

}
