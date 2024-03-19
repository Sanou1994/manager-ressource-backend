package com.app.manage_ressource.dto.response;

import com.app.manage_ressource.entities.RestitutionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DefinitelyDtoResponse
{
    private UUID id;
    private String type_of_ressource;
    private UUID ressourceID;
    private double mount;
    private String description;
    private boolean status;
    private long count;
    private long date_taken;
    private String taker_fullname;
    private UUID takerIdentificant;
    private Date createdOn;
    private Date updatedOn;
}
