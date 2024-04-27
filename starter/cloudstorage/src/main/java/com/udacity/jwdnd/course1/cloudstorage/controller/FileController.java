package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.FileResponse;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;

@Controller
@AllArgsConstructor
public class FileController {
    private final FileService fileService;
    private final UserService userService;

    @PostMapping("/upload-file")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile file, Authentication authentication, RedirectAttributes redirectAttributes) {
        try {
            User user = userService.getUser(authentication.getName());
            if(file.isEmpty()){
                redirectAttributes.addFlashAttribute("operationFailed","No file selected!");
                return "redirect:/home";
            }
            if(fileService.duplicatedFileName(file.getOriginalFilename())){
                redirectAttributes.addFlashAttribute("operationFailed","Duplicated file name!");
                return "redirect:/home";
            }
            fileService.uploadFile(file , user);
            redirectAttributes.addFlashAttribute("operationSuccess", "File is uploaded successfully!");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("operationFailed","Something went wrong!");
        }
        return "redirect:/home";
    }

    @GetMapping("/delete-file/{name}")
    public String noteDelete(@PathVariable String name, RedirectAttributes redirectAttributes) {
        try {
            fileService.deleteFile(name);
            redirectAttributes.addFlashAttribute("operationSuccess", "File is deleted successfully!");
        }catch (Exception e)
        {
            redirectAttributes.addFlashAttribute("operationFailed","Something went wrong!");
        }
        return "redirect:/home";

    }

    @GetMapping("/download-file/{name}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String name) {
        FileResponse fileResponse=fileService.downloadFile(name);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+fileResponse.getFileName()+"\"")
                .body(new ByteArrayResource(fileResponse.getStoredData()));
    }
}
