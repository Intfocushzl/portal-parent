package com.yonghui.portal.controller.business;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yonghui.portal.annotation.OpenAuth;
import com.yonghui.portal.model.businessman.BusinessmanFavorites;
import com.yonghui.portal.service.business.ApiFavoritesService;
import com.yonghui.portal.service.sys.SysoperationLogService;
import com.yonghui.portal.util.ApiQuery;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.R;
import com.yonghui.portal.util.redis.ReportUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 用户收藏
 * 张海 2017.06.07
 */
@RestController
@RequestMapping("/api/business")
public class ApiFavoritesController {

    Logger log = Logger.getLogger(this.getClass());
    @Reference
    private SysoperationLogService sysoperationLogService;
    @Autowired
    private ReportUtil reportUtil;
    @Reference
    private ApiFavoritesService apiFavoritesService;

    /**
     * 获取用户收藏
     *
     * @param req
     * @param response
     */
    @RequestMapping(value = "favorites")
    public R favorites(HttpServletRequest req, HttpServletResponse response) {
        return R.success();
    }

    /**
     * 获取用户收藏列表
     *
     * @param req
     * @param response
     * @param params
     * @return
     */
    @OpenAuth
    @RequestMapping(value = "favoriteList" ,  method = RequestMethod.GET)
    public R favoriteList(HttpServletRequest req, HttpServletResponse response, @RequestParam Map<String, Object> params) {
        PageUtils pageUtil = null;
        try {
            ApiQuery query = new ApiQuery(params);
            List<Map<String, Object>> favoriteList = apiFavoritesService.favoriteList(query);
            int total = apiFavoritesService.queryTotal(query);
            pageUtil = new PageUtils(favoriteList, total, query.getLimit(), query.getPage());
        } catch (Exception e) {
            return R.error("获取用户收藏列表失败");
        }
        return R.success().put("page", pageUtil);
    }

    /**
     * 修改收藏状态
     *
     * @param req
     * @param response
     * @param params
     * @return
     */
    @OpenAuth
    @RequestMapping(value = "editfavorite" ,  method = RequestMethod.GET)
    public R editfavorite(HttpServletRequest req, HttpServletResponse response, @RequestParam Map<String, Object> params) {
        try {
            if (params.get("userId") == null || params.get("acticleId") == null) {
                return R.error("参数有误");
            } else {
                BusinessmanFavorites favorites = apiFavoritesService.favoriteDetail(params);
                if(favorites.getStatus().toString().equals(params.get("status").toString())){
                    return R.error("参数有误");
                }else{
                    apiFavoritesService.editfavorite(params);
                }
            }
        } catch (Exception e) {
            return R.error("修改收藏状态失败");
        }
        return R.success();
    }
}
