package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.FileResponse;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class FileService {
    private final FileMapper fileMapper;


    public int uploadFile(MultipartFile file, User user) throws IOException {
       return fileMapper.uploadFile(new File(null,file.getOriginalFilename(),file.getContentType(),String.valueOf(file.getSize()),file.getBytes(),user.getUserId()));
    }

    public boolean duplicatedFileName(String originalFilename) {
        return fileMapper.findByFileName(originalFilename);
    }

    public List<String> getAllFiles(Integer userId) {
        return fileMapper.getAllFiles(userId);
    }
    public void deleteFile(String name){
        fileMapper.deleteFile(name);
    }

    public FileResponse downloadFile(String name) {
        return fileMapper.getFileByName(name);
    }
}
