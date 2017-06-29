package com.yonghui.portal.controller.sys;

import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.model.businessman.ImgsInfo;
import com.yonghui.portal.model.sys.SysFtpConfig;
import com.yonghui.portal.service.businessman.ImgsInfoService;
import com.yonghui.portal.util.upload.SftpUtil;
import com.yonghui.portal.utils.redis.RedisBizUtilAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.UUID;

/**
 * 文件上传
 * 张海 2017-6-28
 */
@Controller
@RequestMapping("/upload")
public class FileUploadController {
    @Autowired
    public ImgsInfoService imgsInfoService;
    @Autowired
    private RedisBizUtilAdmin redisBizUtilAdmin;

    /**
     * 编辑器插入图片
     *
     * @param request
     * @param response
     */
    @RequestMapping("/kindEditorImgUpload")
    public void kindEditorImgUpload(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out;
        JSONObject json = new JSONObject();
        try {
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            ImgsInfo imgsInfo = null;
            if (multipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                Iterator<String> iter = multiRequest.getFileNames();
                SysFtpConfig sysFtpConfig = JSONObject.parseObject(redisBizUtilAdmin.getFtpInfo(1L), SysFtpConfig.class);
                while (iter.hasNext()) {
                    MultipartFile file = multiRequest.getFile(iter.next());
                    if (file != null) {
                        SftpUtil sftp = new SftpUtil(sysFtpConfig);
                        sftp.connect();
                        String fileName = file.getOriginalFilename();
                        String imgType = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                        String sysName = UUID.randomUUID().toString().replace("-", "").toLowerCase() + "." + imgType;
                        String imgPath = "portal_pic" + File.separator + "item" + File.separator;

                        //原图
                        imgsInfo = new ImgsInfo();
                        imgsInfo.setName(fileName);
                        imgsInfo.setSysName(sysName);
                        imgsInfo.setImgSize(file.getSize() + "");
                        imgsInfo.setImgType(imgType);
                        imgsInfo.setImgPath(imgPath.replace("\\", "/") + sysName);
                        imgsInfo.setDirectoryId(sysFtpConfig.getRootpath());
                        imgsInfoService.save(imgsInfo);

                        // 保存到ftp
                        String path = sysFtpConfig.getRootpath() + imgPath;
                        sftp.upload(path.replace("\\", "/"), sysName, file.getInputStream());
                        sftp.disconnect();
                    }
                }
                // KindEditor 需要返回的信息
                json.put("error", 0);
                json.put("url", sysFtpConfig.getOrigin() + imgsInfo.getImgPath());
            }
            printJson(response, json.toString());
        } catch (Exception e) {
            e.printStackTrace();
            try {
                json.put("error", 1);
                json.put("message", "错误信息");
                printJson(response, json.toString());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * 文件上传
     *
     * @param request
     * @param response
     */
    @RequestMapping("/itemUpload")
    public void itemUpload(HttpServletRequest request, HttpServletResponse response) {
        // 1,图片，2附件，3，视频
        Long tag = Long.parseLong(request.getParameter("tag"));
        JSONObject json = new JSONObject();
        json.put("ErrMsg", "上传失败!");
        json.put("RetCode", "1");
        json.put("imagId", "-1");
        json.put("RetUrl", "");
        try {
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            ImgsInfo imgsInfo = null;
            if (multipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                Iterator<String> iter = multiRequest.getFileNames();
                SysFtpConfig sysFtpConfig = JSONObject.parseObject(redisBizUtilAdmin.getFtpInfo(1L), SysFtpConfig.class);
                String filePath;
                String fileUrl = "";
                while (iter.hasNext()) {
                    MultipartFile file = multiRequest.getFile(iter.next());
                    if (file != null) {
                        SftpUtil sftp = new SftpUtil(sysFtpConfig);
                        sftp.connect();
                        String fileName = file.getOriginalFilename();
                        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                        String sysName = UUID.randomUUID().toString().replace("-", "").toLowerCase() + "." + fileType;
                        filePath = File.separator + "item" + File.separator;

                        // 保存文件信息
                        if (tag == 1) {
                            filePath = "portal_pic" + filePath;
                        } else if (tag == 2) {
                            filePath = "portal_attach" + filePath;
                        } else if (tag == 3) {
                            filePath = "portal_video" + filePath;
                        } else {
                            filePath = "nullbase" + filePath;
                        }

                        fileUrl = filePath.replace("\\", "/").replace("//", "/") + sysName;
                        imgsInfo = new ImgsInfo();
                        imgsInfo.setTag(tag);
                        imgsInfo.setName(fileName);
                        imgsInfo.setSysName(sysName);
                        imgsInfo.setImgSize(file.getSize() + "");
                        imgsInfo.setImgType(fileType);
                        imgsInfo.setImgPath(fileUrl);
                        imgsInfo.setDirectoryId(sysFtpConfig.getRootpath());
                        //使用图片服务保存图片信息
                        imgsInfoService.save(imgsInfo);

                        // 保存到ftp服务器
                        String path = sysFtpConfig.getRootpath() + filePath;
                        sftp.upload(path.replace("\\", "/"), sysName, file.getInputStream());
                        sftp.disconnect();
                    }
                }
                // 返回结果
                json.put("ErrMsg", "");
                json.put("RetCode", "0");
                json.put("imagId", imgsInfo.getId());
                json.put("RetUrl", sysFtpConfig.getOrigin() + fileUrl);
            }
            printJson(response, json.toString());
        } catch (Exception e) {
            e.printStackTrace();
            try {
                json.put("RetCode", "1");
                printJson(response, json.toString());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * 删除图片
     *
     * @param request
     * @param response
     */
    @RequestMapping("/removeImg")
    @ResponseBody
    public Integer removeImg(HttpServletRequest request, HttpServletResponse response) {
        String imgPath = request.getParameter("imgPath");
        try {
            if (imgPath != null && !imgPath.equals("")) {
                SysFtpConfig sysFtpConfig = JSONObject.parseObject(redisBizUtilAdmin.getFtpInfo(1L), SysFtpConfig.class);
                String directory = sysFtpConfig.getRootpath() + "portal_pic/item/";
                String deleteFile = imgPath.substring(imgPath.lastIndexOf("/") + 1, imgPath.length());
                SftpUtil sftp = new SftpUtil(sysFtpConfig);
                sftp.connect();
                sftp.delete(directory, deleteFile);
                sftp.disconnect();
                return 0;//成功
            } else {
                return 2;//图片路径为空
            }
        } catch (Exception e) {
            System.out.println("图片删除失败---->" + imgPath);
            return 1;
        }
    }

    //操作结果
    protected void printJson(HttpServletResponse response, String json)
            throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
        response.getWriter().flush();
        response.getWriter().close();
    }

}
