package com.yonghui.portal.util.upload;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.yonghui.portal.model.sys.SysFtpConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;

/**
 * sftp文件操作工具类</p>
 *
 * @author zhanghai
 */
public class SftpUtil {

    private final Logger logger = LoggerFactory.getLogger(SftpUtil.class);

    ChannelSftp sftp = null;
    private String host = "";   // ftp 地址
    private int port = 22;      // 端口
    private String username = "";   // 用户名
    private String password = "";   // 密码
    private Integer connecttime = 1;    // 登录超时时间 默认1分钟

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Integer getConnecttime() {
        return connecttime;
    }

    public void setConnecttime(Integer connecttime) {
        this.connecttime = connecttime;
    }

    /**
     * 初始化连接信息
     *
     * @param sysFtpConfig
     */
    public SftpUtil(SysFtpConfig sysFtpConfig) {
        this.host = sysFtpConfig.getHost();
        this.port = sysFtpConfig.getPort();
        this.username = sysFtpConfig.getUsername();
        this.password = sysFtpConfig.getPassword();
        this.connecttime = sysFtpConfig.getConnecttime() != null ? sysFtpConfig.getConnecttime() : 1;
    }

    /**
     * 连接sftp服务器
     *
     * @throws Exception
     */
    public void connect() throws Exception {

        JSch jsch = new JSch();
        Session sshSession = jsch.getSession(this.username, this.host, this.port);
        logger.debug(SftpUtil.class + "Session created.");

        sshSession.setPassword(password);
        Properties sshConfig = new Properties();
        sshConfig.put("StrictHostKeyChecking", "no");
        sshSession.setConfig(sshConfig);
        sshSession.connect(connecttime * 60000);    //	设置登陆超时时间 分钟
        logger.debug(SftpUtil.class + " Session connected.");

        logger.debug(SftpUtil.class + " Opening Channel.");
        Channel channel = sshSession.openChannel("sftp");
        channel.connect();
        this.sftp = (ChannelSftp) channel;
        logger.debug(SftpUtil.class + " Connected to " + this.host + ".");
    }

    /**
     * 关闭连接
     * Disconnect with server
     *
     * @throws Exception
     */
    public void disconnect() throws Exception {
        if (this.sftp != null) {
            if (this.sftp.isConnected()) {
                this.sftp.disconnect();
            } else if (this.sftp.isClosed()) {
                logger.debug(SftpUtil.class + " sftp is closed already");
            }
        }
    }

    /**
     * 上传单个文件
     *
     * @param directory  上传的目录
     * @param uploadFile 要上传的文件
     * @throws Exception
     */
    public void upload(String directory, String uploadFile) throws Exception {
        File file = new File(uploadFile);
        this.upload(directory, file);
    }

    public void upload(File file) throws Exception {
        this.upload(file.getParent(), file.getName(), new FileInputStream(file));
    }

    public void upload(String directory, File file) throws Exception {
        this.upload(directory, file.getName(), new FileInputStream(file));
    }

    public void upload(String directory, String fileName, InputStream inputStream) throws Exception {
        try {
            this.sftp.ls(directory);
        } catch (Exception e) {
            this.sftp.mkdir(directory);
        }
        this.cd(directory);
        this.sftp.put(inputStream, fileName);
    }

    /**
     * 上传目录下全部文件
     *
     * @param directory 上传的目录
     * @throws Exception
     */
    public void uploadByDirectory(String directory) throws Exception {

        String uploadFile = "";
        List<String> uploadFileList = this.listFiles(directory);
        Iterator<String> it = uploadFileList.iterator();

        while (it.hasNext()) {
            uploadFile = it.next().toString();
            this.upload(directory, uploadFile);
        }
    }

    /**
     * 下载单个文件
     *
     * @param directory     下载目录
     * @param downloadFile  下载的文件
     * @param saveDirectory 存在本地的路径
     * @throws Exception
     */
    public void download(String directory, String downloadFile,
                         String saveDirectory) throws Exception {
        String saveFile = saveDirectory + "//" + downloadFile;

        this.sftp.cd(directory);
        File file = new File(saveFile);
        this.sftp.get(downloadFile, new FileOutputStream(file));
    }

    /**
     * 下载目录下全部文件
     *
     * @param directory     下载目录
     * @param saveDirectory 存在本地的路径
     * @throws Exception
     */
    public void downloadByDirectory(String directory, String saveDirectory)
            throws Exception {
        String downloadFile = "";
        List<String> downloadFileList = this.listFiles(directory);
        Iterator<String> it = downloadFileList.iterator();

        while (it.hasNext()) {
            downloadFile = it.next().toString();
            if (downloadFile.toString().indexOf(".") < 0) {
                continue;
            }
            this.download(directory, downloadFile, saveDirectory);
        }
    }


    /**
     * 列出目录下的文件
     *
     * @param directory 要列出的目录
     * @return list 文件名列表
     * @throws Exception
     */
    public List<String> listFiles(String directory) throws Exception {

        Vector<?> fileList;
        List<String> fileNameList = new ArrayList<String>();

        fileList = this.sftp.ls(directory);
        Iterator<?> it = fileList.iterator();

        while (it.hasNext()) {
            String fileName = ((ChannelSftp.LsEntry) it.next()).getFilename();
            if (".".equals(fileName) || "..".equals(fileName)) {
                continue;
            }
            fileNameList.add(fileName);
        }
        return fileNameList;
    }

    /**
     * 更改文件名
     *
     * @param directory 文件所在目录
     * @param oldFileNm 原文件名
     * @param newFileNm 新文件名
     * @throws Exception
     */
    public void rename(String directory, String oldFileNm, String newFileNm)
            throws Exception {
        this.cd(directory);
        this.sftp.rename(oldFileNm, newFileNm);
    }

    public void cd(String directory) throws Exception {
        this.sftp.cd(directory);
    }

    public void rm(String filePath) throws Exception {
        this.sftp.rm(filePath);
    }

    public InputStream get(String directory) throws Exception {
        InputStream streatm = this.sftp.get(directory);
        return streatm;
    }

    /**
     * 删除文件(使用前注意了,文件删除后不可恢复)
     *
     * @param directory  文件所在目录 例如:/data/bamboodata/portal_pic/item/
     * @param deleteFile 文件名 例如:S20150815113255214171.jpg
     * @throws Exception
     */
    public void delete(String directory, String deleteFile) throws Exception {
        this.sftp.cd(directory);
        this.sftp.rm(deleteFile);
    }

//	public static void main(String[] args) throws Exception{
//		SftpUtil sftp = new SftpUtil();
//		try {
//			sftp.connect();
//			sftp.delete("/data/bamboodata/portal_pic/item/", "S20150815113255214171.jpg");
//			sftp.disconnect();
//		} catch (Exception e) {
//			sftp.disconnect();
//			e.printStackTrace();
//		}
//	}
}
