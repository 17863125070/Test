package com.first.demo.fileupload.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Company：众阳健康
 * @Author: jiangheng
 * @Date: 2020/4/13 11:02
 * @Version 1.0
 */
public interface FileuploadService {
    Map<String,Object> listFile(Integer pageNum, Integer pageSize);

    Map<String, Object> listFileBuyFuzzy(Integer pageNum, Integer pageSize, String infos);

    List selectListUploader();

    Integer fileDelete(String fileName);
}
