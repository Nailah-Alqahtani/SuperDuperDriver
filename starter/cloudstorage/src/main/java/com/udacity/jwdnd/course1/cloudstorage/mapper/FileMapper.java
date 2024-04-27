package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.FileResponse;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT EXISTS (SELECT 1 FROM FILES WHERE fileName = #{originalFilename})")
    boolean findByFileName(String originalFilename);

    @Insert("INSERT INTO FILES (filename, contenttype,filesize, userid ,filedata) VALUES(#{fileName}, #{contentType}, #{fileSize},#{userId},#{blob})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int uploadFile(File file);

    @Select("SELECT filename FROM FILES WHERE userid = #{userId}")
    List<String> getAllFiles(Integer userId);

    @Delete("DELETE FROM FILES WHERE filename = #{name}")
    void deleteFile(String name);

    @ResultType(FileResponse.class)
    @Select("SELECT filedata as storedData,filename,contenttype FROM FILES WHERE filename = #{name}")
    FileResponse getFileByName(String name);
}
