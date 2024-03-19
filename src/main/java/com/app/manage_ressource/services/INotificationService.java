package com.app.manage_ressource.services;

import com.app.manage_ressource.entities.Reponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


public interface INotificationService
{
    public Reponse sendSms(String filename);
    public Reponse sendMail(MultipartFile file);
}