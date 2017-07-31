package com.yonghui.portal.controller.manage;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.model.api.TokenApi;
import com.yonghui.portal.model.global.User;
import com.yonghui.portal.model.manage.HrProfit;
import com.yonghui.portal.service.global.UserService;
import com.yonghui.portal.service.manage.HrProfitService;
import com.yonghui.portal.util.R;
import com.yonghui.portal.util.manage.HrProfitUtil;
import com.yonghui.portal.util.redis.RedisBizUtilApi;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by liuwei on 2017/7/17.
 * 人事分红控制类
 */

@RestController
@RequestMapping("/api/hrPorfit")
public class HrProfitController {

    Logger log = Logger.getLogger(this.getClass());

    @Reference
    private HrProfitService hrProfitService;

    @Reference
    private UserService userService;

    @Autowired
    private RedisBizUtilApi redisBizUtilApi;

    @Autowired
    private HrProfitUtil hrProfitUtil;

    /**
     * 允许上传的扩展名
     */
    private static final String[] extensionPermit = {"xls", "xlsx"};


    /**
     * 同期利润导入
     *
     * @param multipartRequest
     * @param response
     * @param token
     * @return
     */
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public R Import(MultipartHttpServletRequest multipartRequest, HttpServletResponse response,
                    String token) {
        TokenApi tokenApi = null;
        String jobNumber = null;
        List<Map<String, Object>> profitList = null;
        List<HrProfit> excellist = new ArrayList<HrProfit>();
        try {
            if (token == null) {
                throw new Exception("token不存在");
            } else {
                String tokenApiJsonStr = redisBizUtilApi.getApiToken(token);
                tokenApi = JSONObject.parseObject(tokenApiJsonStr, TokenApi.class);
                jobNumber = tokenApi.getJobNumber();
            }
            for (Iterator it = multipartRequest.getFileNames(); it.hasNext(); ) {
                String key = (String) it.next();
                MultipartFile imgFile = multipartRequest.getFile(key);
                if (!imgFile.isEmpty()) {
                    String fileName = imgFile.getOriginalFilename();
                    String fileExtension = FilenameUtils.getExtension(fileName);
                    if (!ArrayUtils.contains(extensionPermit, fileExtension)) {
                        return R.error("您选择的文件不是excel");
                    }
                    // 根据文件名判断文件是2003版本还是2007版本
                    boolean isExcel2003 = false;
                    // 验证文件名是否合格
                    if (isExcel2003(fileName)) {
                        isExcel2003 = true;
                    } else if (isExcel2007(fileName)) {
                        isExcel2003 = false;
                    } else {
                        return R.error("您选择的文件类型有误");
                    }
                    // 读取excel里面的数据
                    excellist = getExcelInfo(imgFile.getInputStream(), isExcel2003);
                    //保存到临时表
                    for (HrProfit item : excellist) {
                        item.setCreater(jobNumber);
                    }
                    hrProfitService.insertProfitTmp(excellist, jobNumber);
                    //将临时表数据和正式数据进行比对，如有匹配上的需要返回
                    profitList = hrProfitService.profitTmpJoinList(jobNumber);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("程序出现异常");
        }
        return R.success(profitList);
    }

    /**
     * 区域导入确认接口
     *
     * @param request
     * @param response
     * @param token
     * @return
     */
    @RequestMapping(value = "confirm", method = RequestMethod.GET)
    public R confirm(HttpServletRequest request, HttpServletResponse response,
                     String token) {
        TokenApi tokenApi = null;
        String jobNumber = null;
        List<Map<String, Object>> profitList = null;
        try {
            if (token == null) {
                throw new Exception("token不存在");
            } else {
                String tokenApiJsonStr = redisBizUtilApi.getApiToken(token);
                tokenApi = JSONObject.parseObject(tokenApiJsonStr, TokenApi.class);
                jobNumber = tokenApi.getJobNumber();
            }
            //关联临时表，如果是重新导入，先删除
            List<Map<String, Object>> areaList = hrProfitService.profitTmpJoinList(jobNumber);
            List<Integer> areaIdList = new ArrayList<Integer>();
            List<Map<String, Object>> list = new ArrayList<>();
            Map<String, Object> map = null;
            for (Map<String, Object> item : areaList) {
                map = new HashMap<>();
                map.put("lkpMonth", item.get("lkpMonth"));
                map.put("shopid", item.get("shopid"));
                list.add(map);
            }
            //删除之前导入的，将临时表的数据导入到正式，清空临时表
            profitList = hrProfitService.deleteProfit(list, jobNumber);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return R.error("程序出现异常");
        }
        return R.success(profitList);
    }

    /**
     * 分红标准维护
     */
    @RequestMapping(value = "editRule", method = RequestMethod.GET)
    public R editRule(HttpServletRequest request, HttpServletResponse response, Integer id,
                      Integer empNum, BigDecimal standardRule, BigDecimal friRule, BigDecimal secRule,
                      Integer runtype) {
        try {
            hrProfitService.editRule(id, empNum, standardRule, friRule, secRule, runtype);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("分红标准维护,程序出现异常");
        }
        return R.success();
    }

    /**
     * 分红人员维护
     */
    @RequestMapping(value = "editEmp", method = RequestMethod.GET)
    public R editEmp(HttpServletRequest request, HttpServletResponse response, Integer id, Float guarantees,
                     Integer flagBonus, String turnDate, String shopName, String groupName) {
        try {
            Date date = null;
            if (turnDate != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                date = sdf.parse(turnDate);
            }
            hrProfitService.editEmp(id, guarantees, flagBonus, date, shopName, groupName);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("分红人员维护,程序出现异常");
        }
        return R.success();
    }


    /**
     * 查询分红标准
     */
    @RequestMapping(value = "queryRule", method = RequestMethod.GET)
    public R queryRule(HttpServletRequest request, HttpServletResponse response, String shopId, String groupId) {
        List<Map<String, Object>> list = null;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("shopId", shopId);
            map.put("groupId", groupId);
            list = hrProfitService.queryRule(map);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("查询分红标准,程序出现异常");
        }
        return R.success(list);
    }

    /**
     * 查询分红人员
     */
    @RequestMapping(value = "queryEmp", method = RequestMethod.GET)
    public R queryEmp(HttpServletRequest request, HttpServletResponse response, String shopId, String groupId) {
        List<Map<String, Object>> list = null;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("shopId", shopId);
            map.put("groupId", groupId);
            list = hrProfitService.queryEmp(map);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("查询分红标准,程序出现异常");
        }
        return R.success(list);
    }

