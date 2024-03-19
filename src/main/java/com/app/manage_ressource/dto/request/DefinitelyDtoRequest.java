package com.app.manage_ressource.dto.request;

import com.app.manage_ressource.entities.RestitutionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DefinitelyDtoRequest
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
}
