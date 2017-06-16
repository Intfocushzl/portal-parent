package com.yonghui.portal.controller.sys;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.model.businessman.ImgsInfo;
import com.yonghui.portal.model.sys.SysFtpConfig;
import com.yonghui.portal.service.sys.ApiImgsInfoService;
import com.yonghui.portal.util.redis.RedisBizUtilApi;
import com.yonghui.portal.util.upload.SftpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.UUID;

@Controller
@RequestMapping("/api/upload")
public class ApiFileUploadController {
    @Reference
    public ApiImgsInfoService apiImgsInfoService;
    @Autowired
    private RedisBizUtilApi rediBizUtilApi;


    // 图片上传
    @RequestMapping("/itemImgUpload")
    public void itemImgUpload(HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        json.put("ErrMsg", "图片上传失败!");
        json.put("RetCode", "1");
        json.put("imagId", "-1");
        json.put("RetUrl", "");
        try {
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            ImgsInfo imgsInfo = null;
            if (multipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                Iterator<String> iter = multiRequest.getFileNames();
                SysFtpConfig sysFtpConfig = JSONObject.parseObject(rediBizUtilApi.getFtpInfo(1L), SysFtpConfig.class);
                while (iter.hasNext()) {
                    MultipartFile file = multiRequest.getFile(iter.next());
                    if (file != null) {
                        SftpUtil sftp = new SftpUtil(sysFtpConfig);
                        sftp.connect();
                        String fileName = file.getOriginalFilename();
                        String imgType = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                        String sysName = UUID.randomUUID().toString().replace("-", "").toLowerCase() + "." + imgType;
                        String imgPath = "portal_pic" + File.separator + "item" + File.separator;
                        String path = sysFtpConfig.getRootpath() + imgPath;
                        sftp.upload(path.replace("\\", "/"), sysName, file.getInputStream());
                        imgsInfo = new ImgsInfo();
                        imgsInfo.setName(fileName);
                        imgsInfo.setSysName(sysName);
                        imgsInfo.setImgSize(file.getSize() + "");
                        imgsInfo.setImgType(imgType);
                        imgsInfo.setImgPath(imgPath.replace("\\", "/").replace("//", "/") + sysName);
                        imgsInfo.setDirectoryId(sysFtpConfig.getRootpath());
                        //使用图片服务保存图片信息
                        apiImgsInfoService.save(imgsInfo);
                        // KindEditor 需要返回的信息 error = 0
                        json.put("ErrMsg", "");
                        json.put("RetCode", "0");
                        json.put("imagId", imgsInfo.getId());
                        json.put("RetUrl", sysFtpConfig.getOrigin() + imgsInfo.getImgPath());
                        sftp.disconnect();
                    }
                }
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
    public void removeImg(HttpServletRequest request, HttpServletResponse response) {
        String imgPath = request.getParameter("imgPath");
        try {
            if (imgPath != null && !imgPath.equals("")) {
                SysFtpConfig sysFtpConfig = JSONObject.parseObject(rediBizUtilApi.getFtpInfo(1L), SysFtpConfig.class);
                String directory = sysFtpConfig.getRootpath() + "item/";
                String deleteFile = imgPath.substring(imgPath.lastIndexOf("/") + 1, imgPath.length());
                SftpUtil sftp = new SftpUtil(sysFtpConfig);
                sftp.connect();
                sftp.delete(directory, deleteFile);
                sftp.disconnect();
            }
        } catch (Exception e) {
            System.out.println("图片删除失败---->" + imgPath);
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
