package com.yonghui.portal.controller.global;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yonghui.portal.model.global.User;
import com.yonghui.portal.service.global.MenuService;
import com.yonghui.portal.util.R;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xrr on 2017/5/8.
 */
@RestController
@RequestMapping(value="menu")
public class MenuController {

    Logger log=Logger.getLogger(this.getClass());

    @Reference
    private MenuService menuService;

    //获取新大区（区总教练）-- 不根据用户权限
    @RequestMapping("getNewAreaMansListByNotUser")
    @ResponseBody
    public R getNewAreaMansListByNotUser(HttpServletResponse response) {

        List<Map<String, Object>> list = menuService.getNewAreaMansList();
        if(list.size()>0){
            return R.success(list);
        }else{
            return R.error();
        }

    }

    //获取新大区（区总教练）-- 根据用户权限            youwenti
    @RequestMapping("getNewAreaMansListByUser")
    @ResponseBody
    public R getNewAreaMansListByUser(HttpSession session, HttpServletResponse response) {
        List<Map<String, Object>> list = null;
        User user = (User) session.getAttribute("user");

        if (user.getLargeArea() == null || user.getLargeArea().equals("ALL")) {
            list = menuService.getNewAreaMansList();
        }
        list = menuService.getNewAreaMansListByUser(user.getLargeArea());
        if(list.size()>0){
            return R.success(list);
        }else{
            return R.error();
        }
    }

    //根据新大区（区总教练）获取门店
    @RequestMapping("getNewStoreListByDistrictNotUser")
    @ResponseBody
    public R getNewStoreListByDistrictNotUser(HttpSession session, HttpServletResponse response,
                                                                      String district) {
        List<Map<String, Object>> list = menuService.getNewStoreListByDistrictNotUser(district);
        if(list.size() > 0){
            return R.success(list);
        }else{
            return R.error();
        }
    }

    //获取老大区 -- 不根据用户权限
    @RequestMapping("getLargeAreaList")
    @ResponseBody
    public R getLargeAreaList(HttpServletResponse response) {
        List<Map<String, Object>> list = menuService.getLargeAreaList();
        if(list.size() > 0){
            return R.success(list);
        }else{
            return R.error();
        }
    }

    //获取老大区 -- 根据用户权限
    @RequestMapping("getLargeAreaListByUser")
    @ResponseBody
    public R getLargeAreaListByUser(HttpSession session, HttpServletResponse response) {
        List<Map<String, Object>> list = null;
        User user = (User) session.getAttribute("user");

        if (user.getLargeArea() == null || user.getLargeArea().equals("ALL")) {
            list =  menuService.getLargeAreaList();
        }else{
            list =  menuService.getLargeAreaListByUser(user.getLargeArea());
        }
        if(list.size() > 0){
            return R.success(list);
        }else{
            return R.error();
        }
    }
    //根据老大区获取门店
    //# 获取全部门店 -- 不根据用户权限
    @RequestMapping("getStoreListByDistrictNotUser")
    @ResponseBody
    public R getStoreListByDistrictNotUser(HttpServletResponse response, String district) {
        List<Map<String, Object>> list =  menuService.getStoreListByDistrict(district, null);
        if(list.size() > 0){
            return R.success(list);
        }else{
            return R.error();
        }
    }

    //获取全部门店 -- 根据用户权限
    @RequestMapping("getStoreListByDistrict")
    @ResponseBody
    public R getStoreListByDistrict(HttpSession session, HttpServletResponse response,
                                                            String district) {
        List<String> slist = new ArrayList<String>();
        String storeNumber = (String) session.getAttribute("storeNumber");
        if (storeNumber != null) {// 过滤门店权限
            String[] str = storeNumber.split(",");

            if (str.length >= 1 && !str[0].equalsIgnoreCase("ALL")) {
                for (String s : str) {
                    slist.add(s);
                }
            } else {
                slist = null;
            }
        }
        List<Map<String, Object>> list = menuService.getStoreListByDistrict(district, slist);
        if(list.size() > 0){
            return R.success(list);
        }else{
            return R.error();
        }
    }


   ////////// 数据化运营平台小店（商行）筛选

    //人事分红小店 带全部
    @RequestMapping("broveStoreLittleIdList")
    @ResponseBody
    public R broveStoreLittleIdList(HttpSession session, HttpServletResponse response) {

        List<Integer> slist = new ArrayList<Integer>();
        User user = (User) session.getAttribute("user");
        if (user != null && user.getFirm() != null) {// 过滤门店权限

            String[] str = user.getFirm().split(",");

            if (str.length >= 1 && !str[0].equalsIgnoreCase("ALL") && !str[0].equalsIgnoreCase("")) {
                for (String s : str) {
                    slist.add(Integer.valueOf(s));
                }
            } else {
                slist = null;
            }
        } else {
            slist = null;
        }
        List<Map<String, Object>> list = menuService.broveStoreLittleIdList(slist);

        if(list.size() > 0){
            return R.success(list);
        }else{
            return R.error();
        }
    }

    //人事分红小店不带全部
    @RequestMapping("getFifthRotation")
    @ResponseBody
    public R getFifthRotation(HttpServletResponse response) {
        List<Map<String, Object>> list = menuService.getFifthRotation();

        if(list.size() > 0){
            return R.success(list);
        }else{
            return R.error();
        }
    }

    //不含餐饮小店(211)
    @RequestMapping("broveStoreLittleIdNotTwoOneOneList")
    @ResponseBody
    public R broveStoreLittleIdNotTwoOneOneList(HttpSession session,HttpServletResponse response) {

        List<Integer> slist = new ArrayList<Integer>();
        User user = (User) session.getAttribute("user");
        if (user != null && user.getFirm() != null) {// 过滤门店权限

            String[] str = user.getFirm().split(",");

            if (str.length >= 1 && !str[0].equalsIgnoreCase("ALL") && !str[0].equalsIgnoreCase("")) {
                for (String s : str) {
                    slist.add(Integer.valueOf(s));
                }
            } else {
                slist = null;
            }
        } else {
            slist = null;
        }

        List<Map<String, Object>> list = menuService.broveStoreLittleIdNotTwoOneOneList(slist);

        if(list.size() > 0){
            return R.success(list);
        }else{
            return R.error();
        }

    }

    //门店小店（财务）
    @RequestMapping("broveStoreLittleIdListByFinance")
    @ResponseBody
    public R broveStoreLittleIdListByFinance(HttpSession session,
                                                                     HttpServletResponse response) {
        List<Integer> slist = new ArrayList<Integer>();
        User user = (User) session.getAttribute("user");
        if (user != null && user.getFirm() != null) {// 过滤门店权限

            String[] str = user.getFirm().split(",");

            if (str.length >= 1 && !str[0].equalsIgnoreCase("ALL")) {
                for (String s : str) {
                    slist.add(Integer.valueOf(s));
                }
            } else {
                slist = null;
            }
        } else {
            slist = null;
        }
        List<Map<String, Object>> list = menuService.broveStoreLittleIdListByFinance(slist);
        if(list.size() > 0){
            return R.success(list);
        }else{
            return R.error();
        }

    }

    //BRAVO门店小店s
    @RequestMapping("broveList")
    @ResponseBody
    public R broveList(HttpServletResponse response, String district, String province,String areaName) {

        List<Map<String, Object>> list = menuService.secondClusterList(district, province, areaName);

        if(list.size() > 0){
            return R.success(list);
        }else{
            return R.error();
        }
    }



}
