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
public class Unavailability
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @CreationTimestamp
    private Date createdOn;
    @UpdateTimestamp
    private Date updatedOn;
}
