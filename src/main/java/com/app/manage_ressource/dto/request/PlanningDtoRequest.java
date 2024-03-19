package com.app.manage_ressource.dto.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@NoArgsConstructor @AllArgsConstructor
public class PlanningDtoRequest {
    private UUID id;
    private long startDate;
    private long endDate;
    private UUID registerId;
    private double mount;
    private UUID ressourceId;
    private String target;

}