    /**
     * 门店分红，根据用户id查询门店列表
     */
    @RequestMapping(value = "queryShop", method = RequestMethod.GET)
    public R queryShop(HttpServletRequest request, HttpServletResponse response, String token) {
        TokenApi tokenApi = null;
        List<Map<String, Object>> list = null;
        try {
            if (token == null) {
                return R.error("token不存在");
            } else {
                String tokenApiJsonStr = redisBizUtilApi.getApiToken(token);
                tokenApi = JSONObject.parseObject(tokenApiJsonStr, TokenApi.class);
                User user = userService.getUserByJobNumber(tokenApi.getJobNumber());
                list = hrProfitService.queryShop(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("根据用户id查询门店列表,程序出现异常");
        }
        return R.success(list);
    }

    /**
     * 查询联动规则
     */
    @RequestMapping(value = "queryDimRule", method = RequestMethod.GET)
    public R queryDimRule(String groupId, String empNum) {
        List<Map<String, Object>> list = null;
        try {
            list = hrProfitService.queryDimRule(groupId, empNum);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("查询联动规则,程序出现异常");
        }
        return R.success(list);
    }

    /**
     * 推送分红数据到sap（按区域或者门店）
     */
    @RequestMapping(value = "push", method = RequestMethod.GET)
    public R push(String shopId, String areaMans) {
        List<Map<String, Object>> list = null;
        try {
            list = hrProfitService.queryEmpProfit(shopId, areaMans);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("推送分红数据到sap,程序出现异常");
        }
        return R.success(list);
    }

    /**
     * 重新推送，已经失败的分红数据到sap
     */
    @RequestMapping(value = "pushFailedProfit", method = RequestMethod.GET)
    public R pushFailedProfit() {
        List<Map<String, Object>> list = null;
        try {
            list = hrProfitService.pushFailedProfit();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("推送分红数据到sap,程序出现异常");
        }
        return R.success(list);
    }

    /**
     * 撤销分红数据到sap
     */
    @RequestMapping(value = "cancelProfit", method = RequestMethod.GET)
    public R cancelProfit(String shopId, String empNo) {
        JSONObject jSONObject = null;
        try {
            jSONObject = hrProfitService.cancelProfit(shopId, empNo);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("撤销分红数据到sap,程序出现异常");
        }
        return R.success(JSONObject.parse(jSONObject.toJSONString()));
    }

    /**
     * 查询个人的分红信息
     */
    @RequestMapping(value = "queryProfit", method = RequestMethod.GET)
    public R cancelProfit(String empNo) {
        List<Map<String, Object>> list = null;
        try {
            list = hrProfitService.queryProfit(empNo);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("查询个人的分红信息,程序出现异常");
        }
        return R.success(list);
    }

    /**
     * 查询所有失败的分红信息
     */
    @RequestMapping(value = "queryFailPush", method = RequestMethod.GET)
    public R queryFailPush() {
        List<Map<String, Object>> list = null;
        try {
            list = hrProfitService.queryFailPush();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("查询所有失败的分红信息,程序出现异常");
        }
        return R.success(list);
    }


    // @描述：是否是2003的excel，返回true是2003
    public static boolean isExcel2003(String fileName) {
        return fileName.matches("^.+\\.(?i)(xls)$");
    }

    // @描述：是否是2007的excel，返回true是2007
    public static boolean isExcel2007(String fileName) {
        return fileName.matches("^.+\\.(?i)(xlsx)$");

    }

    public List<HrProfit> getExcelInfo(InputStream is, boolean isExcel2003) throws Exception {
        List<HrProfit> hrProfitList = null;
        try {
            /** 根据版本选择创建Workbook的方式 */
            Workbook wb = null;
            // 当excel是2003时
            if (isExcel2003) {
                wb = new HSSFWorkbook(is);
            } else {// 当excel是2007时
                wb = new XSSFWorkbook(is);
            }
            // 读取Excel里面客户的信息
            hrProfitList = hrProfitUtil.importHrProfit(wb);

        } catch (IOException e) {
            throw e;
        }
        return hrProfitList;
    }

}
