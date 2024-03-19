package com.app.manage_ressource.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Data @AllArgsConstructor @NoArgsConstructor
public class TypeRessourceDtoResponse
{

    private UUID id;
    private String type;
    private boolean  isActive;
    private Date createdOn;
    private Date updatedOn;

}