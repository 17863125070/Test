package com.first.demo.fileupload.controller;

import com.alibaba.fastjson.JSONObject;
import com.first.demo.User.entity.User;
import com.first.demo.fileupload.Service.FileuploadService;
import com.first.demo.fileupload.entity.Uploadfile;
import com.first.demo.fileupload.mapper.UploadfileMapper;
import com.first.demo.util.AjaxRusult;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Company：众阳健康
 * @Author: shh
 * @Date: 2020/4/13 8:55
 * @Version 1.0
 */
@Controller
public class FileUploadController {

    @Resource
    private FileuploadService fileuploadService;

    @Resource
    private UploadfileMapper uploadfileMapper;

    @PostMapping("/batch")
    @ResponseBody
    public AjaxRusult fileUpload(MultipartFile file) {
       String fileName =  file.getOriginalFilename();
       //判断浏览器
        // 文件上传时，Chrome和IE/Edge对于originalFilename处理不同
        // Chrome 会获取到该文件的直接文件名称，IE/Edge会获取到文件上传时完整路径/文件名
        // Check for Unix-style path
        int unixSep = fileName.lastIndexOf('/');
        // Check for Windows-style path
        int winSep = fileName.lastIndexOf('\\');
        // Cut off at latest possible point
        int pos = (winSep > unixSep ? winSep : unixSep);
        User sysUser = (User) SecurityUtils.getSubject().getPrincipal();
        String cnName= sysUser.getCnName();
        Integer userId=sysUser.getUserId();
        if (pos != -1)  {
            // Any sort of path separator found...
            fileName = fileName.substring(pos + 1);
        }
        else{
            fileName = cnName+'_'+fileName.substring(pos + 1);
        }
        Uploadfile uploadfile = new Uploadfile();
        uploadfile.setFileName(fileName);
        uploadfile.setUserId(userId);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String formatDate = sdf.format(date);
        uploadfile.setUploadTime(formatDate);
     /*   User sysUser = (User) SecurityUtils.getSubject().getPrincipal();*/
        uploadfile.setOperatorName(cnName);
        if (file != null) {
            String filePath = "D:/uploads/";
            String filePathName = filePath + fileName;
            try {
                file.transferTo(new File(filePathName));
                uploadfileMapper.megreInsert(uploadfile);
            } catch (IOException e) {
                e.printStackTrace();
                return AjaxRusult.error("上传失败，请联系管理员");
            }
            return AjaxRusult.ok("上传成功");
        } else {
            return AjaxRusult.error("文件不能为空");
        }
    }

    /**
     * @return
     * @Date 2020/4/13 13:12
     * @Author jiangheng
     * @Description //TODO  查询所有文件
     **/
    @RequestMapping("/listFile")
    @ResponseBody
    public Map<String, Object> listFile(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        return fileuploadService.listFile(page, limit);
    }




    @RequestMapping("/listFileByFuzzy")
    @ResponseBody
    public Map<String, Object> listFileBuyFuzzy(@RequestBody String operatorName) {
        JSONObject jsonobject = JSONObject.parseObject(operatorName);
        Integer pageNum = jsonobject.getInteger("page");
        Integer pageSize = jsonobject.getInteger("limit");
        String infos = jsonobject.getString("operatorName");
        return fileuploadService.listFileBuyFuzzy(pageNum,pageSize,infos);
    }

    /**
     * @return
     * @Date 2020/4/13 13:12
     * @Author jiangheng
     * @Description //TODO  文件下载
     **/
    @RequestMapping("/fileDownload/{fileName:.+}")
    @ResponseBody
    public AjaxRusult fileDown(@PathVariable("fileName") String fileName, HttpServletResponse resp) {

        String filePath = null;
        try {
            filePath = URLDecoder.decode("D:\\uploads", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //String realPath = "D:" + File.separator + "apache-tomcat-8.5.15" + File.separator + "files";

        String path = filePath + File.separator + fileName;//加上文件名称
        File file = new File(path);
        if (!file.exists()) {
            try {
                throw new IOException("文件不存在");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        resp.reset();
        resp.setContentType("application/octet-stream");
        resp.setCharacterEncoding("utf-8");
        resp.setContentLength((int) file.length());
        resp.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = resp.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(file));
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return AjaxRusult.ok();
    }

    /**
     * @Description:查询下拉框内的选择上传人
     * @Company：众阳健康
     * @Author: wsc on 2020/7/14 17:06
     * @param:
     * @return:
     */
    @RequestMapping("/listUploader")
    @ResponseBody
    public List selectListUploader() {
        List list = fileuploadService.selectListUploader();
        return list;
    }
    /**
     * @Description:删除上传到本地的文件
     * @Company：众阳健康
     * @Author: wsc on 2020/7/14 17:51
     * @param:
     * @return:
     */
    @RequestMapping("/fileDelete/{fileName:.+}")
    @ResponseBody
    public AjaxRusult fileDelete(@PathVariable("fileName") String fileName) {

        String filePath = null;
        try {
            filePath = URLDecoder.decode("D:\\uploads", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //String realPath = "D:" + File.separator + "apache-tomcat-8.5.15" + File.separator + "files";

        String path = filePath + File.separator + fileName;//加上文件名称
        File file = new File(path);
        if (file.exists()) {
            try {
                if (file.delete()) {
                    Integer row = fileuploadService.fileDelete(fileName);
                    if (row>0){
                        return AjaxRusult.ok("文件" +fileName+ "删除成功");
                    }
                } else {
                    return AjaxRusult.error("文件" +fileName+ "删除失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return AjaxRusult.error("删除失败，请联系管理员");
            }
            return AjaxRusult.ok("文件" +fileName+ "删除成功");
        }
        else{
            return AjaxRusult.error("文件" +fileName+ "不存在");
        }
    }
}
