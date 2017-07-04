package com.yonghui.portal.util.ftp;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/**
 * 处理Ftp文件的上传和下载
 *
 * 
 *
 */
public class FtpTool {

	 /**  
     * Description: 向FTP服务器上传文件  
     * @param host FTP服务器hostname  
     * @param port FTP服务器端口  
     * @param username FTP登录账号  
     * @param password FTP登录密码  
     * @param basePath FTP服务器基础目录 
     * @param filePath FTP服务器文件存放路径。例如分日期存放：/2015/01/01。文件的路径为basePath+filePath 
     * @param filename 上传到FTP服务器上的文件名  
     * @param input 输入流  
     * @return 成功返回true，否则返回false  
     */    
    public static boolean uploadFile(String host, int port, String username, String password, String basePath,  
            String filePath, String filename, InputStream input) {  
        boolean result = false;  
        FTPClient ftp = new FTPClient();  
        try {  
            int reply;  
            ftp.connect(host, port);// 连接FTP服务器  
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器  
            ftp.login(username, password);// 登录  
            reply = ftp.getReplyCode();  
            if (!FTPReply.isPositiveCompletion(reply)) {  
                ftp.disconnect();  
                return result;  
            }  
            //切换到上传目录  
            if (!ftp.changeWorkingDirectory(basePath+filePath)) {  
                //如果目录不存在创建目录  
                String[] dirs = filePath.split("/");  
                String tempPath = basePath;  
                for (String dir : dirs) {  
                    if (null == dir || "".equals(dir)) continue;  
                    tempPath += "/" + dir;  
                    if (!ftp.changeWorkingDirectory(tempPath)) {  
                        if (!ftp.makeDirectory(tempPath)) {  
                            return result;  
                        } else {  
                            ftp.changeWorkingDirectory(tempPath);  
                        }  
                    }  
                }  
            }  
            //设置上传文件的类型为二进制类型  
            ftp.setFileType(FTP.BINARY_FILE_TYPE);  
            //上传文件  
            if (!ftp.storeFile(filename, input)) {  
                return result;  
            }  
            input.close();  
            ftp.logout();  
            result = true;  
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
        return result;  
    }  

    /**
     * Description: 从FTP服务器下载文件
     *
     * @param url
     *            FTP服务器hostname
     * @param username
     *            FTP登录账号
     * @param password
     *            FTP登录密码
     * @param remotePath
     *            FTP服务器上的相对路径
     * @param fileName
     *            下载时的默认文件名
     * @param localPath
     *            下载后保存到本地的路径
     * @return
     */
    public static HttpServletResponse downFile(String url, String username, String password,
            String remotePath, String fileName, HttpServletResponse response) {
        // 初始表示下载失败
        boolean success = false;
        // 创建FTPClient对象
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            // 连接FTP服务器
            // 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            ftp.connect(url);
            // 登录ftp
            ftp.login(username, password);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return response;
            }
            String realName = fileName;
            // 转到指定下载目录
            ftp.changeWorkingDirectory(remotePath);
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            // 列出该目录下所有文件
            // 设置文件下载头部
            response.setContentType("application/x-msdownload");// 设置编码
            response.setHeader("Content-Disposition", "attachement;filename="
                    + new String(fileName.getBytes(), "ISO-8859-1"));
            FTPFile[] fs = ftp.listFiles();
            // 遍历所有文件，找到指定的文件
            for (FTPFile ff : fs) {
                if (ff.getName().equals(realName)) {
                    OutputStream out = response.getOutputStream();
                    InputStream bis = ftp.retrieveFileStream(realName);

                    // 根据绝对路径初始化文件
                    // 输出流
                    int len = 0;
                    byte[] buf = new byte[1024];
                    while ((len = bis.read(buf)) > 0) {
                        out.write(buf, 0, len);
                        out.flush();
                    }
                    out.close();
                    bis.close();
                }
            }
            ftp.logout();
            // 下载成功
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
        return response;
    }

}