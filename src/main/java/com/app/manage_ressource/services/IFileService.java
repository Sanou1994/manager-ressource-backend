package com.app.manage_ressource.services;

import com.app.manage_ressource.entities.Reponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


public interface IFileService
{
    public Resource loadFileAsResource(String filename);
    public Reponse uploadFile(MultipartFile file);
    public boolean deletedFile(String fileName);
    public String getPathFile(String filename);


}