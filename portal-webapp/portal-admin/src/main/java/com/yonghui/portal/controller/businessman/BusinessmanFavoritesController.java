package com.yonghui.portal.controller.businessman;

import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.businessman.BusinessmanFavorites;
import com.yonghui.portal.service.businessman.BusinessmanFavoritesService;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.Query;
import com.yonghui.portal.util.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 用户收藏表
 *
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-07 16:33:29
 */
@RestController
@RequestMapping("businessmanfavorites")
public class BusinessmanFavoritesController extends AbstractController {
    @Autowired
    private BusinessmanFavoritesService businessmanFavoritesService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("businessmanfavorites:list")
    public R list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);

        List<BusinessmanFavorites> businessmanFavoritesList = businessmanFavoritesService.queryList(query);
        int total = businessmanFavoritesService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(businessmanFavoritesList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("businessmanfavorites:info")
    public R info(@PathVariable("id") Long id){
        BusinessmanFavorites businessmanFavorites = businessmanFavoritesService.queryObject(id);
        return R.success().put("businessmanFavorites", businessmanFavorites);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("businessmanfavorites:save")
    public R save(@RequestBody BusinessmanFavorites businessmanFavorites){
		businessmanFavoritesService.save(businessmanFavorites);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("businessmanfavorites:update")
    public R update(@RequestBody BusinessmanFavorites businessmanFavorites){
		businessmanFavoritesService.update(businessmanFavorites);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/delete")
    @RequiresPermissions("businessmanfavorites:delete")
    public R delete(@RequestBody Long[] ids){
		businessmanFavoritesService.deleteBatch(ids);
        return R.success();
    }

}
