package com.yonghui.portal.controller.channelTransparency;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.model.channelTransparency.ShopDisVO;
import com.yonghui.portal.model.channelTransparency.ShopImgVO;
import com.yonghui.portal.model.channelTransparency.ShopPlanVO;
import com.yonghui.portal.model.channelTransparency.ShopRackVO;
import com.yonghui.portal.model.global.User;
import com.yonghui.portal.service.channelTransparency.*;
import com.yonghui.portal.util.PropertiesTools;
import com.yonghui.portal.util.R;
import com.yonghui.portal.util.TimeUtil;
import com.yonghui.portal.util.ftp.FtpConst;
import com.yonghui.portal.util.ftp.FtpTool;
import com.yonghui.portal.util.ftp.SFTPConstants;
import com.yonghui.portal.util.ftp.SFTPUpload;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

/**
 * 通道透明化控制類
 *
 * @author Shengwm
 */
@RestController
@RequestMapping("/bravo/channelTransparencyController")
/*@Api(value = "/bravo/channelTransparencyController", description = "通道透明化")*/
public class ChannelTransparencyController {

    Logger log = Logger.getLogger(this.getClass());
    @Reference
    private ShopRackService shopRackService;


    @Reference
    private ShopDisService shopDisService;

    @Reference
    private ShopImgService shopImgService;

    @Reference
    private ShopPlanService shopPlanService;
    @Reference
    private FirmService firmService;
    @Reference
    private MenuService menuService;
    @Value(value = "${template.excel}")
    private String templatePath;

    /**
     * 允许上传的扩展名
     */
    private static final String[] extensionPermit = {"jpg", "JPG", "png", "PNG", "gif", "GIF"};

