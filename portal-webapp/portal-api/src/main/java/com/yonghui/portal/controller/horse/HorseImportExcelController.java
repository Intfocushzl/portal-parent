package com.yonghui.portal.controller.horse;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yonghui.portal.annotation.IgnoreAuth;
import com.yonghui.portal.model.horse.HorseImportAreaMans;
import com.yonghui.portal.model.horse.HorseImportCash;
import com.yonghui.portal.model.horse.HorseOperateScore;
import com.yonghui.portal.service.horse.HorseImportExcelService;
import com.yonghui.portal.util.R;
import com.yonghui.portal.util.StringUtils;
import com.yonghui.portal.util.TimeUtil;
import com.yonghui.portal.util.horse.HorseExcelUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 赛马营运数据和收银准确率数据导入api接口
 *
 * @author liuwei
 */
@RestController
@RequestMapping("/bravo/horseimport")
public class HorseImportExcelController {

    Logger log = Logger.getLogger(this.getClass());


    public int totalCells;

    @Reference
    private HorseImportExcelService horseImportExcelService;

    /**
     * 允许上传的扩展名
     */
    private static final String[] extensionPermit = {"xsl", "xlsx"};

    /**
     * 赛马运营分数导入
     *
     * @param multipartRequest
     * @param response
     * @throws Exception
     */
    @IgnoreAuth
    @RequestMapping(value = "score", method = RequestMethod.POST)
    public R importscore(MultipartHttpServletRequest multipartRequest, HttpServletResponse response,
                         String user, String areamans) {
        int i = 0;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=UTF-8");
        Map<String, Object> resMap = new HashMap<String, Object>();
        List<HorseOperateScore> excellist = new ArrayList<HorseOperateScore>();
        try {
            String time = getDate();
            // String user = multipartRequest.getParameter("user");
            // 查询该用户上月收银未确认的数据,如果有,提示他去确认
            if (user == null) {
                throw new Exception("工号为空");
            }
            List<HorseOperateScore> list = horseImportExcelService.getlatelyscoreListState(user, areamans, time);
            if (list.size() > 0) {
                log.info("您上月有未确认数据不能进行导入操作");
                resMap.put("result", "0");
                resMap.put("list", excellist);
                return R.success(resMap);
            }
            for (Iterator it = multipartRequest.getFileNames(); it.hasNext(); ) {
                String key = (String) it.next();
                MultipartFile imgFile = multipartRequest.getFile(key);
                if (!imgFile.isEmpty()) {
                    String fileName = imgFile.getOriginalFilename();
                    String fileExtension = FilenameUtils.getExtension(fileName);
                    if (!ArrayUtils.contains(extensionPermit, fileExtension)) {
                        resMap.put("result", "0");
                        resMap.put("list", excellist);
                        return R.success(resMap);
                    }
                    // 根据文件名判断文件是2003版本还是2007版本
                    boolean isExcel2003 = false;
                    // 验证文件名是否合格
                    if (isExcel2003(fileName)) {
                        isExcel2003 = true;
                    } else if (isExcel2007(fileName)) {
                        isExcel2003 = false;
                    } else {
                        resMap.put("result", "0");
                        resMap.put("list", excellist);
                        return R.success(resMap);
                    }
                    // 查询所有门店所对应的的小区
                    List<HorseImportAreaMans> listinfo = horseImportExcelService.getareamans();
                    Map<String, String> map = new HashMap<String, String>();
                    for (HorseImportAreaMans item : listinfo) {
                        map.put(item.getId(), item.getAreamans());
                    }
                    // 读取excel里面的数据
                    excellist = getExcelInfo(imgFile.getInputStream(), isExcel2003);
                    Map<String, Object> excellistinfoMap = new HashMap<String, Object>();
                    if (excellist == null || excellist.size() == 0) {
                        resMap.put("result", "0");
                        resMap.put("list", excellist);
                        return R.success(resMap);
                    }
                    // 查询该区域全部数据，转成map，用于判断倒入数据是否重复
                    List<HorseOperateScore> totelList = horseImportExcelService.getscorelist(user, areamans);
                    Map<String, Object> totelMap = new HashMap<String, Object>();
                    for (HorseOperateScore item : totelList) {
                        totelMap.put(item.getSdate() + "-" + item.getSapshopid() + "-" + item.getGroupid(), item);
                    }

                    for (HorseOperateScore item : excellist) {
                        // 判断sapshopid和groupid是否正确
                        if (item.getSapshopid() != null) {
                            Map<String, String> shopnamemap = horseImportExcelService.getshopname(item.getSapshopid());
                            if (shopnamemap == null) {
                                throw new Exception("填写的sapshopid有误，请检查后再试");
                            } else {
                                String shopname = shopnamemap.get("shopname");
                                item.setShopname(shopname);
                            }
                            if(!time.equals(item.getSdate())){
                                throw new Exception("上传文件里日期不是上月");
                            }
                        } else {
                            throw new Exception("填写的sapshopid有空值，请检查后再试");
                        }

                        if (item.getGroupid() != null) {
                            Map<String, String> groupnamemap = horseImportExcelService
                                    .getgroupname(item.getGroupid().toString());
                            if (groupnamemap == null) {
                                throw new Exception("填写的groupid有误，请检查后再试");
                            } else {
                                String shopname = groupnamemap.get("groupname");
                                item.setGroupname(shopname);
                            }
                        } else {
                            throw new Exception("填写的groupid有空值，请检查后再试");
                        }

                        if (excellistinfoMap
                                .get(item.getSdate() + "-" + item.getSapshopid() + "-" + item.getGroupid()) != null) {
                            throw new Exception("您导入的excel里面有重复的数据");
                        }
                        excellistinfoMap.put(item.getSdate() + "-" + item.getSapshopid() + "-" + item.getGroupid(),
                                item);

                        item.setAccount(user);// 记录上传人账号

                        item.setFlag(0);

                        TimeUtil util = new TimeUtil();
                        item.setUpdatetime(util.getFromDataToString(new Date()));

                        if (map.get(item.getSapshopid()) != null) {// 记录小店区域名称
                            if (!map.get(item.getSapshopid()).equals(areamans)) {
                                resMap.put("result", "-2");
                                resMap.put("list", excellist);
                                return R.success(resMap);
                            }
                            item.setAreamans(map.get(item.getSapshopid()));
                        }

                        if (totelMap
                                .get(item.getSdate() + "-" + item.getSapshopid() + "-" + item.getGroupid()) != null) {
                            throw new Exception("您excel中数据与数据库里面的重复，请修改后再上传");
                        }
                    }
                    i = horseImportExcelService.insertscorelist(excellist);
                    log.info("添加记录:" + i);
                }

            }
        } catch (Exception e) {
            log.error("导入数据异常", e);
            resMap.put("result", "-1");
            resMap.put("msg", e.getMessage());
            resMap.put("list", excellist);
            return R.success(resMap);
        }
        resMap.put("result", i);
        resMap.put("list", excellist);
        return R.success(resMap);
    }

