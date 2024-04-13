package com.app.manage_ressource.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ressource
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String category;
    @Lob
    private String description;
    private int count;
    private String code;
    private String marque;
    private int currentCount;
    private int demandCount;
    private int countByGroups=0;
    private double mount;
    private boolean paidByHours=false;
    private boolean paidByDays=false;
    private boolean paidByGroups=false;
    private boolean unpaid=true;
    private boolean  status;
    private boolean temporaly;
    private boolean programmable;
    @CreationTimestamp
    private Date createdOn;
    @UpdateTimestamp
    private Date updatedOn;

}
