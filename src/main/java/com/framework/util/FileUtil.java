package com.framework.util;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

/**
 * Created by deru on 2017/1/26.
 */
public class FileUtil {

    static public String fileUpload(HttpServletRequest request,String path) throws IOException {
        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        String realPath = request.getSession().getServletContext().getRealPath(path);
        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
                request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        if(multipartResolver.isMultipart(request))
        {
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
            //获取multiRequest 中所有的文件名
            Iterator iter=multiRequest.getFileNames();
            while(iter.hasNext())
            {
                //一次遍历所有文件
                MultipartFile file=multiRequest.getFile(iter.next().toString());
                if(file!=null)
                {
                    String fileName=generateFileName(file.getOriginalFilename());
                    File new_file=new File(realPath,fileName);
                    //上传
                    file.transferTo(new_file);
                    return path + "/" +fileName;
                }
            }
        }
        return "";
    }

    static public String saveFile(HttpServletRequest request,CommonsMultipartFile file,String path) throws IOException {
        String realPath = request.getSession().getServletContext().getRealPath(path);
        String fileName=generateFileName(file.getOriginalFilename());
        File new_file=new File(realPath,fileName);
        //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
        file.transferTo(new_file);
        return path + "/" +fileName;
    }

    /**
     * new文件名= 时间 + 全球唯一编号
     * @param fileName old文件名
     * @return new文件名
     */
    static public String generateFileName(String fileName) {
        //时间
        DateFormat df = new SimpleDateFormat("yy_MM_dd_HH_mm_ss");
        String formatDate = df.format(new Date());
        //全球唯一编号
        String uuid= UUID.randomUUID().toString();
        int position = fileName.lastIndexOf(".");
        String extension = fileName.substring(position);
        return formatDate + uuid + extension;
    }
}