    /**
     * 赛马人才梯队分数导入
     *
     * @param multipartRequest
     * @param response
     * @throws Exception
     */
    @IgnoreAuth
    @RequestMapping(value = "humanscore", method = RequestMethod.POST)
    public R importhumanscore(MultipartHttpServletRequest multipartRequest,
                              HttpServletResponse response, String user, String areamans) {
        int i = 0;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=UTF-8");
        Map<String, Object> resMap = new HashMap<String, Object>();
        List<HorseOperateScore> excellist = new ArrayList<HorseOperateScore>();
        try {
            String time = getDate();
            // String user = multipartRequest.getParameter("user");
            // 查询该用户上月收银未确认的数据,如果有,提示他去确认
            if (user == null) {
                throw new Exception("工号为空");
            }
            List<HorseOperateScore> list = horseImportExcelService.getlatelyscoreListStatehuman(user, areamans, time);
            if (list.size() > 0) {
                log.info("您上月有未确认数据不能进行导入操作");
                resMap.put("result", "0");
                resMap.put("list", excellist);
                return R.success(resMap);
            }
            for (Iterator it = multipartRequest.getFileNames(); it.hasNext(); ) {
                String key = (String) it.next();
                MultipartFile imgFile = multipartRequest.getFile(key);
                if (!imgFile.isEmpty()) {
                    String fileName = imgFile.getOriginalFilename();
                    String fileExtension = FilenameUtils.getExtension(fileName);
                    if (!ArrayUtils.contains(extensionPermit, fileExtension)) {
                        resMap.put("result", "0");
                        resMap.put("list", excellist);
                        return R.success(resMap);
                    }
                    // 根据文件名判断文件是2003版本还是2007版本
                    boolean isExcel2003 = false;
                    // 验证文件名是否合格
                    if (isExcel2003(fileName)) {
                        isExcel2003 = true;
                    } else if (isExcel2007(fileName)) {
                        isExcel2003 = false;
                    } else {
                        resMap.put("result", "0");
                        resMap.put("list", excellist);
                        return R.success(resMap);
                    }
                    // 查询所有门店所对应的的小区
                    List<HorseImportAreaMans> listinfo = horseImportExcelService.getareamans();
                    Map<String, String> map = new HashMap<String, String>();
                    for (HorseImportAreaMans item : listinfo) {
                        map.put(item.getId(), item.getAreamans());
                    }
                    // 读取excel里面的数据
                    excellist = getExcelInfo(imgFile.getInputStream(), isExcel2003);
                    if (excellist == null || excellist.size() == 0) {
                        resMap.put("result", "0");
                        resMap.put("list", excellist);
                        return R.success(resMap);
                    }
                    // 查询该区域全部数据，转成map，用于判断倒入数据是否重复
                    List<HorseOperateScore> totelList = horseImportExcelService.getscorelisthuman(user, areamans);
                    Map<String, Object> totelMap = new HashMap<String, Object>();
                    for (HorseOperateScore item : totelList) {
                        totelMap.put(item.getSdate() + "-" + item.getSapshopid() + "-" + item.getGroupid(), item);
                    }
                    Map<String, Object> excellistinfoMap = new HashMap<String, Object>();
                    for (HorseOperateScore item : excellist) {
                        // 判断sapshopid和groupid是否正确
                        if (item.getSapshopid() != null) {
                            Map<String, String> shopnamemap = horseImportExcelService.getshopname(item.getSapshopid());
                            if (shopnamemap == null) {
                                throw new Exception("填写的sapshopid有误，请检查后再试");
                            } else {
                                String shopname = shopnamemap.get("shopname");
                                item.setShopname(shopname);
                            }
                            if(!time.equals(item.getSdate())){
                                throw new Exception("上传文件里日期不是上月");
                            }
                        } else {
                            throw new Exception("填写的sapshopid有空值，请检查后再试");
                        }

                        if (item.getGroupid() != null) {
                            Map<String, String> groupnamemap = horseImportExcelService
                                    .getgroupname(item.getGroupid().toString());
                            if (groupnamemap == null) {
                                throw new Exception("填写的groupid有误，请检查后再试");
                            } else {
                                String shopname = groupnamemap.get("groupname");
                                item.setGroupname(shopname);
                            }
                        } else {
                            throw new Exception("填写的groupid有空值，请检查后再试");
                        }

                        if (excellistinfoMap
                                .get(item.getSdate() + "-" + item.getSapshopid() + "-" + item.getGroupid()) != null) {
                            throw new Exception("您导入的excel里面有重复的数据");
                        }
                        excellistinfoMap.put(item.getSdate() + "-" + item.getSapshopid() + "-" + item.getGroupid(),
                                item);

                        item.setAccount(user);// 记录上传人账号

                        TimeUtil util = new TimeUtil();
                        item.setUpdatetime(util.getFromDataToString(new Date()));

                        item.setFlag(0);

                        if (map.get(item.getSapshopid()) != null) {// 记录小店区域名称
                            if (!map.get(item.getSapshopid()).equals(areamans)) {
                                resMap.put("result", "-2");
                                resMap.put("list", excellist);
                                return R.success(resMap);
                            }
                            item.setAreamans(map.get(item.getSapshopid()));
                        }

                        if (totelMap
                                .get(item.getSdate() + "-" + item.getSapshopid() + "-" + item.getGroupid()) != null) {
                            throw new Exception("您excel中数据与数据库里面的重复，请修改后再上传");
                        }
                    }
                    i = horseImportExcelService.insertscorelisthuman(excellist);
                    log.info("添加记录:" + i);
                }

            }
        } catch (Exception e) {
            log.error("导入数据异常", e);
            resMap.put("result", "-1");
            resMap.put("msg", e.getMessage());
            resMap.put("list", excellist);
            return R.success(resMap);
        }
        resMap.put("result", i);
        resMap.put("list", excellist);
        return R.success(resMap);
    }

