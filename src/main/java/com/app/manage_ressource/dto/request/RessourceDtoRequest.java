package com.app.manage_ressource.dto.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@NoArgsConstructor @AllArgsConstructor
public class RessourceDtoRequest {
    private UUID id;
    private String category;
    private String description;
    private String code;
    private String marque;
    private int count;
    private double mount;
    private boolean paidByHours;
    private boolean paidByDays;
    private boolean paidByGroups;
    private int countByGroups;
    private boolean unpaid;
    private int currentCount;
    private int demandCount;
    private boolean  status;
    private boolean temporaly;
    private boolean programmable;

}
