package com.yonghui.portal.controller.common;

import com.yonghui.portal.annotation.SysLog;
import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.common.DBravoShopAddress;
import com.yonghui.portal.service.common.DBravoShopAddressService;
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
 * 门店地址
 *
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-07-17 19:56:17
 */
@RestController
@RequestMapping("dbravoshopaddress")
public class DBravoShopAddressController extends AbstractController {
    @Autowired
    private DBravoShopAddressService dBravoShopAddressService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("dbravoshopaddress:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<DBravoShopAddress> dBravoShopAddressList = dBravoShopAddressService.queryList(query);
        int total = dBravoShopAddressService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(dBravoShopAddressList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("dbravoshopaddress:info")
    public R info(@PathVariable("id") Long id) {
        DBravoShopAddress dBravoShopAddress = dBravoShopAddressService.queryObject(id);
        return R.success().put("dBravoShopAddress", dBravoShopAddress);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("dbravoshopaddress:save")
    public R save(@RequestBody DBravoShopAddress dBravoShopAddress) {
        dBravoShopAddress.setCreater(ShiroUtils.getUserId());
        dBravoShopAddressService.save(dBravoShopAddress);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("dbravoshopaddress:update")
    public R update(@RequestBody DBravoShopAddress dBravoShopAddress) {
        dBravoShopAddress.setModifier(ShiroUtils.getUserId());
        dBravoShopAddressService.update(dBravoShopAddress);
        return R.success();
    }

    /**
     * 删除
     */
    @SysLog("删除门店")
    @RequestMapping("/delete")
    @RequiresPermissions("dbravoshopaddress:delete")
    public R delete(@RequestBody Long[] ids) {

        dBravoShopAddressService.deleteBatch(ids);
        return R.success();
    }

}