    /**
     * 赛马收银准确性数据导入 liuwei
     *
     * @param multipartRequest
     * @param response
     * @throws Exception
     */
    @IgnoreAuth
    @RequestMapping(value = "cash", method = RequestMethod.POST)
    public R importpaytruely(MultipartHttpServletRequest multipartRequest,
                             HttpServletResponse response, String user, String areamans) {
        int i = 0;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=UTF-8");
        Map<String, Object> resMap = new HashMap<String, Object>();
        List<HorseImportCash> excellist = new ArrayList<HorseImportCash>();
        try {
            String time = getDate();
            // String user = multipartRequest.getParameter("user");
            // 查询该用户上月收银未确认的数据,如果有,提示他去确认
            if (user == null) {
                throw new Exception("工号为空");
            }
            List<HorseImportCash> list = horseImportExcelService.getlatelycashlistState(user, areamans, time);
            if (list.size() > 0) {
                throw new Exception("您上月有未确认数据不能进行导入操作");
            }
            for (Iterator it = multipartRequest.getFileNames(); it.hasNext(); ) {
                String key = (String) it.next();
                MultipartFile imgFile = multipartRequest.getFile(key);
                if (!imgFile.isEmpty()) {
                    String fileName = imgFile.getOriginalFilename();
                    String fileExtension = FilenameUtils.getExtension(fileName);
                    if (!ArrayUtils.contains(extensionPermit, fileExtension)) {
                        resMap.put("result", "0");
                        resMap.put("list", excellist);
                        return R.success(resMap);
                    }
                    // 根据文件名判断文件是2003版本还是2007版本
                    boolean isExcel2003 = false;
                    // 验证文件名是否合格
                    if (isExcel2003(fileName)) {
                        isExcel2003 = true;
                    } else if (isExcel2007(fileName)) {
                        isExcel2003 = false;
                    } else {
                        resMap.put("result", "0");
                        resMap.put("list", excellist);
                        return R.success(resMap);
                    }
                    List<HorseImportAreaMans> listinfo = horseImportExcelService.getareamans();
                    Map<String, String> map = new HashMap<String, String>();
                    for (HorseImportAreaMans item : listinfo) {
                        map.put(item.getId(), item.getAreamans());
                    }
                    excellist = getExcelInfo1(imgFile.getInputStream(), isExcel2003);
                    if (excellist == null || excellist.size() == 0) {
                        resMap.put("result", "0");
                        resMap.put("list", excellist);
                        return R.success(resMap);
                    }

                    // 查询该区域全部数据，转成map，用于判断倒入数据是否重复
                    List<HorseImportCash> totelList = horseImportExcelService.getchashlist(user, areamans);
                    Map<String, Object> totelMap = new HashMap<String, Object>();
                    for (HorseImportCash item : totelList) {
                        totelMap.put(item.getSdate() + "-" + item.getSapshopid() + "-" + item.getGroupid(), item);
                    }

                    Map<String, Object> excellistinfoMap = new HashMap<String, Object>();
                    for (HorseImportCash item : excellist) {
                        // 判断sapshopid和groupid是否正确
                        if (item.getSapshopid() != null) {
                            Map<String, String> shopnamemap = horseImportExcelService.getshopname(item.getSapshopid());
                            if (shopnamemap == null) {
                                throw new Exception("填写的sapshopid有误，请检查后再试");
                            } else {
                                String shopname = shopnamemap.get("shopname");
                                item.setShopname(shopname);
                            }
                            if(!time.equals(item.getSdate())){
                                throw new Exception("上传文件里日期不是上月");
                            }
                        } else {
                            throw new Exception("填写的sapshopid有空值，请检查后再试");
                        }

                        if (item.getGroupid() != null) {
                            Map<String, String> groupnamemap = horseImportExcelService
                                    .getgroupname(item.getGroupid().toString());
                            if (groupnamemap == null) {
                                throw new Exception("填写的groupid有误，请检查后再试");
                            } else {
                                String shopname = groupnamemap.get("groupname");
                                item.setGroupname(shopname);
                            }
                        } else {
                            throw new Exception("填写的groupid有空值，请检查后再试");
                        }

                        if (excellistinfoMap
                                .get(item.getSdate() + "-" + item.getSapshopid() + "-" + item.getGroupid()) != null) {
                            throw new Exception("您导入的excel里面有重复的数据");
                        }
                        excellistinfoMap.put(item.getSdate() + "-" + item.getSapshopid() + "-" + item.getGroupid(),
                                item);

                        item.setAccount(user);

                        TimeUtil util = new TimeUtil();
                        item.setUpdatetime(util.getFromDataToString(new Date()));

                        item.setFlag(0);

                        if (map.get(item.getSapshopid()) != null) {// 记录小店区域名称
                            if (!map.get(item.getSapshopid()).equals(areamans)) {
                                resMap.put("result", "-2");
                                resMap.put("list", excellist);
                                return R.success(resMap);
                            }
                            item.setAreamans(map.get(item.getSapshopid()));
                        }

                        if (totelMap
                                .get(item.getSdate() + "-" + item.getSapshopid() + "-" + item.getGroupid()) != null) {
                            throw new Exception("您excel中数据与数据库里面的重复，请修改后再上传");
                        }
                    }
                    i = horseImportExcelService.insertcashlist(excellist);
                    log.info("添加记录:" + i);
                }
            }
        } catch (Exception e) {
            log.error("导入数据异常", e);
            resMap.put("result", "-1");
            resMap.put("msg", e.getMessage());
            resMap.put("list", excellist);
            return R.success(resMap);
        }
        resMap.put("result", i);
        resMap.put("list", excellist);
        return R.success(resMap);
    }

