package com.app.manage_ressource.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.UUID;


@Data @AllArgsConstructor @NoArgsConstructor
public class PlanningDtoResponse
{

    private UUID id;
    private long startDate;
    private double mount;
    private UUID registerId;
    private long endDate;
    private UUID ressourceId;
    private String target;
    private Date createdOn;
    private Date updatedOn;

}