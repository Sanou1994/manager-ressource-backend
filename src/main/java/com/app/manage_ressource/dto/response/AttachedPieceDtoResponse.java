package com.app.manage_ressource.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttachedPieceDtoResponse {
    private UUID id;
    private String url;
    private boolean isActive;
    private String filename;
    private UUID stepID;
    private Resource resource;
    private String title;
    private UUID registerFolderID;
    private Date createdOn;
    private Date updatedOn;
}
