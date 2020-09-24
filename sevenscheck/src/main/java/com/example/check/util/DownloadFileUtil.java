package com.example.check.util;

import sun.misc.BASE64Encoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class DownloadFileUtil {
    /**
     * 下载文件
     *
     * @param originalFileName ：下载文件的原始文件名
     * @param file             ：下载的文件
     * @param response         ：相应对象
     */
    public static void downloadFile(String originalFileName, File file, HttpServletResponse response, HttpServletRequest request) throws Exception {

        //相应头的处理
        //清空response中的输出流
//        response.reset();
        //设置文件大小
        response.setContentLength((int) file.length());
        System.err.println((int) file.length());
        //设置Content-Type头
        response.setContentType("application/octet-stream;charset=UTF-8");
        //设置Content-Disposition头 以附件形式解析
        String encodedFilename = processFileName(request, originalFileName);
//        String encodedFilename=file.getName();
        response.addHeader("fileName",encodedFilename);
        response.addHeader("Content-Disposition", "attachment;filename=" + encodedFilename);
        System.err.println(file.getName());
        //将来文件流写入response中
        FileInputStream fileInputStream = null;
        ServletOutputStream outputStream = null;
        try {
            //获取文件输入流
            fileInputStream = new FileInputStream(file);
            //创建数据缓冲区
            byte[] buffers = new byte[1024];
            //通过response中获取ServletOutputStream输出流
            outputStream = response.getOutputStream();
            int length;
            while ((length = fileInputStream.read(buffers)) > 0) {
                //写入到输出流中
                outputStream.write(buffers, 0, length);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //流的关闭
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取URL编码后的原始文件名
     *
     * @param request          ：客户端请求
     * @param fileNames ：原始文件名
     * @return ：
     */
    public static String processFileName(HttpServletRequest request, String fileNames) {
        String codedfilename = null;
        try {
            String agent = request.getHeader("USER-AGENT");
            if (null != agent && -1 != agent.indexOf("MSIE") || null != agent
                    && -1 != agent.indexOf("Trident")) {// ie

                String name = java.net.URLEncoder.encode(fileNames, "UTF8");

                codedfilename = name;
            } else if (null != agent && -1 != agent.indexOf("Mozilla")) {// 火狐,chrome等

                codedfilename = new String(fileNames.getBytes("UTF-8"), "iso-8859-1");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return codedfilename;
    }
}
