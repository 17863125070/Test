package com.first.demo.fileupload.Service.impl;

import com.first.demo.User.entity.User;
import com.first.demo.User.service.SysRoleService;
import com.first.demo.fileupload.Service.FileuploadService;
import com.first.demo.fileupload.entity.Uploadfile;
import com.first.demo.fileupload.mapper.UploadfileMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Company：众阳健康
 * @Author: jiangheng
 * @Date: 2020/4/13 11:02
 * @Version 1.0
 */
@Service
public class FileuploadServiceImpl implements FileuploadService {
    @Resource
    private UploadfileMapper uploadfileMapper;
    @Resource
    private SysRoleService sysRoleService;
    /**
     * @Description:查询出表格中所需要的文件信息
     * @Company：众阳健康
     * @Author: wsc on 2020/7/15 14:16
     * @param:
     * @return:
     */
    @Override
    public Map<String,Object> listFile(Integer pageNum, Integer pageSize) {
        Map<String,Object> map = new HashMap<>();
        PageHelper.startPage(pageNum, pageSize);
        User principal = (User) SecurityUtils.getSubject().getPrincipal();
        Integer userId = principal.getUserId();
        List<String> roleName=sysRoleService.findRolesByUserId(userId);
        for (String name:roleName){
            if("系统管理员".equals(name)){
                List<Uploadfile> uploadfilesyslist = uploadfileMapper.listFile();
                PageInfo<Uploadfile> uploadfilePageInfo = new PageInfo<>(uploadfilesyslist);
                map.put("data",uploadfilePageInfo.getList());
                map.put("count", uploadfilePageInfo.getTotal());
                map.put("code", 0);
                return map;
            }else{
                List<Uploadfile> uploadfilelist = uploadfileMapper.file(userId);
                PageInfo<Uploadfile> uploadfilePageInfo = new PageInfo<>(uploadfilelist);
                map.put("data",uploadfilePageInfo.getList());
                map.put("count", uploadfilePageInfo.getTotal());
                map.put("code", 0);
                return map;
            }
        }
        return map;
    }

    @Override
    public Map<String, Object> listFileBuyFuzzy(Integer pageNum, Integer pageSize, String infos) {
        Map<String,Object> map = new HashMap<>();
        PageHelper.startPage(pageNum, pageSize);
        List<Uploadfile> uploadfilelist = uploadfileMapper.listFileBuyFuzzy(infos);
        PageInfo<Uploadfile> uploadfilePageInfo = new PageInfo<>(uploadfilelist);
        map.put("data",uploadfilePageInfo.getList());
        map.put("count", uploadfilePageInfo.getTotal());
        map.put("code", 0);
       return map;
    }

    /**
     * @Description:筛选出下拉框中上传过文件的用户
     * @Company：众阳健康
     * @Author: wsc on 2020/7/15 14:14
     * @param:
     * @return:
     */
    @Override
    public List selectListUploader() {
        User principal = (User) SecurityUtils.getSubject().getPrincipal();
        Integer userId = principal.getUserId();
        List<String> roleName=sysRoleService.findRolesByUserId(userId);
        List uploaderList = new ArrayList();
        for(String name:roleName){
            if("系统管理员".equals(name)){
                List<Uploadfile> list = uploadfileMapper.selectListUploader();
                if (list != null && !list.isEmpty()) {
                    for (Uploadfile Uploadfile : list) {
                        uploaderList.add(Uploadfile.getOperatorName());
                    }
                }
                return uploaderList;
            }else{
                List<Uploadfile> list = uploadfileMapper.selectListUploaderByUserId(userId);
                if (list != null && !list.isEmpty()) {
                    for (Uploadfile Uploadfile : list) {
                        uploaderList.add(Uploadfile.getOperatorName());
                    }
                }
                return uploaderList;
            }
        }
        return uploaderList;
    }

    /**
     * @Description:删除本地文件并且删除数据库中的内容
     * @Company：众阳健康
     * @Author: wsc on 2020/7/15 14:15
     * @param:
     * @return:
     */
    @Override
    public Integer fileDelete(String fileName) {
        if (fileName!=null){
            Integer rows = uploadfileMapper.fileDelete(fileName);
           if(rows>0){
               return rows;
           }
        }
        return 0;
    }
}
