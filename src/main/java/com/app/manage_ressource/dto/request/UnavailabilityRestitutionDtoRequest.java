package com.app.manage_ressource.dto.request;

import com.app.manage_ressource.entities.RestitutionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnavailabilityRestitutionDtoRequest
{
    private UUID id;
    private double mount;
    private String description;
    private long date_restitution;
    private long count;

}
