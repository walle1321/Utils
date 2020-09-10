package org.example.utils;

import org.apache.tools.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class ZipUtil {

   private static  Logger logger = LoggerFactory.getLogger(ZipUtil.class);
    /**
     * 使用GBK编码可以避免压缩中文文件名乱码
     */
    private static final String CHINESE_CHARSET = "GBK";

    /**
     * 文件读取缓冲区大小
     */
    private static final int CACHE_SIZE = 1024;

    /**
     * 压缩文件
     *
     * @param sourceFilePath 源文件路径
     * @param zipFilePath    压缩后文件存储路径
     * @param zipFilename    压缩文件名
     */
    public static String compressToZip(String sourceFilePath, String zipFilePath, String zipFilename) {
        File sourceFile = new File(sourceFilePath);
        File zipPath = new File(zipFilePath);
        if (!zipPath.exists()) {
            zipPath.mkdirs();
        }
        File zipFile = new File(zipPath + File.separator + zipFilename);
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile))) {
            writeZip(sourceFile, "", zos);
            //文件压缩完成后，删除被压缩文件
            boolean flag = deleteDir(sourceFile);
            logger.info("删除被压缩文件[" + sourceFile + "]标志：{}", flag);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
        return zipPath + File.separator + zipFilename;
    }

    /**
     * 遍历所有文件，压缩
     *
     * @param file       源文件目录
     * @param parentPath 压缩文件目录
     * @param zos        文件流
     */
    public static void writeZip(File file, String parentPath, ZipOutputStream zos) {
        if (file.isDirectory()) {
            //目录
            parentPath += file.getName() + File.separator;
            File[] files = file.listFiles();
            for (File f : files) {
                writeZip(f, parentPath, zos);
            }
        } else {
            //文件
            try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
                //指定zip文件夹
                ZipEntry zipEntry = new ZipEntry(parentPath + file.getName());
                zos.putNextEntry(zipEntry);
                int len;
                byte[] buffer = new byte[1024 * 10];
                while ((len = bis.read(buffer, 0, buffer.length)) != -1) {
                    zos.write(buffer, 0, len);
                    zos.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage(), e.getCause());
            }
        }
    }

        /**
         * 删除文件夹
         *
         * @param dir
         * @return
         */
        public static boolean deleteDir(File dir) {
            if (dir.isDirectory()) {
                String[] children = dir.list();
                for (int i = 0; i < children.length; i++) {
                    boolean success = deleteDir(new File(dir, children[i]));
                    if (!success) {
                        return false;
                    }
                }
            }
            //删除空文件夹
            return dir.delete();
        }



        /**
         * create by: yp
         * description:  下载zip压缩包
         * create time:  2020/4/28 0028
         *
         * @param url  : zip压缩包的url
         * @param zipName  : zip压缩包的名称
         * @param path  : zip压缩包的生成的位置

         * @return

         */
        public static String downZip(String url,String zipName,String path){


            try {

                URL zipurl = new URL(url);
                DataInputStream dataInputStream = new DataInputStream(zipurl.openStream());
                try {
                   File file = new File(path);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                FileOutputStream fileOutputStream = new FileOutputStream(new File(path)+"\\"+zipName );
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                //byte[] buffer = new byte[1024*1024*1024*1024];
                int length;
                while ((length = dataInputStream.read()) != -1) {
                    output.write(length);
                }
                fileOutputStream.write(output.toByteArray());
                dataInputStream.close();
                fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }

            return "success";


        }

    public static boolean downloadFile(String fileUrl, String fileLocal) throws Exception {
        boolean flag=false;
        URL url = new URL(fileUrl);
        HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
        urlCon.setConnectTimeout(6000);
        urlCon.setReadTimeout(6000);
        int code = urlCon.getResponseCode();
        if (code != HttpURLConnection.HTTP_OK) {
            throw new Exception("文件读取失败");
        }
        //读文件流
        DataInputStream in = new DataInputStream(urlCon.getInputStream());
        DataOutputStream out = new DataOutputStream(new FileOutputStream(fileLocal));
        byte[] buffer = new byte[2048];
        int count = 0;
        while ((count = in.read(buffer)) > 0) {
            out.write(buffer, 0, count);
        }
        try {
            if(out!=null) {
                out.close();
            }
            if(in!=null) {
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        flag=true;
        return flag;
    }

    /**
     * <p>
     * 解压压缩包
     * </p>
     *
     * @param zipFilePath 压缩文件路径
     * @param destDir 压缩包释放目录
     * @throws Exception
     */
    public static void unZip(String zipFilePath, String destDir) throws Exception {
        ZipFile zipFile = new ZipFile(zipFilePath, CHINESE_CHARSET);
        Enumeration<?> emu = zipFile.getEntries();
        BufferedInputStream bis;
        FileOutputStream fos;
        BufferedOutputStream bos;
        File file, parentFile;
        org.apache.tools.zip.ZipEntry entry;
        byte[] cache = new byte[CACHE_SIZE];
        while (emu.hasMoreElements()) {
            entry = (org.apache.tools.zip.ZipEntry) emu.nextElement();
            if (entry.isDirectory()) {
                new File(destDir + entry.getName()).mkdirs();
                continue;
            }
            bis = new BufferedInputStream(zipFile.getInputStream(entry));
            file = new File(destDir + entry.getName());
            parentFile = file.getParentFile();
            if (parentFile != null && (!parentFile.exists())) {
                parentFile.mkdirs();
            }
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos, CACHE_SIZE);
            int nRead = 0;
            while ((nRead = bis.read(cache, 0, CACHE_SIZE)) != -1) {
                fos.write(cache, 0, nRead);
            }
            bos.flush();
            bos.close();
            fos.close();
            bis.close();
        }
        zipFile.close();
    }



    public static void main(String[] args) throws Exception {
/*        String path1 = "D:\\testVideo\\1233456\\001";
        String path2 = "D:\\testVideo\\1233456";
        String fileName = "001.zip";
        String path =  compressToZip(path1,path2,fileName);
        System.out.println(path);*/

//        String url = "http://10.18.99.6:7002/SunICMS/servlet/DownloadFileServlet?UgfnUAyaDhZ7JufGSPDfX17-VPFiUd7fSuORXgABUPfuUd7eVvDqVh7aUQL1mCHrmCer8d7JdQLEmNF4_u6r8NX1_h6sUPHz8hSw_N2zmuSwUB_a_hSumfLEcu6wSsMgUgUgUg6umsMfSN51_N6zUB0sUN51_gIBmsevcs5qYgfrIgMfSPMnpPzfKN5t8Neu_CHtmsSw8N0gDh7lUPw7ptIdpuadSeDHSuHzAPIE_FA4XBI1m5DHpvMB_CIE2cH=";
//        String path = "D:\\360\\test.zip";
//        boolean result = downloadFile(url,path);
//        System.out.println(result);

        UUID uuid = UUID.randomUUID();
        unZip("src/main/resources/temporary/p.zip","src/main/resources/temporary/"+uuid+"/");
    }

}
