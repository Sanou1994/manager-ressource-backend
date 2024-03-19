package com.app.manage_ressource.repositories;

import com.app.manage_ressource.entities.TypeRessource;
import com.app.manage_ressource.entities.Unavailability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IDaoUnavailability extends JpaRepository<Unavailability, UUID> {
    List<Unavailability> findByRessourceID(UUID ressourceID);
    List<Unavailability> findByTakerIdentificantAndRessourceID(UUID dentifiant,UUID ressourceID);




}
