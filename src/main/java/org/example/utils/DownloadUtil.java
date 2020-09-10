package org.example.utils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * 下载工具类
 *
 * @author: agu
 */
public class DownloadUtil {
    /**
     * usrStr : 图片路径
     * filename: 图片名称
     * savePath: 要保存的路径
     */
    public static void download(String urlStr, String filename, String
            savePath) throws IOException {
        URL url = new URL(urlStr);
        //打开url连接
        URLConnection connection = url.openConnection();
        //请求超时时间
        connection.setConnectTimeout(5000);
        //输入流
        InputStream in = connection.getInputStream();
        //缓冲数据
        byte[] bytes = new byte[1024];
        //数据长度
        int len;
        //文件
        File file = new File(savePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        OutputStream out = new
                FileOutputStream(file.getPath() + "\\" + filename);
        //先读到bytes中
        while ((len = in.read(bytes)) != -1) {
            //再从bytes中写入文件
            out.write(bytes, 0, len);
        }
        //关闭IO
        out.close();
        in.close();
    }

    public static void main(String[] args) throws IOException {
        download("http://gd.downkuai.com/fictitious/soft/xshell6_113909.rar","2.zip","src/main/resources/picture/");

    }
}