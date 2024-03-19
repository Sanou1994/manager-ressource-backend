package com.app.manage_ressource.repositories;

import com.app.manage_ressource.entities.Planning;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IDaoPlanning extends JpaRepository<Planning, UUID> {

    List<Planning> findByRessourceId(UUID ressourceID);
}
