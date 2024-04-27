package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Data;

@Data
public class FileResponse {
    private String fileName;
    private String contentType;
    private byte[] storedData;
}