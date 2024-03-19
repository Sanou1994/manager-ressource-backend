package com.app.bank.dto.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
@NoArgsConstructor @AllArgsConstructor
public class AttachedPieceDtoRequest {

    private UUID id;
    private MultipartFile file;
    private String filename;
    private boolean isActive;
    private UUID stepID;
    private String title;
    private UUID registerFolderID;

}
