package com.first.demo.fileupload.mapper;

import com.first.demo.fileupload.entity.Uploadfile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UploadfileMapper {
    int deleteByPrimaryKey(String id);

    int insert(Uploadfile record);



    List<Uploadfile> selectAll();

    int updateByPrimaryKey(Uploadfile record);

    /**
     * @return
     * @Date 2020/4/13 11:15
     * @Author jiangheng
     * @Description //TODO  查询上传的文件
     **/
    List<Uploadfile> listFile();
    /**
     * @return
     * @Date 2020/4/13 13:29
     * @Author jiangheng
     * @Description //TODO 上传文件
     **/
    int megreInsert(Uploadfile record);

    List<Uploadfile> listFileBuyFuzzy(@Param("infos") String infos);

    List<Uploadfile> selectListUploader();

    Integer fileDelete(String fileName);

    List<Uploadfile> file(Integer userId);

    List<Uploadfile> selectListUploaderByUserId(Integer userId);
}