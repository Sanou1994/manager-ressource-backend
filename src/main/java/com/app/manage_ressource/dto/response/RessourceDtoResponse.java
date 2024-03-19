package com.app.manage_ressource.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Data @AllArgsConstructor @NoArgsConstructor
public class RessourceDtoResponse
{

    private UUID id;
    private String category;
    private String description;
    private int count;
    private int currentCount;
    private String code;
    private String marque;
    private int reservationTotalCount;
    private int demandCount;
    private double mount;
    private boolean  status;
    private List<PlanningDtoResponse> plannings= new ArrayList<>();
    private boolean temporaly;
    private boolean programmable;
    private Date createdOn;
    private Date updatedOn;

}