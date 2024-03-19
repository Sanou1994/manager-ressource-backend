package com.app.manage_ressource.dto.request;

import com.app.manage_ressource.entities.RestitutionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnavailabilityDtoRequest
{
    private UUID id;
    private String type_of_ressource;
    private UUID ressourceID;
    private double mount;
    private String description;
    private boolean status;
    private RestitutionEnum etat;
    private long date_restitution;
    private long count;
    private long count_given;
    private long date_taken;
    private long date_given;
    private String taker_fullname;
    private UUID takerIdentificant;
    private String taker_signature ;
}