    /**
     * 根据门店ID取出陈列位信息
     *
     * @param req
     * @param response
     * @param shopid
     * @return
     */
/*    @ApiOperation(value = "根据门店ID取出陈列位信息", produces = MediaType.APPLICATION_JSON_VALUE, notes = "根据门店ID取出陈列位信息",response = R.class)
    @GetMapping(value = "/getShopRackVOSByshopId", produces = MediaType.APPLICATION_JSON_VALUE)*/
    @RequestMapping(value="getShopRackVOSByshopId",method = RequestMethod.GET)
    public R groupHorseTotal(HttpServletRequest req, HttpServletResponse response, String shopid,  String area,   String groupid) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> map = new HashMap<>();
        map.put("shopid", shopid);
        map.put("area", area);
        map.put("groupid", groupid);
        List<ShopRackVO> list = shopRackService.queryRacks(map);
        JSONObject jo = new JSONObject();
        jo.put("rows", list);
        return R.success(list);
    }

    /**
     * 根据门店ID取出陈列位信息
     *
     * @param req
     * @param response
     * @param shopid
     * @return
     */
    @RequestMapping(value = "selectShopRack", produces = "application/json; charset=utf-8")
    public R selectShopRack(HttpServletRequest req, HttpServletResponse response, String shopid) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        ShopRackVO shopRackVO = shopRackService.selectByPrimaryKey(Long.valueOf(shopid));
        return R.success(shopRackVO);
    }

    /**
     * 商行列表
     *
     * @param session
     * @param response
     * @return
     */
    @RequestMapping("listGroup")
    public R listGroup(HttpSession session, HttpServletResponse response) {
        try {
            return R.success(shopDisService.listGroup());
        } catch (Exception e) {
            log.error("获取商行列表出错", e);
            return R.error("获取商行列表出错");
        }
    }

    /**
     * 当期列表
     *
     * @param session
     * @param response
     * @return
     */
    @RequestMapping("listDateNo")
    public R listDateNo(HttpSession session, HttpServletResponse response) {
        try {
            return R.success(shopDisService.listDateNo());
        } catch (Exception e) {
            log.error("获取档期列表出错", e);
            return R.error("获取档期列表出错");
        }
    }

    /**
     * 根据门店ID取出陈列位信息
     *
     * @param req
     * @param response
     * @param shopid
     * @return
     */
    @RequestMapping(value = "getShopImgsByshopId", produces = "application/json; charset=utf-8")
    public R getShopImgsByshopId(HttpServletRequest req, HttpServletResponse response, String shopid) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        List<ShopImgVO> list = shopImgService.selectByShopid(shopid);
        JSONObject jo = new JSONObject();
        jo.put("rows", list);
        return R.success(list);
    }

    /**
     * 下载Excel
     *
     * @param req
     * @param model
     * @throws IOException
     */
    @RequestMapping(value = "/downloadExcel.do")
    public void downloadExcel(HttpServletRequest req, HttpServletResponse res, Map<String, Object> model)
            throws IOException {
        // 下载路径+文件名
        String path = PropertiesTools.getFileIO("template.excel", "/settings.properties");
        String filename = path + "shop_rack_tpl.xlsx";
        String fileDownLoadName = "shop_rack_tpl.xlsx";
        // fileDownLoadName = URLEncoder.encode(fileDownLoadName,"UTF-8");
        fileDownLoadName = new String(fileDownLoadName.getBytes("UTF-8"), "ISO8859-1");

        res.addHeader("Content-Disposition", "attachment;filename=" + fileDownLoadName);
        OutputStream osm = null;
        FileInputStream in = null;

        try {
            // 获取输出流
            osm = res.getOutputStream();
            // 文件读入
            in = new FileInputStream(filename);
            byte[] b = new byte[1024];
            int i = 0;
            while ((i = in.read(b)) > 0) {
                osm.write(b, 0, i);
            }
            osm.flush();
            osm.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
                in = null;
            }
        }
    }

    /**
     * 门店图片上传
     *
     * @param multipartRequest
     * @param response
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/upload", produces = "application/json; charset=utf-8")
    public R imgUpload(String shopid,String storeNumber, MultipartHttpServletRequest multipartRequest, HttpServletResponse response) {
        String json = "{\"result\":1}";
        if (storeNumber != null && !storeNumber.equals("ALL")) {
            response.setContentType("image/jpeg");

            ShopImgVO pimg = null;
            // String saveDirectoryPath =
            // "C:\\soft\\apache-tomcat-8.0.35\\yonghui\\\\upload\\image\\finance\\";
            /*
             * String saveDirectoryPath =
			 * "/home/soft/tomcat/webapps/upload/image/finance/";
			 */
            // String saveDirectoryPath =
            // "/home/soft/tomcat/webapps/upload/image/finance/";request.getSession().getServletContext().getRealPath("/");
            String saveDirectoryPath = multipartRequest.getSession().getServletContext().getRealPath("/");

            // windows路径
            // String path = "\\upload\\image\\";
            // linux路径
            String path = "/upload/image/";

            String s = multipartRequest.getContextPath().toString();
            // String saveDirectoryPath = s;
            saveDirectoryPath = saveDirectoryPath + path;
            log.info("saveDirectoryPath :" + saveDirectoryPath);
            for (Iterator it = multipartRequest.getFileNames(); it.hasNext(); ) {
                String key = (String) it.next();
                MultipartFile imgFile = multipartRequest.getFile(key);

                if (imgFile.getOriginalFilename().length() > 0) {
                    try {
                        saveDirectoryPath = saveDirectoryPath + TimeUtil.getTimeYMD();
                        String uuid = UUID.randomUUID().toString();
                        // windows
                        // String name = "\\" + uuid + ".jpg";
                        // linux
                        String name = "/" + uuid + ".jpg";
                        File saveDirectory = new File(saveDirectoryPath);
                        if (!saveDirectory.isDirectory() && !saveDirectory.exists()) {
                            saveDirectory.mkdirs();
                        }
                        if (!imgFile.isEmpty()) {
                            String fileName = imgFile.getOriginalFilename();
                            String fileExtension = FilenameUtils.getExtension(fileName);
                            if (!ArrayUtils.contains(extensionPermit, fileExtension)) {
                                throw new Exception("No Support extension.");
                            }

                            saveDirectoryPath = saveDirectoryPath + name;

                            log.info("saveDirectoryPath:" + saveDirectoryPath);
                            FileOutputStream out = new FileOutputStream(saveDirectoryPath);
                            // 写入文件
                            out.write(imgFile.getBytes());
                            out.flush();
                            out.close();

                            pimg = new ShopImgVO();
                                pimg.setShopid(storeNumber);
                            String pname = "/" + uuid + ".jpg";
                            pimg.setImgurl(s + "/upload/image/" + TimeUtil.getTimeYMD() + pname);
                            // pimg.setTitle(key);
                            if (multipartRequest.getParameter("title") != null
                                    && StringUtils.isNoneBlank(multipartRequest.getParameter("title"))) {
                                pimg.setTitle(multipartRequest.getParameter("title"));
                            }
                            pimg.setUpdatedate(new Date());
                            pimg.setShopid(shopid);
                            shopImgService.insertSelective(pimg);
                        }
                    } catch (Exception e) {
                        log.info("上传异常", e);
                    }
                }
            }
        } else {
            json = "{\"result\":\"对不起，没有门店权限！\"}";//
            return R.error("对不起，没有门店权限！");
        }
        return R.success("上传成功");
    }

    /**
     * 门店图片上传ftp
     *
     * @param multipartRequest
     * @param response
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/uploadShop", produces = "application/json; charset=utf-8")
    public R imgUploadShop(String shopid,String storeNumber, MultipartHttpServletRequest multipartRequest,
                                HttpServletResponse response) {
        String json = "{\"result\":1}";
        if (storeNumber != null && !storeNumber.equals("ALL")) {
            response.setContentType("image/jpeg");

            ShopImgVO pimg = null;
            // String saveDirectoryPath =
            // "C:\\soft\\apache-tomcat-8.0.35\\yonghui\\\\upload\\image\\finance\\";
            /*
             * String saveDirectoryPath =
			 * "/home/soft/tomcat/webapps/upload/image/finance/";
			 */
            // String saveDirectoryPath =
            // "/home/soft/tomcat/webapps/upload/image/finance/";request.getSession().getServletContext().getRealPath("/");
            String saveDirectoryPath = multipartRequest.getSession().getServletContext().getRealPath("/");

            // windows路径
            // String path = "\\upload\\image\\";
            // linux路径
            String path = "/upload/image/";

            String s = multipartRequest.getContextPath().toString();
            // String saveDirectoryPath = s;
            saveDirectoryPath = saveDirectoryPath + path;
            for (Iterator it = multipartRequest.getFileNames(); it.hasNext(); ) {
                String key = (String) it.next();
                MultipartFile imgFile = multipartRequest.getFile(key);

                if (imgFile.getOriginalFilename().length() > 0) {
                    try {
                        saveDirectoryPath = saveDirectoryPath + TimeUtil.getTimeYMD();
                        String uuid = UUID.randomUUID().toString();
                        // windows
                        // String name = "\\" + uuid + ".jpg";
                        // linux
                        String name = "/" + uuid + ".jpg";
                        File saveDirectory = new File(saveDirectoryPath);
                        if (!saveDirectory.isDirectory() && !saveDirectory.exists()) {
                            saveDirectory.mkdirs();
                        }
                        if (!imgFile.isEmpty()) {
                            String fileName = imgFile.getOriginalFilename();
                            String fileExtension = FilenameUtils.getExtension(fileName);
                            /*
                             * if (!ArrayUtils.contains(extensionPermit,
							 * fileExtension)) { throw new Exception(
							 * "No Support extension."); }
							 * 
							 * saveDirectoryPath = saveDirectoryPath + name;
							 * 
							 * log.info("saveDirectoryPath:" +
							 * saveDirectoryPath); FileOutputStream out = new
							 * FileOutputStream(saveDirectoryPath); // 写入文件
							 * out.write(imgFile.getBytes()); out.flush();
							 * out.close();
							 */
                            // File ff = (File) imgFile;
                            InputStream in = imgFile.getInputStream();
                            String filepath = TimeUtil.getTimeYMD();
                            String ftpname = uuid + ".jpg";

                            SFTPUpload.upload(in, SFTPConstants.LOCATION_IMAGE + filepath + "/" + ftpname, SFTPConstants.LOCATION_IMAGE + "/" + filepath);

                            // ("10.0.31.206", 21, "administrator", "yhcs1234",
                            // "shcg/", "shop_rack_tpl.xlsx", "D:/")

                            pimg = new ShopImgVO();
                                pimg.setShopid(storeNumber);
                            String pname = "/" + uuid + ".jpg";
                            pimg.setImgurl(SFTPConstants.SHOP_IMAGE + TimeUtil.getTimeYMD() + pname);
                            // pimg.setTitle(key);
                            if (multipartRequest.getParameter("title") != null
                                    && StringUtils.isNoneBlank(multipartRequest.getParameter("title"))) {
                                pimg.setTitle(multipartRequest.getParameter("title"));
                            }
                            pimg.setUpdatedate(new Date());
                            shopImgService.insertSelective(pimg);
                        }
                    } catch (Exception e) {
                        log.info("上传异常", e);
                    }
                }
            }
        } else {
            json = "{\"result\":\"对不起，没有门店权限！\"}";//
            return R.error("对不起，没有门店权限！");
        }
        return R.success("上传成功");
    }

    /**
     * 门店陈列excel
     *
     * @param multipartRequest
     * @param response
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/uploadShopPlan", produces = "application/json; charset=utf-8")
    public R uploadShopPlan(String shopid, String storeNumber,MultipartHttpServletRequest multipartRequest,
                                 HttpServletResponse response) {
        User user = (User) multipartRequest.getSession().getAttribute("user");
        String json = "{\"result\":1}";
        if (storeNumber != null && !storeNumber.equals("ALL")) {
            ShopPlanVO shopPlanVO = new ShopPlanVO();
            shopPlanVO.setShopid(storeNumber);
            ShopImgVO pimg = null;
            for (Iterator it = multipartRequest.getFileNames(); it.hasNext(); ) {
                String key = (String) it.next();
                MultipartFile imgFile = multipartRequest.getFile(key);

                if (imgFile.getOriginalFilename().length() > 0) {
                    try {
                        String uuid = UUID.randomUUID().toString();
                        // windows
                        // String name = "\\" + uuid + ".jpg";
                        // linux
                        if (!imgFile.isEmpty()) {
                            InputStream in = imgFile.getInputStream();
                            String filepath = TimeUtil.getTimeYMD();
                            String ftpname = uuid + ".xlsx";
                            // SFTPUpload.upload(in,
                            // SFTPConstants.LOCATION_SHOPPLAN+ftpname);
                            /*FtpTool.uploadFile("10.0.31.206", 21, "administrator", "yhcs1234", "shcg/shopplan",
                                    filepath, ftpname, in);*/
                            // ("10.0.31.206", 21, "administrator", "yhcs1234",
                            // "shcg/", "shop_rack_tpl.xlsx", "D:/")
                            SFTPUpload.upload(in, SFTPConstants.LOCATION_SHOPPLAN + filepath + "/" + ftpname, SFTPConstants.LOCATION_SHOPPLAN + "/" + filepath);
                            pimg = new ShopImgVO();
                            if (user != null) {
                                pimg.setShopid(user.getStoreNumber());
                            }
                            String pname = "/" + uuid + ".xlsx";
                            pimg.setImgurl(SFTPConstants.LOCATION_SHOPPLAN + TimeUtil.getTimeYMD() + pname);
                            // pimg.setTitle(key);
                            if (multipartRequest.getParameter("title") != null
                                    && StringUtils.isNoneBlank(multipartRequest.getParameter("title"))) {
                                pimg.setTitle(multipartRequest.getParameter("title"));
                            }
                            pimg.setUpdatedate(new Date());
                            shopPlanVO.setUpdatedate(new Date());
                            shopPlanVO.setUrl(SFTPConstants.SHOP_PLAN + TimeUtil.getTimeYMD() + pname);
                            shopPlanService.updateByShopid(shopPlanVO);
                        }
                    } catch (Exception e) {
                        log.info("上传异常", e);
                    }
                }
            }
        } else {
            json = "{\"result\":\"对不起，没有门店权限！\"}";//
            return R.error("对不起，没有门店权限！");
        }
        return R.success("上传成功");
    }

    /**
     * 门店EXCEL上传
     *
     * @param ex
     * @param req
     * @param response
     * @param model
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/uploadShopRack.do", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public R uploadData(Exception ex,String storeNumber, HttpServletRequest req, HttpServletResponse response,
                             Map<String, Object> model,
                             @org.springframework.web.bind.annotation.RequestParam("file") CommonsMultipartFile file) throws Exception {
        String json = "{\"result\":1}";
        if (storeNumber != null && !storeNumber.equals("ALL")) {
            if (file.getSize() > 0) {

                String AllExt = ".xls|.xlsx|";
                String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."))
                        .toLowerCase();
                if (AllExt.indexOf(ext + "|") != -1) {

                    List<ShopRackVO> data = new ArrayList<>();
                    StringBuffer mapAllotKey = new StringBuffer(100);

                    String[] arrstr = null;

                    InputStream fis = file.getInputStream();
                    com.yonghui.portal.util.ExcelParse ep = new com.yonghui.portal.util.ExcelParse();
                    ep.reader(fis, 0);
                    Map<String, List<String[]>> map = ep.getMap();
                    if (map.size() == 0) {
                        json = "{\"result\":\"请填写上传内容！\"}";//
                        return R.error("请填写上传内容！");
                    }

                    String[] headerTemplate = {"门店名称", "商行ID", "商行名称", "陈列位编码", "陈列类型"};
                    List<String> err = new ArrayList<String>();
                    Iterator<Map.Entry<String, List<String[]>>> it = map.entrySet().iterator();
                    while (it.hasNext()) {

                        Map.Entry<String, List<String[]>> entry = (Map.Entry<String, List<String[]>>) it.next();
                        List<String[]> list = entry.getValue();
                        if (list == null || list.size() == 0) {
                            err.add(entry.getKey() + "请填写上传内容！\n");
                        } else {

                            String[] header = list.get(0);
                            if (header.length < headerTemplate.length) {
                                err.add(entry.getKey() + "上传列数与模板不符！\n");
                            } else {
                                boolean has = false;
                                for (int i = 0; i < headerTemplate.length; i++) {
                                    if (!headerTemplate[i].equals(header[i])) {
                                        err.add(entry.getKey() + "第" + (i + 1) + "列与“" + header[i] + "”模板不符\n");
                                        has = true;
                                    }
                                }
                                if (has) {
                                    continue;
                                }

                                // row
                                for (int i = 1; i < list.size(); i++) {
                                    ShopRackVO shopRackVO = new ShopRackVO();
                                    // bo = null;
                                    mapAllotKey.setLength(0);
                                    // 补全数组
                                    if (list.get(i).length < headerTemplate.length) {
                                        arrstr = new String[headerTemplate.length];
                                        String[] tmp = list.get(i);
                                        for (int j = 0; j < tmp.length; j++) {
                                            arrstr[j] = tmp[j];
                                        }
                                    } else {
                                        arrstr = list.get(i);
                                    }
                                    /*
									 * if (StringUtils.isNotBlank(arrstr[0])) {
									 * shopRackVO.setShopid(arrstr[0]); }
									 */
                                    if (StringUtils.isNotBlank(arrstr[0])) {
                                        shopRackVO.setShopname(arrstr[0]);
                                    }
                                    if (StringUtils.isNotBlank(arrstr[1])) {
                                        shopRackVO.setGroupid(arrstr[1]);
                                    }
                                    if (StringUtils.isNotBlank(arrstr[2])) {
                                        shopRackVO.setGroupname(arrstr[2]);
                                    }

                                    if (StringUtils.isNotBlank(arrstr[3])) {
                                        shopRackVO.setRackno(arrstr[3]);
                                    }
									/*
									 * if (StringUtils.isNotBlank(arrstr[4])) {
									 * shopRackVO.setArea(Double.valueOf(arrstr[
									 * 4])); }
									 */
                                    if (StringUtils.isNotBlank(arrstr[4])) {
                                        shopRackVO.setDistype(arrstr[4]);
                                    }
                                    shopRackVO.setShopid(storeNumber);
                                    shopRackVO.setUpdateDate(new Date());
                                    data.add(shopRackVO);
                                }
                            }
                        }
                    }
                    if (err.size() > 0) {
                        json = "{\"result\":" + JSON.toJSONString(err) + "}";
                        return R.error(json);
                    }
                    shopRackService.insertlist(data);
                    return R.success("上传成功");
                }
            }
        } else {
            json = "{\"result\":\"您尚无门店权限！\"}";
            return R.error("您尚无门店权限！");
        }
        return R.success("成功");
    }

    /**
     * 门店陈列信息
     *
     * @param req
     * @param
     * @param
     * @param model
     * @return
     * @throws IOException
     */

    @RequestMapping("/listTable.do")
    public R listTable(HttpServletRequest req, HttpServletResponse response, String shopid, String area, String groupid, String rackNo, Map<String, Object> model)
            throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("shopid", shopid);
        map.put("area", area);
        map.put("groupid", groupid);
        map.put("rackNo", rackNo);
        List<ShopRackVO> list = shopRackService.queryRacks(map);
        model.put("data", list);
        return R.success(list);
    }

    /**
     * 门店陈列快照信息
     *
     * @param req
     * @param dateno
     * @param groupid
     * @param model
     * @return
     * @throws IOException
     */
    @RequestMapping("/listShopDisTable.do")
    public R listShopDisTable (HttpServletRequest req, String dateno, String groupid, String shopid,Map<String, Object> model) {
        List<ShopDisVO> list = shopDisService.selectByShopidAndGroupidAndShopid(dateno, groupid, shopid);
        model.put("data", list);
        return R.success(list);
    }

    /**
     * 门店陈列图
     *
     * @param req
     * @param
     * @param
     * @param model
     * @return
     * @throws IOException
     */
    @RequestMapping("/shopplan.do")
    public R shopplan(HttpServletRequest req, String shopid, Map<String, Object> model) throws IOException {
        // String shopid = req.getParameter("shopid");
        ShopPlanVO shopPlanVO = shopPlanService.selectByShopid(shopid);
        if (shopPlanVO != null && shopPlanVO.getUrl() != null) {
            return R.success(shopPlanVO.getUrl());
        }
        return R.error("暂无图片");
    }

    /**
     * 新增陈列快照
     *
     * @param multipartRequest
     * @param shopDisVO
     * @param res
     * @param model
     * @return
     */

    @RequestMapping(value = "/add.do", produces = "application/json; charset=utf-8")
    public R add(MultipartHttpServletRequest multipartRequest, String storeNumber, ShopDisVO shopDisVO, HttpServletResponse res,
                      Map<String, Object> model) {
        Map<String, Object> map = new HashMap<>();
        String msg = "ok";
        String json = "{\"result\":1}";
        if (storeNumber != null && !storeNumber.equals("ALL")) {
            if (multipartRequest.getParameter("id").equals("")) {
                // ShopDisVO shopDisVO = new ShopDisVO();
                if (multipartRequest.getParameter("dateno") != null) {
                    shopDisVO.setDateno(multipartRequest.getParameter("dateno").trim());
                }

                if (multipartRequest.getParameter("groupid") != null) {
                    shopDisVO.setGroupid(multipartRequest.getParameter("groupid").trim());
                }

				/*
				 * if(multipartRequest.getParameter("shopid") != null){
				 * shopDisVO.setShopid(multipartRequest.getParameter("shopid").
				 * trim()); }
				 */
                if (multipartRequest.getParameter("rackno") != null) {
                    shopDisVO.setRackno(multipartRequest.getParameter("rackno").trim());
                }
                if (multipartRequest.getParameter("shopname") != null) {
                    shopDisVO.setShopname(multipartRequest.getParameter("shopname").trim());
                }
                shopDisVO.setShopid(storeNumber);
                shopDisVO.setImgurl(uploadFile("/upload/image/", "/upload/image/", multipartRequest));
                shopDisVO.setUpdatedate(new Date());
                shopDisService.insertSelective(shopDisVO);
            } else {
                for (Iterator it = multipartRequest.getFileNames(); it.hasNext(); ) {
                    String key = (String) it.next();
                    MultipartFile imgFile = multipartRequest.getFile(key);
                    if (imgFile != null) {
                        shopDisVO.setImgurl(uploadFile("/upload/image/", "/upload/image/", multipartRequest));
                    }

                }
                shopDisVO.setShopid(storeNumber);
                shopDisVO.setUpdatedate(new Date());
                shopDisService.updateByPrimaryKeySelective(shopDisVO);
            }
        } else {
            json = "{\"result\":\"您尚无门店权限！\"}";
            return R.error("您尚无门店权限！");
        }

        return R.success("成功！");
    }

    /**
     * 编辑陈列快照
     *
     * @param req
     * @param id
     * @param model
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/edit.do")
    public R edit(HttpServletRequest req, String id, Map<String, Object> model) throws IOException {
        ShopDisVO shopDisVO = new ShopDisVO();
        Map<String, Object> map = new HashMap<>();
        shopDisVO = shopDisService.selectByPrimaryKey(Long.valueOf(id));
        model.put("data", shopDisVO);
        return R.success(shopDisVO);

    }

    /**
     * 编辑陈列信息
     *
     * @param req
     * @param id
     * @param model
     * @return
     * @throeditShopRack.dows IOException
     */
    @RequestMapping(value = "/editShopRack.do")
    public R editShopRack(HttpServletRequest req, String id, Map<String, Object> model) throws IOException {
        ShopRackVO shopRackVO = new ShopRackVO();
        Map<String, Object> map = new HashMap<>();
        shopRackVO = shopRackService.selectByPrimaryKey(Long.valueOf(id));
        model.put("data", shopRackVO);
        return R.success(shopRackVO);
    }

    /**
     * 更新陈列信息
     *
     * @param req
     * @param
     * @param id
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateShopRack.do")
    public R updateShopRack(HttpServletRequest req, ShopRackVO shopRackVO, String id, Map<String, Object> model)
            throws Exception {
        String msg = "ok";
        shopRackVO.setUpdateDate(new Date());
        if (shopRackVO.getShopid() != null) {
            Map<String, Object> mapshop = menuService.getShopByShopId(shopRackVO.getShopid());
            if (mapshop != null && mapshop.get("text") != null) {
                shopRackVO.setShopname(mapshop.get("text").toString());
            }
        }
        if (shopRackVO.getGroupid() != null) {
            Map<String, Object> mapgroup = firmService.getGroupByGroupId(shopRackVO.getGroupid());
            if (mapgroup != null && mapgroup.get("groupname") != null) {
                shopRackVO.setGroupname(mapgroup.get("groupname").toString());
            }
        }
        shopRackService.updateByPrimaryKeySelective(shopRackVO);
        return R.success("修改成功");
    }

    /**
     * 新增陈列快照
     *
     * @param multipartRequest
     * @param
     * @param res
     * @param model
     * @return
     * @throws Exception
     */

    @RequestMapping(value = "/addRack.do", produces = "application/json; charset=utf-8")
    public R addRack(HttpServletRequest multipartRequest, String storeNumber,ShopRackVO shopRackVO, HttpServletResponse res,
                          Map<String, Object> model) throws Exception {
        Map<String, Object> map = new HashMap<>();
        String msg = "ok";
        String json = "{\"result\":1}";
        if (storeNumber != null && !storeNumber.equals("ALL")) {
            if (shopRackVO.getShopid() != null) {
                Map<String, Object> mapshop = menuService.getShopByShopId(shopRackVO.getShopid());
                if (mapshop != null && mapshop.get("text") != null) {
                    shopRackVO.setShopname(mapshop.get("text").toString());
                }
            }
            if (shopRackVO.getGroupid() != null) {
                Map<String, Object> mapgroup = firmService.getGroupByGroupId(shopRackVO.getGroupid());
                if (mapgroup != null && mapgroup.get("groupname") != null) {
                    shopRackVO.setGroupname(mapgroup.get("groupname").toString());
                }
            }
            shopRackVO.setUpdateDate(new Date());
            shopRackService.insertSelective(shopRackVO);
            //	shopRackVOMapper.insertSelective(shopRackVO);

        } else {
            json = "{\"result\":\"您尚无门店权限！\"}";
            return R.error("您尚无门店权限！");
        }

        return R.success();
    }

    /**
     * 删除陈列信息
     *
     * @param req
     * @param
     * @param id
     * @param model
     * @return
     * @throws IOException
     */
    @RequestMapping("/deleteShopRack.do")
    public R deleteShopRack(HttpServletRequest req, String id, Map<String, Object> model) throws IOException {
        String msg = "ok";
        shopRackService.deleteByPrimaryKey(Long.valueOf(id));
        //	shopRackVOMapper.deleteByPrimaryKey(Long.valueOf(id));
        return R.success("删除成功");
    }

    /**
     * 更新陈列快照
     *
     * @param req
     * @param shopDisVO
     * @param id
     * @param model
     * @return
     * @throws IOException
     */
    @RequestMapping("/update.do")
    public R update(MultipartHttpServletRequest req, ShopDisVO shopDisVO, String id, Map<String, Object> model)
            throws IOException {
        String msg = "ok";
        if (req.getParameter("file") != null) {
            shopDisVO.setImgurl(uploadFile("/upload/image/", "/upload/image/", req));
        }
        shopDisVO.setUpdatedate(new Date());
        shopDisService.updateByPrimaryKeySelective(shopDisVO);
        return R.success("更新成功");
    }



    /**
     * 上传图片方法
     *
     * @param savepath
     * @param urlpath
     * @param multipartRequest
     * @return
     */
    public String uploadFile(String savepath, String urlpath, MultipartHttpServletRequest multipartRequest) {
        String url = "";

        for (Iterator it = multipartRequest.getFileNames(); it.hasNext(); ) {
            String key = (String) it.next();
            MultipartFile imgFile = multipartRequest.getFile(key);

            if (imgFile.getOriginalFilename().length() > 0) {
                try {
                    String uuid = UUID.randomUUID().toString();
                    if (!imgFile.isEmpty()) {
						/*
						 * String fileName = imgFile.getOriginalFilename();
						 * String fileExtension =
						 * FilenameUtils.getExtension(fileName); if
						 * (!ArrayUtils.contains(extensionPermit,
						 * fileExtension)) { throw new Exception(
						 * "No Support extension."); }
						 * 
						 * saveDirectoryPath = saveDirectoryPath + name;
						 * 
						 * log.info("saveDirectoryPath:" + saveDirectoryPath);
						 * FileOutputStream out = new
						 * FileOutputStream(saveDirectoryPath); // 写入文件
						 * out.write(imgFile.getBytes()); out.flush();
						 * out.close();
						 */

                        InputStream in = imgFile.getInputStream();
                        String filepath = TimeUtil.getTimeYMD();
                        String ftpname = uuid + ".jpg";
                        SFTPUpload.upload(in, "/usr/local/sftp/images/" + filepath + "/" + ftpname, "/usr/local/sftp/images/" + filepath);
                        url = FtpConst.IMG_URL_244 + filepath + "/" + ftpname;
                        return url;
                    }
                } catch (Exception e) {
                    log.info("上传异常", e);
                }
            }
        }
        return url;
    }

    /**
     * 根据门店ID取出陈列位信息
     *
     * @param req
     * @param response
     * @param shopid
     * @return
     */
    @RequestMapping(value = "downloadfile")
    public void downloadfile(HttpServletRequest req, HttpServletResponse response, String shopid) {
        response.setHeader("Access-Control-Allow-Origin", "*");
		/*
		 * List<ShopRackVO> list =
		 * shopRackVOMapper.getShopRackVOSByshopId(shopid); JSONObject jo = new
		 * JSONObject(); jo.put("rows", list);
		 */
        response = FtpTool.downFile("10.0.31.206", "administrator", "yhcs1234", "shcg/", "shop_rack_tpl.xlsx",
                response);// ("10.0.31.206", 21, "administrator", "yhcs1234",
        // "shcg/", "shop_rack_tpl.xlsx", "D:/");
        // return JSON.toJSONString(b);
    }

    // ftp://10.0.31.206/shcg/shop_rack_tpl%20(9).xlsx
    public static boolean downFile(String url, // FTP服务器hostname
                                   int port, // FTP服务器端口
                                   String username, // FTP登录账号
                                   String password, // FTP登录密码
                                   String remotePath, // FTP服务器上的相对路径
                                   String fileName, // 要下载的文件名
                                   String localPath // 下载后保存到本地的路径

    ) {
        boolean success = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(url, port);
            // 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            ftp.login(username, password);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return success;
            }
            System.out.println("aaa");
            ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
            FTPFile[] fs = ftp.listFiles();

            for (FTPFile ff : fs) {
                System.out.println("bb" + fs);

                if (ff.getName().equals(fileName)) {
                    System.out.println("dd");
                    File localFile = new File(localPath + "/" + ff.getName());
                    OutputStream is = new FileOutputStream(localFile);
                    ftp.retrieveFile(ff.getName(), is);
                    System.out.println("ccc" + ff.getName() + fileName);
                    is.close();
                }
            }
            ftp.logout();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;
    }

    public static boolean downFileNew(String url, // FTP服务器hostname
                                      int port, // FTP服务器端口
                                      String username, // FTP登录账号
                                      String password, // FTP登录密码
                                      String remotePath, // FTP服务器上的相对路径
                                      String fileName, // 要下载的文件名
                                      String localPath, HttpServletResponse response /// 下载后保存到本地的路径

    ) {
        boolean success = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(url, port);
            // 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            ftp.login(username, password);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return success;
            }
            System.out.println("aaa");
            ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
            FTPFile[] fs = ftp.listFiles();

            for (FTPFile ff : fs) {
                System.out.println("bb" + fs);

                if (ff.getName().equals(fileName)) {
                    System.out.println("dd");
                    File localFile = new File(localPath + "/" + ff.getName());
                    OutputStream is = new FileOutputStream(localFile);
                    ftp.retrieveFile(ff.getName(), is);
                    System.out.println("ccc" + ff.getName() + fileName);
                    is.close();

                    // 获取输出流
                    OutputStream osm = response.getOutputStream();
                    InputStream in = null;
                    // 文件读入
                    in = new FileInputStream(ff.getName());
                    byte[] b = new byte[1024];
                    int i = 0;
                    while ((i = in.read(b)) > 0) {
                        osm.write(b, 0, i);
                    }
                    osm.flush();
                    osm.close();
                }
            }
            ftp.logout();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;
    }

}