    /**
     * 查询该用户上月收银数据
     *
     * @param user
     * @param areamans
     * @return
     */
    @RequestMapping(value = "lastcashlist", method = RequestMethod.GET)
    public R getlatelycashlist(String user, String areamans, HttpServletResponse response,
                               HttpServletRequest request) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=UTF-8");
        List<HorseImportCash> list = new ArrayList<HorseImportCash>();
        String time = getDate();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            list = horseImportExcelService.getlatelycashlist(user, areamans, time);
            if (list.size() > 0) {
                for (HorseImportCash item : list) {
                    if (item.getSapshopid() != null) {
                        item.setShopname(horseImportExcelService.getshopname(item.getSapshopid()).get("shopname"));
                    }
                    if (item.getGroupid() != null) {
                        item.setGroupname(
                                horseImportExcelService.getgroupname(item.getGroupid().toString()).get("groupname"));
                    }
                }
                for (HorseImportCash item : list) {
                    if (item.getFlag() == 0) {
                        map.put("code", "2");// 代表该用户上月有未确认的数据，需要提示用户确认
                        break;
                    } else {
                        map.put("code", "3");// 代表上月该用户导入的数据全部为已确认状态
                    }
                }
            } else {
                map.put("code", "1");// 代表上月该用户未导入数据
            }
            map.put("list", list);
        } catch (Exception e) {
            log.error("查询异常", e);
            return R.success(map);
        }
        return R.success(map);
    }

    /**
     * 查询该用户上月运营得分数据
     *
     * @param user
     * @param areamans
     * @return
     */
    @RequestMapping(value = "lastscorelist", method = RequestMethod.GET)
    public R getlatelyscorelist(String user, String areamans, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=UTF-8");
        List<HorseOperateScore> list = new ArrayList<HorseOperateScore>();
        Map<String, Object> map = new HashMap<String, Object>();
        String time = getDate();
        try {
            list = horseImportExcelService.getlatelyscorelist(user, areamans, time);
            if (list.size() > 0) {
                for (HorseOperateScore item : list) {
                    if (item.getSapshopid() != null) {
                        item.setShopname(horseImportExcelService.getshopname(item.getSapshopid()).get("shopname"));
                    }
                    if (item.getGroupid() != null) {
                        item.setGroupname(
                                horseImportExcelService.getgroupname(item.getGroupid().toString()).get("groupname"));
                    }
                }
                for (HorseOperateScore item : list) {
                    if (item.getFlag() == 0) {
                        map.put("code", "2");// 代表该用户上月有未确认的数据，需要提示用户确认
                        break;
                    } else {
                        map.put("code", "3");// 代表上月该用户导入的数据全部为已确认状态
                    }
                }
            } else {
                map.put("code", "1");// 代表上月该用户未导入数据
            }
            map.put("list", list);
        } catch (Exception e) {
            log.error("查询异常", e);
            return R.success(map);
        }
        return R.success(map);
    }

    /**
     * 查询该用户上月得分数据(人才梯队)
     *
     * @param user
     * @param areamans
     * @return
     */
    @RequestMapping(value = "lasthumanscorelist", method = RequestMethod.GET)
    public R getlatelyhumanscorelist(String user, String areamans, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=UTF-8");
        List<HorseOperateScore> list = new ArrayList<HorseOperateScore>();
        Map<String, Object> map = new HashMap<String, Object>();
        String time = getDate();
        try {
            list = horseImportExcelService.getlatelyscorelisthuman(user, areamans, time);
            if (list.size() > 0) {
                for (HorseOperateScore item : list) {
                    if (item.getSapshopid() != null) {
                        item.setShopname(horseImportExcelService.getshopname(item.getSapshopid()).get("shopname"));
                    }
                    if (item.getGroupid() != null) {
                        item.setGroupname(
                                horseImportExcelService.getgroupname(item.getGroupid().toString()).get("groupname"));
                    }
                }
                for (HorseOperateScore item : list) {
                    if (item.getFlag() == 0) {
                        map.put("code", "2");// 代表该用户上月有未确认的数据，需要提示用户确认
                        break;
                    } else {
                        map.put("code", "3");// 代表上月该用户导入的数据全部为已确认状态
                    }
                }
            } else {
                map.put("code", "1");// 代表上月该用户未导入数据
            }
            map.put("list", list);
        } catch (Exception e) {
            log.error("查询异常", e);
            return R.success(map);
        }
        return R.success(map);
    }

    /**
     * 查询全部收银数据
     *
     * @param areamans
     * @return
     */
    @RequestMapping(value = "cashlist", method = RequestMethod.GET)
    public R getchashlist(HttpServletResponse response, String areamans, String user) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=UTF-8");
        List<HorseImportCash> list = new ArrayList<HorseImportCash>();
        try {
            list = horseImportExcelService.getchashlist(user, areamans);
            for (HorseImportCash item : list) {
                if (item.getSapshopid() != null) {
                    item.setShopname(horseImportExcelService.getshopname(item.getSapshopid()).get("shopname"));
                }
                if (item.getGroupid() != null) {
                    item.setGroupname(
                            horseImportExcelService.getgroupname(item.getGroupid().toString()).get("groupname"));
                }
            }
        } catch (Exception e) {
            log.error("查询异常", e);
            return R.success(list);
        }
        return R.success(list);
    }

    /**
     * 查询所有运营得分数据
     *
     * @param areamans
     * @return
     */
    @RequestMapping(value = "scorelist", method = RequestMethod.GET)
    public R getscorelist(HttpServletResponse response, String areamans, String user) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=UTF-8");
        List<HorseOperateScore> list = new ArrayList<HorseOperateScore>();
        try {
            list = horseImportExcelService.getscorelist(user, areamans);
            for (HorseOperateScore item : list) {
                if (item.getSapshopid() != null) {
                    item.setShopname(horseImportExcelService.getshopname(item.getSapshopid()).get("shopname"));
                }
                if (item.getGroupid() != null) {
                    item.setGroupname(
                            horseImportExcelService.getgroupname(item.getGroupid().toString()).get("groupname"));
                }
            }
        } catch (Exception e) {
            log.error("查询异常", e);
            return R.success(list);
        }
        return R.success(list);
    }

    /**
     * 查询所有得分数据(人才梯队)
     *
     * @param areamans
     * @return
     */
    @RequestMapping(value = "humanscorelist", method = RequestMethod.GET)
    public R gethumanscorelist(HttpServletResponse response, String areamans, String user) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=UTF-8");
        List<HorseOperateScore> list = new ArrayList<HorseOperateScore>();
        try {
            list = horseImportExcelService.getscorelisthuman(user, areamans);
            for (HorseOperateScore item : list) {
                if (item.getSapshopid() != null) {
                    item.setShopname(horseImportExcelService.getshopname(item.getSapshopid()).get("shopname"));
                }
                if (item.getGroupid() != null) {
                    item.setGroupname(
                            horseImportExcelService.getgroupname(item.getGroupid().toString()).get("groupname"));
                }
            }
        } catch (Exception e) {
            log.error("查询异常", e);
            return R.success(list);
        }
        return R.success(list);
    }

    @RequestMapping(value = "editcashstate", method = RequestMethod.POST)
    public R updatecashstate(HttpServletResponse response, String areamans, String user) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=UTF-8");
        String time = getDate();
        int i = 0;
        try {
            List<HorseImportCash> list = horseImportExcelService.getlatelycashlistState(user, areamans, time);
            if (list.size() == 0 || list == null) {
                // 没有需要确认的数据
                return R.success(i);
            }
            TimeUtil util = new TimeUtil();
            i = horseImportExcelService.updatecashstate(user, areamans, time, util.getFromDataToString(new Date()));
        } catch (Exception e) {
            log.error("确认异常", e);
            i = -1;
            return R.success(i);
        }
        return R.success(i);
    }

    @RequestMapping(value = "editscorestate", method = RequestMethod.POST)
    public R updatescorestate(HttpServletResponse response, String areamans, String user) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String time = getDate();
        int i = 0;
        try {
            List<HorseOperateScore> list = horseImportExcelService.getlatelyscoreListState(user, areamans, time);
            if (list.size() == 0 || list == null) {
                // 没有需要确认的数据
                return R.success(i);
            }
            TimeUtil util = new TimeUtil();
            i = horseImportExcelService.updatescorestate(user, areamans, time, util.getFromDataToString(new Date()));
        } catch (Exception e) {
            log.error("确认异常", e);
            i = -1;
            return R.success(i);
        }
        return R.success(i);
    }

    @RequestMapping(value = "edithumanscorestate", method = RequestMethod.POST)
    public R updatehumanscorestate(HttpServletResponse response, String areamans, String user) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String time = getDate();
        int i = 0;
        try {
            List<HorseOperateScore> list = horseImportExcelService.getlatelyscoreListStatehuman(user, areamans, time);
            if (list.size() == 0 || list == null) {
                // 没有需要确认的数据
                return R.success(i);
            }
            TimeUtil util = new TimeUtil();
            i = horseImportExcelService.updatescorestatehuman(user, areamans, time,
                    util.getFromDataToString(new Date()));
        } catch (Exception e) {
            log.error("确认异常", e);
            i = -1;
            return R.success(i);
        }
        return R.success(i);
    }

    @RequestMapping(value = "deletecash", method = RequestMethod.POST)
    public R deleteUnconfirmedCash(HttpServletResponse response, String areamans, String user) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String time = getDate();
        int i = 0;
        try {
            i = horseImportExcelService.deleteUnconfirmedCash(user, areamans, time);
        } catch (Exception e) {
            log.error("刪除异常", e);
            return R.success(i);
        }
        return R.success(i);
    }

    @RequestMapping(value = "deletehumanscore", method = RequestMethod.POST)
    public R deletehumanUnconfirmedScore(HttpServletResponse response, String areamans, String user) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String time = getDate();
        int i = 0;
        try {
            i = horseImportExcelService.deleteUnconfirmedScorehuman(user, areamans, time);
        } catch (Exception e) {
            log.error("刪除异常", e);
            return R.success(i);
        }
        return R.success(i);
    }

    @RequestMapping(value = "deletescore", method = RequestMethod.POST)
    public R deleteUnconfirmedScore(HttpServletResponse response, String areamans, String user) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String time = getDate();
        int i = 0;
        try {
            i = horseImportExcelService.deleteUnconfirmedScore(user, areamans, time);
        } catch (Exception e) {
            log.error("刪除异常", e);
            return R.success(i);
        }
        return R.success(i);
    }

    /**
     * 根据excel里面的内容读取客户信息
     *
     * @param is          输入流
     * @param isExcel2003 excel是2003还是2007版本
     * @return
     * @throws Exception
     */
    public List<HorseOperateScore> getExcelInfo(InputStream is, boolean isExcel2003) throws Exception {
        List<HorseOperateScore> scoreList = null;
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
            scoreList = importExcelForHorseScore(wb);
        } catch (IOException e) {
            throw e;
        }
        return scoreList;
    }

    public List<HorseOperateScore> importExcelForHorseScore(Workbook wb) throws Exception {
        // 得到第一个shell
        Sheet sheet = wb.getSheetAt(0);
        // 得到Excel的行数
        int totalRows = sheet.getLastRowNum();// 所有行
        // 得到Excel的最大的一列(前提是有行数)
        if (totalRows >= 1 && sheet.getRow(0) != null) {
            this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        } else {
            throw new Exception("请按照模板填写数据后上传");
        }
        List<HorseOperateScore> scoreList = new ArrayList<HorseOperateScore>();
        HorseOperateScore score;
        Row row;
        // 循环Excel行数,从第三行开始。
        for (int r = 1; r <= totalRows; r++) {
            row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            score = new HorseOperateScore();
            // 循环Excel的列
            for (int c = 0; c < this.totalCells; c++) {
                Cell cell = row.getCell(c);
                // 格式化，每个单元个都为字符串
                if (cell != null) {
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    // 设置行号
                    score.setRowNum(r + 1);
                    // 判断是否为空
                    boolean isNull = StringUtils.isEmpty(cell.getStringCellValue().trim());
                    if (isNull) {
                        throw new Exception("填写的数据含有空值");
                    }
                    // 判断是否全部是数字
                    boolean isNum = StringUtils.isNumeric(cell.getStringCellValue().trim());
                    if (!isNum) {
                        throw new Exception("表格中有非数字的数据");
                    }
                    if (c == 0) {
                        if (cell.getStringCellValue().trim().length() != 6) {
                            throw new Exception("请填写正确的六位数日期，例如201704");
                        }
                        score.setSdate(cell.getStringCellValue().trim());
                    } else if (c == 1) {
                        score.setSapshopid(cell.getStringCellValue().trim());
                    } else if (c == 2) {
                        score.setGroupid(Integer.valueOf(cell.getStringCellValue().trim()));
                    } else if (c == 3) {
                        score.setThevalue(Double.valueOf(cell.getStringCellValue().trim()));
                    }
                }
            }
            // 添加数据
            scoreList.add(score);
        }
        return scoreList;
    }

    /**
     * 收银准确性
     *
     * @param is
     * @param isExcel2003
     * @return
     * @throws Exception
     */
    public List<HorseImportCash> getExcelInfo1(InputStream is, boolean isExcel2003) throws Exception {
        List<HorseImportCash> list = null;
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
            list = new HorseExcelUtil().importExcelForHorsePayTruely(wb);
        } catch (IOException e) {
            throw e;
        }
        return list;
    }

    // @描述：是否是2003的excel，返回true是2003
    public static boolean isExcel2003(String fileName) {
        return fileName.matches("^.+\\.(?i)(xls)$");
    }

    // @描述：是否是2007的excel，返回true是2007
    public static boolean isExcel2007(String fileName) {
        return fileName.matches("^.+\\.(?i)(xlsx)$");
    }

    public String getDate() {
        String result = null;
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        if (month > 9) {
            result = year + "" + month;
        } else {
            result = year + "0" + month;
        }
        return result;
    }
}
