package com.yonghui.portal.controller.horse;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yonghui.portal.model.horse.DataMapIndexAperture;
import com.yonghui.portal.service.horse.DataMapIndexApertureService;
import com.yonghui.portal.util.R;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 元数据统一api接口
 *
 * @author liuwei
 */
@RestController
@RequestMapping("/bravo/aperture")
public class DataApertureController {

    Logger log = Logger.getLogger(this.getClass());

    @Reference
    private DataMapIndexApertureService dataMapIndexApertureService;

    /**
     * 查询所有的指标名称
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    @ResponseBody
    public R indexlist(HttpServletRequest request, HttpServletResponse response, String theme) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=UTF-8");
        List<String> list = new ArrayList<String>();
        try {
            list = dataMapIndexApertureService.getindexlist(theme);
            return R.success(list);
        } catch (Exception e) {
            log.error("查询指标异常", e);
            return R.error();
        }
    }

    /**
     * 查询所有的主题名称
     */
    @RequestMapping(value = "theme", method = RequestMethod.GET)
    @ResponseBody
    public R themelist(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=UTF-8");
        List<String> list = new ArrayList<String>();
        try {
            list = dataMapIndexApertureService.getthemelist();
            return R.success(list);
        } catch (Exception e) {
            log.error("查询指标异常", e);
            return R.error();
        }
    }

    /**
     * 根据指标名称查询统一口径数据信息
     *
     * @param request
     * @param response
     * @param index
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public R list(HttpServletRequest request, HttpServletResponse response, String index,
                  String theme) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=UTF-8");
        List<DataMapIndexAperture> list = new ArrayList<DataMapIndexAperture>();
        try {
            list = dataMapIndexApertureService.getDataMapIndexAperture(theme, index);
            return R.success(list);
        } catch (Exception e) {
            log.error("查询口径数据异常", e);
            return R.error();
        }
    }
}
