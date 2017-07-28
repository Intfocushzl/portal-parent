package com.yonghui.portal.controller.platform;

import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.platform.BravoShop;
import com.yonghui.portal.model.report.PortalProcedure;
import com.yonghui.portal.model.sys.SysLog;
import com.yonghui.portal.service.platform.BravoShopService;
import com.yonghui.portal.util.ComputerUtils;
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
        bravoShop.setCreater(ShiroUtils.getUserId());
		bravoShopService.save(bravoShop);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("bravoshop:update")
    public R update(@RequestBody BravoShop bravoShop){
        bravoShop.setModifier(ShiroUtils.getUserId());
		bravoShopService.update(bravoShop);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/delete")
    @RequiresPermissions("bravoshop:delete")
    public R delete(@RequestBody String[] shopids){

        StringBuffer str = new StringBuffer();
        for (int i = 0; i < shopids.length; i++) {
            BravoShop bravoShop = bravoShopService.queryObject(shopids[i]);
            str.append("sapshopid:"+bravoShop.getSapShopid()+"==name:"+bravoShop.getSname()+"===");
        }

        SysLog log = new SysLog();
        log.setIp(ComputerUtils.getIp());
        log.setUsername(ShiroUtils.getUserEntity().getUsername());
        log.setOperation(str.toString());

        bravoShopService.savelog(log);

		bravoShopService.deleteBatch(shopids);
        return R.success();
    }

}
