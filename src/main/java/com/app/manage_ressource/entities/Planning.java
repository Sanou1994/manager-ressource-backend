package com.app.manage_ressource.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Planning
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private long startDate;
    private UUID ressourceId;
    private UUID registerId;
    private long endDate;
    private String target;
    private double mount;
    private boolean isActive;
    @CreationTimestamp
    private Date createdOn;
    @UpdateTimestamp
    private Date updatedOn;

}
